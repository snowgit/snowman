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

import com.sun.darkstar.example.snowman.common.protocol.ServerProtocol;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.server.SnowmanFlag.TEAMCOLOR;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.Channel;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.Delivery;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * This object represents an actual running game session of Project Snowman,
 * @author Jeffrey Kesselman
 */
class SnowmanGame implements ManagedObject, Serializable {
    static final long serialVersionUID = 1L;
    static final String CHANPREFIX = "_GAMECHAN_";
    static final private int FLAGBASEID = 100;
    // player starts == x1,y1,x2,y2....
    static final float[] playerStarts = {0,0,10,0,0,10,10,10};
    // flag starts == x1,y1,x2,y2.....
    static final float[] flagStarts={1,1,9,9};
    // flag goals == x1,y1,r1,x2,y2,r2 ...
    static final float[] flagGoals={1,1,1,9,9,1};
    ManagedReference<Channel> channelRef;
    List<ManagedReference<SnowmanFlag>> flags = 
            new ArrayList<ManagedReference<SnowmanFlag>>();
    
    
        
    static SnowmanGame create(String name) {
        return new SnowmanGame(name);
    }
    
    private List<ManagedReference<SnowmanPlayer>>  playerRefs = 
            new ArrayList<ManagedReference<SnowmanPlayer>>();
    
    private SnowmanGame(String gameName){
        
        channelRef = AppContext.getDataManager().createReference(
                AppContext.getChannelManager().createChannel(
                CHANPREFIX+gameName, null, Delivery.RELIABLE));
        
        for(TEAMCOLOR color : TEAMCOLOR.values()){
            int idx = color.ordinal();
            SnowmanFlag flag = new SnowmanFlag(color,
                    flagGoals[idx*3],flagGoals[idx*3+1],flagGoals[idx*3+2]); 
            flag.SetLocation(flagStarts[idx*2], flagStarts[idx*2+1]);
            ManagedReference<SnowmanFlag> ref =
                    AppContext.getDataManager().createReference(flag);
            flags.add(ref);
            flag.setID(FLAGBASEID+flags.indexOf(ref));
        }
    }
    
    public void send(ClientSession sess, ByteBuffer buff){
        buff.flip();
        channelRef.get().send(sess, buff);
        
    }

    public void addPlayer(SnowmanPlayer player, TEAMCOLOR color) {
        ManagedReference<SnowmanPlayer> playerRef = 
                AppContext.getDataManager().createReference(player);
        playerRefs.add(playerRef);
        int id = playerRefs.indexOf(playerRef);
        player.setID(id);
        player.setPosition(playerStarts[id*2],playerStarts[(id*2)+1]);
        player.setTeamColor(color);
        player.setArea(this);
        player.send(ServerProtocol.getInstance().createNewGamePkt(id, 
                "default_map"));
        channelRef.get().join(player.getSession());
    }
    
    public void removePlayer(SnowmanPlayer player){
         ManagedReference<SnowmanPlayer> playerRef = 
                AppContext.getDataManager().createReference(player);
         int idx = playerRefs.indexOf(playerRef);
        playerRefs.set(idx, null);
    }
    
    public void sendMapInfo(){
        long time = System.currentTimeMillis();
        for(ManagedReference<SnowmanPlayer> ref : playerRefs){
            SnowmanPlayer player = ref.get();
            send(null,ServerProtocol.getInstance().createAddMOBPkt(
                    player.getID(), player.getX(time), player.getY(time), 
                    EMOBType.SNOWMAN));
        }
        for(ManagedReference<SnowmanFlag> flagRef : flags){
            SnowmanFlag flag = flagRef.get();
            send(null,ServerProtocol.getInstance().createAddMOBPkt(
                    flag.getID(), flag.getX(time), flag.getY(time), EMOBType.FLAG));
        }
        send(null,ServerProtocol.getInstance().createReadyPkt());
        
    }
    
    public void checkReadyToPlay(){
        for(ManagedReference<SnowmanPlayer> playerRef : playerRefs){
            if (playerRef != null){
                if (!playerRef.get().isReadyToPlay()){
                    return;
                }
            }
        }
        startGame();
    }
    
    public void attack(SnowmanPlayer attacker, float x, float y, int attackedID,
            long timestamp){
        SnowmanPlayer attacked = playerRefs.get(attackedID).get();
        float dx = x-attacked.getX(timestamp);
        float dy = y-attacked.getY(timestamp);
        
        if ((dx*dx)+(dy*dy) <= attacker.getThrowDistanceSqd()){
            send(null,ServerProtocol.getInstance().createAttackedPkt(
                    attacker.getID(), attackedID));
            attacked.doHit();
        }
                
        
    }

    private void startGame() {
        send(null,ServerProtocol.getInstance().createStartGamePkt());
    }

}
