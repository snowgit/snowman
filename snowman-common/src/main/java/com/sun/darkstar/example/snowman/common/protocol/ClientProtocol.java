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

package com.sun.darkstar.example.snowman.common.protocol;

import java.nio.ByteBuffer;

import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EOPCODE;
import com.sun.darkstar.example.snowman.common.protocol.processor.IClientProcessor;
import com.sun.darkstar.example.snowman.common.protocol.processor.IProtocolProcessor;

/**
 * <code>ClientProtocol</code> is a singleton class responsible for generating packets
 * that are sent from the client to the server.
 * <p>
 * <code>ClientProtocol</code> is also responsible for parsing the packets received
 * from the server and invoke the corresponding methods in <code>ClientProcessor</code>.
 * 
 * @author Yi Wang (Neakor)
 * @author Jeffrey Kesselman
 * @author Owen Kellett
 * @version Creation date: 05-29-08 11:11 EST
 * @version Modified date: 06-17-08 10:37 EST
 */
public class ClientProtocol extends Protocol{
    /**
     * The <code>ClientProtocol</code> instance.
     */
    private static ClientProtocol instance;

    /**
     * Constructor of <code>ClientProtocol</code>.
     */
    private ClientProtocol() {}

    /**
     * Retrieve the <code>ClientProtocol</code> instance.
     * @return The <code>ClientProtocol</code> instance.
     */
    public static ClientProtocol getInstance() {
        if (ClientProtocol.instance == null) {
            ClientProtocol.instance = new ClientProtocol();
        }
        return ClientProtocol.instance;
    }

    /**
     * Create a "move me" packet which notifies the server that this client is trying
     * to move towards the given destination.
     * @param x The x coordinate of the start point.
     * @param y The y coordinate of the start point.
     * @param endx The x coordinate of the clicked position.
     * @param endy The y coordinate of the clicked position.
     * @return The <code>ByteBuffer</code> "move me" packet.
     */
    public ByteBuffer createMoveMePkt(float x, float y, float endx, float endy) {
        byte[] bytes = new byte[1 + 8 + 16];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.put((byte) EOPCODE.MOVEME.ordinal());
        buffer.putLong(System.currentTimeMillis());
        buffer.putFloat(x);
        buffer.putFloat(y);
        buffer.putFloat(endx);
        buffer.putFloat(endy);
        return buffer;
    }

    /**
     * Create an "attack" packet which notifies the server that the sending
     * client is attacking the object with given ID from the given location.
     * @param targetID The ID number of the target.
     * @param x The x coordinate of the position of the attacker
     * @param y The y coordinate of the position of the attacker
     * @return The <code>ByteBuffer</code> "attack" packet.
     */
    public ByteBuffer createAttackPkt(int targetID, float x, float y) {
        byte[] bytes = new byte[1 + 8 + 12];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.put((byte) EOPCODE.ATTACK.ordinal());
        buffer.putLong(System.currentTimeMillis());
        buffer.putInt(targetID);
        buffer.putFloat(x);
        buffer.putFloat(y);
        return buffer;
    }


    /**
     * Create a "get flag" packet which notifies the server that this client is
     * trying to pick up the flag with given ID.
     * @param flagID The ID number of the flag the client is picking up.
     * @return The <code>ByteBuffer</code> "get flag" packet.
     */
    public ByteBuffer createGetFlagPkt(int flagID) {
        byte[] bytes = new byte[1 + 8 + 4];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.put((byte) EOPCODE.GETFLAG.ordinal());
        buffer.putLong(System.currentTimeMillis());
        buffer.putInt(flagID);
        return buffer;
    }
    
    /**
     * Create a "stop me" packet which notifies the server that this client
     * has reached its destination and has stopped at the given position.
     * @param x The x coordinate of the stop position
     * @param y The y coordinate of the stop position
     * @return The <code>ByteBuffer</code> "stop me" packet
     */
    public ByteBuffer createStopMePkg(float x, float y, long timestamp) {
        byte[] bytes = new byte[1 + 8 + 8];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.put((byte) EOPCODE.STOPME.ordinal());
        buffer.putLong(timestamp);
        buffer.putFloat(x);
        buffer.putFloat(y);
        return buffer;
    }


    @Override
    protected void parsePacket(EOPCODE code, ByteBuffer packet, IProtocolProcessor processor) {
        IClientProcessor unit = (IClientProcessor) processor;
        switch (code) {
            case NEWGAME:
                //discard timestamp
                packet.getLong();
                int myID = packet.getInt();
                byte[] mapname = new byte[packet.getInt()];
                packet.get(mapname);
                unit.newGame(myID, new String(mapname));
                break;
            case STARTGAME:
                //discard timestamp
                packet.getLong();
                unit.startGame();
                break;
            case ENDGAME:
                //discard timestamp
                packet.getLong();
                unit.endGame(EEndState.values()[packet.getInt()]);
                break;
            case ADDMOB:
                //discard timestamp
                packet.getLong();
                unit.addMOB(packet.getInt(), 
                            packet.getFloat(),
                            packet.getFloat(),
                            EMOBType.values()[packet.getInt()]);
                break;
            case REMOVEMOB:
                //discard timestamp
                packet.getLong();
                unit.removeMOB(packet.getInt());
                break;
            case MOVEMOB:
                //discard timestamp
                packet.getLong();
                unit.moveMOB(packet.getInt(), 
                             packet.getFloat(),
                             packet.getFloat(),
                             packet.getFloat(), 
                             packet.getFloat(),
                             packet.getLong());
                break;
            case STOPMOB:
                //discard timestamp
                packet.getLong();
                unit.stopMOB(packet.getInt(),
                             packet.getFloat(),
                             packet.getFloat());
                break;
            case ATTACHOBJ:
                //discard timestamp
                packet.getLong();
                unit.attachObject(packet.getInt(),
                                  packet.getInt());
                break;
            case ATTACKED:
                //discard timestamp
                packet.getLong();
                unit.attacked(packet.getInt(), 
                              packet.getInt());
                break;
            case SETHP:
                //discard timestamp
                packet.getLong();
                unit.setHP(packet.getInt(), 
                           packet.getInt());
                break;
            default:
                this.logger.info("Unsupported OPCODE: " + code.toString());
        }
    }
}
