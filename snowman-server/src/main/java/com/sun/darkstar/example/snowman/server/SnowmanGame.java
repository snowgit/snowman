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

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.Channel;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.Delivery;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * This object represents an actual running game session of Project Snowman,
 * @author Jeffrey Kesselman
 */
class SnowmanGame implements ManagedObject, Serializable {
    static final long serialVersionUID = 1L;
    static final String CHANPREFIX = "_GAMECHAN_";
    ManagedReference<Channel> channelRef;
        
    static SnowmanGame create(String name, ManagedReference<SnowmanPlayer>[] playerRefs) {
        return new SnowmanGame(name, playerRefs);
    }
    
    private ManagedReference<SnowmanPlayer>[] playerRefs;
    
    private SnowmanGame(String gameName, ManagedReference<SnowmanPlayer>[] playerRefs){
        this.playerRefs = 
                new ManagedReference[playerRefs.length];
        System.arraycopy(playerRefs, 0, this.playerRefs, 0,
                playerRefs.length);
        channelRef = AppContext.getDataManager().createReference(
                AppContext.getChannelManager().createChannel(
                CHANPREFIX+gameName, null, Delivery.RELIABLE));
    }
    
    public void send(ClientSession sess, ByteBuffer buff){
        buff.flip();
        channelRef.get().send(sess, buff);
    }

}
