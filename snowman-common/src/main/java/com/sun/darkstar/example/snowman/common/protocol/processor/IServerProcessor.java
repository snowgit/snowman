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

package com.sun.darkstar.example.snowman.common.protocol.processor;


/**
 * <code>IServerProcessor</code> defines the interface which handles processing
 * messages received by the server from the client.
 * <p>
 * <code>IServerProcessor</code> is directly invoked by <code>ServerProtocol</code>
 * after parsing a received packet.
 * 
 * @author Yi Wang (Neakor)
 * @author Jeffrey Kesselman
 * @author Owen Kellett
 * @version Creation date: 05-29-08 11:44 EST
 * @version Modified date: 06-03-08 10:58 EST
 */
public interface IServerProcessor extends IProtocolProcessor {

    /**
     * Flag the sending client to be ready for battle.
     */
    public void ready();

    /**
     * Move the sending client to the given position.
     * @param timestamp timestamp that the client begins the move
     * @param x The x coordinate of the start point.
     * @param y The y coordinate of the start point.
     * @param endx The x coordinate of the end point.
     * @param endy The y coordinate of the end point.
     */
    public void moveMe(long timestamp, float x, float y, float endx, float endy);

    /**
     * Attack the target with given ID number with the sending client.
     * @param timestamp timestamp that the client initiated the attack
     * @param targetID The ID number of the target.
     * @param x The x coordinate of the attacker.
     * @param y The y coordinate of the attacker.
     */
    public void attack(long timestamp, int targetID, float x, float y);

    /**
     * Attach the flag with given ID number to the sending client.
     * @param timestamp timestamp that the client initiated the get flag
     * @param flagID The ID number of the flag.
     */
    public void getFlag(long timestamp, int flagID);
    
    /**
     * Stop the sending client at the given position
     * @param timestamp timestamp that the client stopped.
     * @param x The x coordinate of the stop position
     * @param y The y coordinate of the stop position
     */
    public void stopMe(long timestamp, float x, float y);
}
