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
import com.sun.darkstar.example.snowman.common.util.HPConverter;
import com.sun.darkstar.example.snowman.server.interfaces.TeamColor;
import com.sun.darkstar.example.snowman.server.context.SnowmanAppContext;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanFlag;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ManagedObject;
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
class SnowmanPlayerImpl implements SnowmanPlayer, Serializable, 
                                   ManagedObject, IServerProcessor
{
    public static final long serialVersionUID = 1L;

    private static Logger logger = Logger.getLogger(SnowmanPlayerImpl.class.getName());
    
    private static long DEATHDELAYMS = 10 * 1000;
    private static float POSITIONTOLERANCESQD = 1.0f;//.5f * .5f;
    
    private ManagedReference<ClientSession> sessionRef;
    private String name;
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
    private TeamColor teamColor;
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
    
    public void reset(){
        setHP(100);
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

    public void setTeamColor(TeamColor color) {
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
    
    public float getX(long time){
        return startX;
    }
    
    public float getY(long time){
        return startY;
    }
    
    public boolean checkXY(long time, float xPrime, float yPrime, float tolerance) {
        float expectedX = startX;
        float expectedY = startY;
        if ((deltaX != 0) || (deltaY != 0)){ // moving
            long dt = time - timestamp;
            expectedX += (deltaX * dt);
            expectedY += (deltaY * dt);
//            System.out.println("new expectedXY= " + expectedX + "," + expectedY +
//                             "\ndestXY=         " + destX + "," + destY);
            // clip the new position to the destination
            float dx = expectedX - startX;
            float dy = expectedY - startY;
            float newLengthSq = (dx*dx) + (dy*dy);
            dx = destX - startX;
            dy = destY - startY;
            float maxLengthSq = (dx*dx) + (dy*dy);
            if (newLengthSq > maxLengthSq) {
                expectedX = destX;
                expectedY = destY;
            }
        }
//        System.out.println("move  dx,dy= " + deltaX + ","+deltaY +
//                         "\nexpected xy= " + expectedX + "," + expectedY +
//                         "\nentered  xy= " + xPrime + "," + yPrime);
        float dx = expectedX - xPrime;
        float dy = expectedY - yPrime;
        float skew = (float)Math.sqrt((dx*dx)+(dy*dy)); // TODO skip sqrt
//        System.out.println("checkXY dt= " + (time - timestamp) + " skew= " + skew);
        return skew < tolerance;
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

    public void moveMe(long timestamp, float startx, float starty, float endx, float endy) {
        if (checkXY(timestamp, startx, starty, POSITIONTOLERANCESQD)){
            startX = startx;
            startY = starty;
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
        } else {
            logger.log(Level.WARNING, "move from {0} failed check", name);
            currentGameRef.get().send(null,
                    ServerMessages.createStopMOBPkt(id, startX, startY));
            deltaX = 0.0f;
            deltaY = 0.0f;
        }
        this.timestamp = timestamp;
    }

    public void attack(long timestamp, int targetID, float x, float y) {
        if (checkXY(timestamp, x, y, POSITIONTOLERANCESQD)){
            currentGameRef.get().attack(this,x,y,targetID,timestamp);
        }
    }

    public void getFlag(long timestamp, int flagID) {
        SnowmanGame game = currentGameRef.get();
        SnowmanFlag flag = game.getFlag(flagID);
        if (flag.getTeamColor() == teamColor ||
            flag.isHeld())
            return;
        
        if (checkXY(timestamp, flag.getX(timestamp), flag.getY(timestamp), flag.getGoalRadius())) {
            flag.setHeldBy(this);
            game.send(null, ServerMessages.createAttachObjPkt(id, flagID));
        }
    }

    public void stopMe(long timestamp, float x, float y) {
        if (checkXY(timestamp, x, y, POSITIONTOLERANCESQD)){
            this.setTimestampLocation(timestamp, x,y);
            currentGameRef.get().send(
                    null,
                    ServerMessages.createStopMOBPkt(id, x, y));
        }
    }
    
    public float getThrowDistanceSqd(){
        //TODO put in Yis function
        return 10.0f; // temporary
    }
    
    public void setHP(int hp){
        appContext.getDataManager().markForUpdate(this);
        hitPoints = hp;
        currentGameRef.get().send(null,
                                  ServerMessages.createSetHPPkt(id, hitPoints));
        if (hitPoints <= 0){ // newly dead
            appContext.getTaskManager().scheduleTask(
                    new RespawnTask(appContext.getDataManager().createReference((SnowmanPlayer)this)),
                                    DEATHDELAYMS);
        }
    }
    
    static private class RespawnTask implements Task, Serializable {
        final ManagedReference<SnowmanPlayer> playerRef;
        
        RespawnTask(ManagedReference<SnowmanPlayer> playerRef) {
            this.playerRef = playerRef;
        }
        
        public void run() throws Exception {
            playerRef.get().reset();
        }           
    }
    
    public void doHit(){
        if (hitPoints>0){ // not already dead
            setHP(hitPoints-1);
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

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public void setLocation(float x, float y) {
        startX = destX = x;
        startY = destY = y;
    }
}