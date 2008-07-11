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
import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.processor.IClientProcessor;
import com.sun.darkstar.example.snowman.common.protocol.processor.IServerProcessor;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.NameNotBoundException;
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
    private ManagedReference<ClientSession> sessionRef;
    private String name;
    private int wins;
    private int losses;
    float x;
    float y;
    private int id;
    private ManagedReference<SnowmanGame> currentGameRef;
    private ManagedReference<Matchmaker> currentMatchMakerRef;
   
    

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

    public void receivedMessage(ByteBuffer arg0) {
        ClientProtocol.getInstance().parsePacket(arg0,this);
    }

    public void disconnected(boolean arg0) {
        if (currentMatchMakerRef!=null){
            currentMatchMakerRef.get().removeWaitingPlayer(this);
            currentMatchMakerRef = null;
        }
        logger.info("Player "+name+" logged out");
    }

    void setMatchMaker(Matchmaker matcher) {
        currentMatchMakerRef = 
                AppContext.getDataManager().createReference(matcher);
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
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
    
    public int getID(){
        return id;
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void matchMe() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void moveMe(float newx, float newy) {
        x = newx;
        y = newy;
        currentGameRef.get().send(null,
                ClientProtocol.getInstance().createMoveMePkt(x, y));
    }

    public void attack(int targetID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getFlag(int flagID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void requestInfo(int objectID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void chatText(String text) {
        currentGameRef.get().send(sessionRef.get(), 
                ClientProtocol.getInstance().createChatPkt(text));
    }

   
   
    
}
