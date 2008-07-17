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
import java.util.logging.Logger;

import com.sun.darkstar.example.snowman.common.protocol.enumn.EOPCODE;
import com.sun.darkstar.example.snowman.common.protocol.processor.IProtocolProcessor;

/**
 * <code>Protocol</code> defines the base abstraction for all protocols shared by the
 * server and the client.
 * <p>
 * Subclasses of <code>Protocol</code> have to implement the particular parsePacket method
 * in order to be able to parsing received packets.
 * 
 * @author Yi Wang (Neakor)
 * @author Jeffrey Kesselman
 * @author Owen Kellett
 * @version Creation date: 05-29-08 11:09 EST
 * @version Modified date: 06-17-08 10:45 EST
 */
public abstract class Protocol {
    /**
     * The ID number of the Game terrain.
     */
    public static final int TERRAINID = 0;
    /**
     * The <code>Logger</code> instance.
     */
    protected final Logger logger = Logger.getLogger(Protocol.class.toString());
    
    /**
     * Create a READY message packet.  There is no payload associated with
     * a READY message except for the standard timestamp.
     * @return A <code>ByteBuffer</code> "ready" packet
     */
    public ByteBuffer createReadyPkt() {
        byte[] bytes = new byte[1 + 8];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.put((byte)EOPCODE.READY.ordinal());
        buffer.putLong(System.currentTimeMillis());
        return buffer;
    }
    
    /**
     * Parse the given <code>ByteBuffer</code> packet then invoke the corresponding
     * method in given <code>ProtocolProcessor</code>.
     * @param packet The <code>ByteBuffer</code> packet to be parsed.
     * @param processor The <code>ProtocolProcessor</code> to be invoked.
     */
    public void parsePacket(ByteBuffer packet, IProtocolProcessor processor) {
        byte opbyte = packet.get();
        if ((opbyte < 0) || (opbyte > EOPCODE.values().length - 1)) {
            this.logger.severe("Unknown op value: " + opbyte);
            return;
        }
        EOPCODE code = EOPCODE.values()[opbyte];
        // Parse common code.
        switch (code) {
            case READY:
                //discard timestamp
                packet.getLong();
                processor.ready();
                break;
            // Pass message down to subclasses.
            default:
                this.parsePacket(code, packet, processor);
                break;
        }
    }
    
    /**
     * Parse the given packet with given processor based on given code.
     * @param code The <code>EOPCODE</code> enumeration.
     * @param packet The <code>ByteBuffer</code> packet.
     * @param processor The <code>IProtocolProcessor</code> unit.
     */
    protected abstract void parsePacket(EOPCODE code, ByteBuffer packet, IProtocolProcessor processor);
}
