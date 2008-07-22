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
package com.sun.darkstar.example.snowman.server;

import com.sun.darkstar.example.snowman.common.protocol.ClientProtocol;
import com.sun.darkstar.example.snowman.common.protocol.ServerProtocol;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.processor.IClientProcessor;
import com.sun.darkstar.example.snowman.common.protocol.processor.IServerProcessor;
import com.sun.darkstar.example.snowman.server.SnowmanFlag.TEAMCOLOR;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.NameNotBoundException;
import com.sun.sgs.app.Task;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

/**
 * This class is the player's "proxy" in the world of managed
 * objects.  It implements the ClientSessionListener interface so that
 * it can be the reception point for all client session events.
 * For here, it will call other managed ibjects to respond to thsoe
 * events.
 * @author Jeffrey Kesselman
 */
class SnowmanPlayer implements Serializable, ManagedObject, 
        ClientSessionListener, IServerProcessor {
    private static Logger logger = Logger.getLogger(SnowmanPlayer.class.getName());
    public static final long serialVersionUID = 1L;
    private static final String PREFIX = "__PLAYER_";
    private static long DEATHDELAYMS = 10 * 1000;
    private static float POSITIONTOLERANCESQD = .5f * .5f;
    private ManagedReference<ClientSession> sessionRef;
    private String name;
    private int wins;
    private int losses;
    private int id;
    float startX;
    float startY;
    long timestamp;
    float destX;
    float destY;
    float deltaX;
    float deltaY;
    TEAMCOLOR teamColor;
    private ManagedReference<SnowmanGame> currentGameRef;
    private ManagedReference<Matchmaker> currentMatchMakerRef;
    private boolean readyToPlay = false;
    private int hitPoints = 10;
    
   
    

    static SnowmanPlayer find(ClientSession arg0) {
        String pname = PREFIX + arg0.getName();
        SnowmanPlayer player;
        try {
            player = (SnowmanPlayer) AppContext.getDataManager().getBinding(pname);
            player.setSession(arg0);
        } catch (NameNotBoundException e) {
            player = new SnowmanPlayer(arg0);
            AppContext.getDataManager().setBinding(pname, player);

        }
        return player;
    }
   
   

    private SnowmanPlayer(ClientSession session) {
        name = session.getName();
        setSession(session);
    }
    
    public void reset(){
        setHP(10);
    }

    public void receivedMessage(ByteBuffer arg0) {
        ClientProtocol.getInstance().parsePacket(arg0,this);
    }

    public void disconnected(boolean arg0) {
        if (currentMatchMakerRef!=null){
            currentMatchMakerRef.get().removeWaitingPlayer(this);
            currentMatchMakerRef = null;
        }
        if (currentGameRef != null) {
            currentGameRef.get().removePlayer(this);
            currentGameRef = null;
        }
        readyToPlay = false;
        logger.info("Player "+name+" logged out");
    }

    void setID(int id) {
        this.id = id;
    }

    void setMatchMaker(Matchmaker matcher) {
        currentMatchMakerRef = 
                AppContext.getDataManager().createReference(matcher);
    }

    void setPosition(float x, float y) {
       startX = destX = x;
       startY = destY = y;
       deltaX = deltaY = 0;
    }

    void setTeamColor(TEAMCOLOR color) {
        AppContext.getDataManager().markForUpdate(this);
        teamColor = color;
    }

    private float getMovePerMS() {
        //TODO: replace with real Hp based values
        return 10;
    }

    private void setSession(ClientSession arg0) {
        sessionRef = AppContext.getDataManager().createReference(arg0);
    }
    
    public void setArea(SnowmanGame game){
        if (game == null){
            currentGameRef = null;
        } else {
            currentGameRef = AppContext.getDataManager().createReference(game);
            currentMatchMakerRef = null;
        }
    }
    
    public float ranking(){
        return 1000f*wins/losses;
    }
    
    public float getX(long time){
        if ((deltaX==0)&&(deltaY==0)){ // stopped
            return startX;
        } else {
            // interpolate
            long dur = time - timestamp;
            return startX + (deltaX*dur);
        }
    }
    
    public float getY(long time){
        if ((deltaX==0)&&(deltaY==0)){ // stopped
            return startY;
        } else {
            long dur = time - timestamp;
            return startY + (deltaY*dur);
        }
    }
    
    private boolean checkXY(long time, float xPrime, float yPrime){
        float currentX = getX(time);
        float currentY = getY(time);
        float dx = currentX - xPrime;
        float dy = currentY - yPrime;
        return ((dx*dx)+(dy*dy) < POSITIONTOLERANCESQD);
    }
    
    public int getID(){
        return id;
    }
    
    public boolean isReadyToPlay(){
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
        currentGameRef.get().checkReadyToPlay();
    }

    public void moveMe(long timestamp, float x, float y, float endx, float endy) {
       if (checkXY(timestamp,x,y)){
           startX = x;
           startY = y;
           float dx = endx-x;
           float dy = endy - y;
           float dist = (float)Math.sqrt((dx*dx)+(dy*dy));
           float time = dist/getMovePerMS();
           deltaX = dx/time;
           deltaY = dy/time;
           this.timestamp = timestamp;
           currentGameRef.get().send(null,
                   ServerProtocol.getInstance().createMoveMOBPkt(
                   id, startX, startX, endx, endy, timestamp));
       }
    }

    public void attack(long timestamp, int targetID, float x, float y) {
        if (checkXY(timestamp,x,y)){
            currentGameRef.get().attack(this,x,y,targetID,timestamp);
        }
    }

    public void getFlag(int flagID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void stopMe(long timestamp, float x, float y) {
        if (checkXY(timestamp,x,y)){
            setPosition(x,y);
            currentGameRef.get().send(
                    null,
                    ServerProtocol.getInstance().createStopMOBPkt(id, x, y));
        }
    }
    
    public float getThrowDistanceSqd(){
        //TODO put in Yis function
        return 10.0f; // temporary
    }
    
    public void setHP(int hp){
        AppContext.getDataManager().markForUpdate(this);
        hitPoints = hp;
        currentGameRef.get().send(null, 
                ServerProtocol.getInstance().createSetHPPkt(id, hitPoints));
        if (hitPoints<=0){ // newly dead
            AppContext.getTaskManager().scheduleTask(new Task(){
                ManagedReference<SnowmanPlayer> playerRef = 
                        AppContext.getDataManager().createReference(
                            SnowmanPlayer.this);
                public void run() throws Exception {
                    playerRef.get().reset();
                }
            }, DEATHDELAYMS);
        }
    }
    
    public void doHit(){
        if (hitPoints<=0){ // already dead
            setHP(hitPoints-1);
        }
        
    }

   
   
    
}
