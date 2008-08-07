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

package com.sun.darkstar.example.snowman.server.interfaces;

import com.sun.darkstar.example.snowman.common.protocol.processor.IServerProcessor;
import com.sun.darkstar.example.snowman.common.protocol.enumn.ETeamColor;
import com.sun.sgs.app.ClientSession;
import java.nio.ByteBuffer;

/**
 * The <code>SnowmanPlayer</code> interface defines the basic behavior
 * of a player in the snowman game.
 * 
 * @author Owen Kellett
 */
public interface SnowmanPlayer extends DynamicEntity
{
    /**
     * Get the name of the player
     * @return
     */
    public String getName();

    /**
     * Set the location of the player
     * @param x
     * @param y
     */
    public void setLocation(float x, float y);

    /**
     * Set the team of the player
     * @param color
     */
    public void setTeamColor(ETeamColor color);
    public ETeamColor getTeamColor();

    /**
     * Set the game that the player is in
     * @param game
     */
    public void setGame(SnowmanGame game);
    public SnowmanGame getGame();
    
    public float ranking();
    
    public void setReadyToPlay(boolean readyToPlay);
    public boolean getReadyToPlay();
    
    /**
     * Revive player, set hitpoints back to maximum value,
     * and relocate it to a respawn position
     */
    public void respawn();
    
    /**
     * Hit this snowman with the given hit point value
     * @param hp
     * @return the number of hit points deducted from the player's value
     */
    public int hit(int hp);
    
    public void setSession(ClientSession session);
    public ClientSession getSession();
    
    /**
     * Send a message to the player
     * @param buff
     */
    public void send(ByteBuffer buff);
    /**
     * Get the protocol processor that processes messages for this player
     * @return
     */
    public IServerProcessor getProcessor();
}
