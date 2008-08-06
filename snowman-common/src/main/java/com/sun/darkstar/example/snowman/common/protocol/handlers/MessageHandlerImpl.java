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

package com.sun.darkstar.example.snowman.common.protocol.handlers;

import java.nio.ByteBuffer;
import java.util.logging.Logger;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.ETeamColor;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EOPCODE;
import com.sun.darkstar.example.snowman.common.protocol.processor.IProtocolProcessor;
import com.sun.darkstar.example.snowman.common.protocol.processor.IClientProcessor;
import com.sun.darkstar.example.snowman.common.protocol.processor.IServerProcessor;

/**
 * Default implementation of the <code>MessageHandler</code> interface.
 * 
 * @author Yi Wang (Neakor)
 * @author Jeffrey Kesselman
 * @author Owen Kellett
 */
public class MessageHandlerImpl implements MessageHandler
{
    private static MessageHandler instance;
    protected MessageHandlerImpl() {}
    public static MessageHandler getInstance() {
        if(instance == null) {
            MessageHandlerImpl.instance = new MessageHandlerImpl();
        }
        return instance;
    }
    
    /**
     * The <code>Logger</code> instance.
     */
    protected final Logger logger = Logger.getLogger(MessageHandlerImpl.class.toString());
    
    /* {@inheritDoc} */
    public void parseClientPacket(ByteBuffer packet, IClientProcessor processor) {
        EOPCODE code = this.getOpCode(packet);
        this.parseClientPacket(code, packet, processor);
    }
    
    /* {@inheritDoc} */
    public void parseServerPacket(ByteBuffer packet, IServerProcessor processor) {
        EOPCODE code = this.getOpCode(packet);
        this.parseServerPacket(code, packet, processor);
    }
    
    /**
     * Parses the given packet with the given opcode and hands off the data
     * to the appropriate IClientProcessor method with data from the packet
     * sent in as parameters.
     * 
     * @param code opcode of the packet
     * @param packet data packet with the read head at the start of the payload
     * @param unit processing unit to receive and process the data
     */
    protected void parseClientPacket(EOPCODE code, ByteBuffer packet, IClientProcessor unit) {
        switch (code) {
            case NEWGAME:
                int myID = packet.getInt();
                byte[] mapname = new byte[packet.getInt()];
                packet.get(mapname);
                unit.newGame(myID, new String(mapname));
                break;
            case STARTGAME:
                unit.startGame();
                break;
            case ENDGAME:
                unit.endGame(EEndState.values()[packet.getInt()]);
                break;
            case ADDMOB:
                unit.addMOB(packet.getInt(), 
                            packet.getFloat(),
                            packet.getFloat(),
                            EMOBType.values()[packet.getInt()],
                            ETeamColor.values()[packet.getInt()]);
                break;
            case REMOVEMOB:
                unit.removeMOB(packet.getInt());
                break;
            case MOVEMOB:
                unit.moveMOB(packet.getInt(), 
                             packet.getFloat(),
                             packet.getFloat(),
                             packet.getFloat(), 
                             packet.getFloat());
                break;
            case STOPMOB:
                unit.stopMOB(packet.getInt(),
                             packet.getFloat(),
                             packet.getFloat());
                break;
            case ATTACHOBJ:
                unit.attachObject(packet.getInt(),
                                  packet.getInt());
                break;
            case ATTACKED:
                unit.attacked(packet.getInt(), 
                              packet.getInt(),
                              packet.getInt());
                break;
            case RESPAWN:
                unit.respawn(packet.getInt(), 
                             packet.getFloat(), 
                             packet.getFloat());
                break;
            default:
                //divert to common parser
                this.parseCommonPacket(code, packet, unit);
        }
    }
    
    /**
     * Parses the given packet with the given opcode and hands off the data
     * to the appropriate IServerProcessor method with data from the packet
     * sent in as parameters.
     * 
     * @param code opcode of the packet
     * @param packet data packet with the read head at the start of the payload
     * @param unit processing unit to receive and process the data
     */
    protected void parseServerPacket(EOPCODE code, ByteBuffer packet, IServerProcessor unit) {
        switch (code) {
            case MOVEME:
                unit.moveMe(packet.getFloat(), 
                            packet.getFloat(),
                            packet.getFloat(),
                            packet.getFloat());
                break;
            case ATTACK:
                unit.attack(packet.getInt(),
                            packet.getFloat(), 
                            packet.getFloat());
                break;
            case GETFLAG:
                unit.getFlag(packet.getInt(),
                             packet.getFloat(),
                             packet.getFloat());
                break;
            case SCORE:
                unit.score(packet.getFloat(),
                           packet.getFloat());
                break;
            default:
                //divert to common parser
                this.parseCommonPacket(code, packet, unit);
        }
    }
    
    
    /**
     * Parse the given <code>ByteBuffer</code> packet then invoke the corresponding
     * method in given <code>ProtocolProcessor</code>.
     * @param packet The <code>ByteBuffer</code> packet to be parsed.
     * @param processor The <code>ProtocolProcessor</code> to be invoked.
     */
    private void parseCommonPacket(EOPCODE code, ByteBuffer packet, IProtocolProcessor processor) {
        // Parse common code.
        switch (code) {
            case READY:
                processor.ready();
                break;
            default:
                this.logger.info("Unsupported OPCODE: " + code.toString());
        }
    }
    
    /**
     * Get the OPCODE from the packet
     * @param packet
     * @return
     */
    private EOPCODE getOpCode(ByteBuffer packet) 
    {
        byte opbyte = packet.get();
        if ((opbyte < 0) || (opbyte > EOPCODE.values().length - 1)) {
            this.logger.severe("Unknown op value: " + opbyte);
            return null;
        }
        EOPCODE code = EOPCODE.values()[opbyte];
        
        return code;
    }
}
