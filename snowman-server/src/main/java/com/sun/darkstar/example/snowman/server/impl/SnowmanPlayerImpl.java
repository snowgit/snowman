/*
* Copyright (c) 2008, Sun Microsystems, Inc.
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in
*       the documentation and/or other materials provided with the
*       distribution.
*     * Neither the name of Sun Microsystems, Inc. nor the names of its
*       contributors may be used to endorse or promote products derived
*       from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
* A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
* OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
* THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
* OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/ 
package com.sun.darkstar.example.snowman.server.impl;

import com.sun.darkstar.example.snowman.common.physics.enumn.EForce;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanPlayer;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanGame;
import com.sun.darkstar.example.snowman.common.protocol.messages.ServerMessages;
import com.sun.darkstar.example.snowman.common.protocol.processor.IServerProcessor;
import com.sun.darkstar.example.snowman.common.protocol.enumn.ETeamColor;
import com.sun.darkstar.example.snowman.common.util.HPConverter;
import com.sun.darkstar.example.snowman.server.context.SnowmanAppContext;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanFlag;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.Task;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is the player's "proxy" in the world of managed
 * objects.  It implements the ClientSessionListener interface so that
 * it can be the reception point for all client session events.
 * For here, it will call other managed ibjects to respond to thsoe
 * events.
 * @author Jeffrey Kesselman
 * @author Owen Kellett
 */
public class SnowmanPlayerImpl implements SnowmanPlayer, Serializable, 
                                          IServerProcessor
{
    public static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(SnowmanPlayerImpl.class.getName());
    
    private static long DEATHDELAYMS = 10 * 1000;
    private static float POSITIONTOLERANCESQD = 1.0f;//.5f * .5f;
    
    private ManagedReference<ClientSession> sessionRef;
    private final String name;
    private int wins;
    private int losses;
    private int id;
    private float startX;
    private float startY;
    private float destX;
    private float destY;
    private float deltaX;
    private float deltaY;
    // zero timestamp indicates no move yet received
    private long timestamp;
    private ETeamColor teamColor;
    private ManagedReference<SnowmanGame> currentGameRef;
    private boolean readyToPlay = false;
    private int hitPoints = 100;
    private SnowmanAppContext appContext;
    
    public SnowmanPlayerImpl(SnowmanAppContext appContext,
                             ClientSession session) {
        this.appContext = appContext;
        name = session.getName();
        setSession(session);
    }    

    public void setID(int id) {
        this.id = id;
    }

    public void setTimestampLocation(long timestamp, float x, float y) {
       startX = destX = x;
       startY = destY = y;
       deltaX = deltaY = 0;
       this.timestamp = timestamp;
    }

    public void setTeamColor(ETeamColor color) {
        appContext.getDataManager().markForUpdate(this);
        teamColor = color;
    }

    public void setSession(ClientSession arg0) {
        sessionRef = appContext.getDataManager().createReference(arg0);
    }
    
    public void setGame(SnowmanGame game){
        if (game == null){
            currentGameRef = null;
        } else {
            currentGameRef = appContext.getDataManager().createReference(game);
        }
    }
    
    public float ranking(){
        return 1000f*wins/losses;
    }
    
    public float getX() {
        assert (deltaX == 0) && (deltaY != 0);
        return startX;
    }
    
    public float getY() {
        assert (deltaX == 0) && (deltaY != 0);
        return startY;
    }
    
    // transient class to pass around xy pairs
    // TODO include flag on whether we are moving?
    static private class Position {
        float x;
        float y;
        Position(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
    
    private Position getCurrentPos(long time) {
        Position current = new Position(startX, startY);
        if ((deltaX != 0) || (deltaY != 0)){ // moving
            long dt = time - timestamp;
            current.x += (deltaX * dt);
            current.y += (deltaY * dt);

            // clip the new position to the destination
            float dx = current.x - startX;
            float dy = current.y - startY;
            float newLengthSqd = (dx*dx) + (dy*dy);
            dx = destX - startX;
            dy = destY - startY;
            float maxLengthSqd = (dx*dx) + (dy*dy);
            if (newLengthSqd > maxLengthSqd) {
                current.x = destX;
                current.y = destY;
            }
        }
        return current;
    }
    
    // check whether x, y is within tolerance (squared) of the current
    // position
    public boolean checkXY(long time, float x, float y, float toleranceSqd) {
        return checkTolerance(getCurrentPos(time), x, y, toleranceSqd);
    }
    
    // update the start position to the current position, while checking
    // whether x, y is withing tolerance (squared) of the new start
    // position
    private boolean setStartXY(long time, float x, float y, float toleranceSqd) {
        Position current = getCurrentPos(time);
        startX = current.x;
        startY = current.y;
        return checkTolerance(current, x, y, toleranceSqd);
    }
    
    // Check whether x,y is within tolerance (squared) of pos
    private boolean checkTolerance(Position pos, float x, float y,
                                   float toleranceSqd)
    {
        float dx = pos.x - x;
        float dy = pos.y - y;
        float skewSqd = (dx*dx)+(dy*dy);
        return true;//skewSqd < toleranceSqd; 
    }
                               
    public int getID(){
        return id;
    }
    
    public void setReadyToPlay(boolean readyToPlay){
        this.readyToPlay = readyToPlay;
    }
    
    public boolean getReadyToPlay(){
        return readyToPlay;
    }
    
    public void send(ByteBuffer buff){
        buff.flip();
        sessionRef.get().send(buff);
    }
    
    public ClientSession getSession(){
        return sessionRef.get();
    }
    
     // IServerProcessor Messages

    public void ready() {
        readyToPlay=true;
        currentGameRef.get().startGameIfReady();
    }

    public void moveMe(float startx, float starty,
                       float endx, float endy)
    {
        if (setStartXY(timestamp, startx, starty, POSITIONTOLERANCESQD)){
            destX = endx;
            destY = endy;
            
            // calc the deltas for this move based on direction and rate of movement
            float dx = destX - startX;
            float dy = destY - startY;
            if (dx != 0.0f || dy != 0.0) {
                float length = (float)Math.sqrt((dx * dx) + (dy * dy));
                float rate = (EForce.Movement.getMagnitude() / HPConverter.getInstance().convertMass(hitPoints)) * 0.01f;

                // normalize the x,y and multiply by the move per ms
                deltaX = (dx / length) * rate;
                deltaY = (dy / length) * rate;
            } else {
                deltaX = 0.0f;
                deltaY = 0.0f;
            }
            // Need collision detection here
            currentGameRef.get().send(null,
                   ServerMessages.createMoveMOBPkt(
                   id, startX, startY, destX, destY));
            this.timestamp = timestamp;
        } else {
            logger.log(Level.WARNING, "move from {0} failed check", name);
            stopMe(0.0f, 0.0f);
        }
    }

    public void attack(int targetID, float x, float y) {
        if (checkXY(timestamp, x, y, POSITIONTOLERANCESQD)) {            
            SnowmanPlayer target = currentGameRef.get().getPlayer(targetID);

            // check to see if the we can reach the target
            if (target.checkXY(timestamp, x, y, getThrowDistanceSqd()))
                currentGameRef.get().send(null,
                        ServerMessages.createAttackedPkt(id, targetID, target.doHit()));
                
            else
                currentGameRef.get().send(null,
                        ServerMessages.createAttackedPkt(id, targetID, -1));
        } else
            logger.log(Level.WARNING, "attack from {0} failed check", name);
    }

    public void getFlag(int flagID, float x, float y) {
        SnowmanGame game = currentGameRef.get();
        SnowmanFlag flag = game.getFlag(flagID);
        
        // Can not get flag if same team or flag is held by another player
        if (flag.getTeamColor() == teamColor ||
            flag.isHeld())
            return;
        
        if (checkXY(timestamp, flag.getX(), flag.getY(), flag.getGoalRadius())) {
            flag.setHeldBy(this);   // TODO - save ref to flag
            game.send(null, ServerMessages.createAttachObjPkt(id, flagID));
        } else
            logger.log(Level.WARNING, "set flag from {0} failed check", name);
    }
    
    public void score(float x, float y) {
        
    }

    private void stopMe(float x, float y) {
        Position current = getCurrentPos(timestamp);
        setTimestampLocation(timestamp, current.x, current.y);
        currentGameRef.get().send(
                    null,
                    ServerMessages.createStopMOBPkt(id, startX, startY));
    }
    
    public float getThrowDistanceSqd() {
        //TODO put in Yis function
        return 10.0f; // temporary
    }
    
    // respawn
    public void setHP(int hp){
        appContext.getDataManager().markForUpdate(this);
        hitPoints = hp;
        //FIXME - update the respawn position
        currentGameRef.get().send(null,
                                  ServerMessages.createRespawnPkt(id, 0.0f, 0.0f));
    }
    
    public int doHit(){
        if (hitPoints>0) { // not already dead
            hitPoints--;
            if (hitPoints <= 0) { // newly dead
                // TODO - drop flag
                appContext.getTaskManager().scheduleTask(
                        new RespawnTask(appContext.getDataManager().createReference((SnowmanPlayer)this)),
                                        DEATHDELAYMS);
            }
        }
        return hitPoints;
    }
    
    static private class RespawnTask implements Task, Serializable {
        final ManagedReference<SnowmanPlayer> playerRef;
        
        RespawnTask(ManagedReference<SnowmanPlayer> playerRef) {
            this.playerRef = playerRef;
        }
        
        public void run() throws Exception {
            playerRef.get().setHP(100);
        }           
    }
    
    public SnowmanGame getGame() {
        return currentGameRef == null ? null : currentGameRef.get();
    }

    public String getName() {
        return name;
    }

    public IServerProcessor getProcessor() {
        return this;
    }

    public ETeamColor getTeamColor() {
        return teamColor;
    }

    public void setLocation(float x, float y) {
        startX = destX = x;
        startY = destY = y;
    }
}