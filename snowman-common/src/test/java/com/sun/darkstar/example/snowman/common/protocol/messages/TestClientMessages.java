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

package com.sun.darkstar.example.snowman.common.protocol.messages;

import com.sun.darkstar.example.snowman.common.protocol.enumn.EOPCODE;
import java.nio.ByteBuffer;
import org.junit.Test;
import org.junit.Assert;

/**
 * Test the ClientMessages class
 * 
 * @author Owen Kellett
 */
public class TestClientMessages extends AbstractTestMessages
{
    /**
     * Verify the header of the packet which includes a timestamp.
     * First check that the given opcode matches the first byte of
     * the packet.  Then check that the next 8 bytes are a long which
     * is equivalent to a timestamp approximately equal to now.
     * (Checks that it is within 20 ms)
     * @param packet packet to check
     * @param opcode opcode to verify against
     */
    protected void checkOpcodeAndTimestamp(ByteBuffer packet, EOPCODE opcode) {
        checkOpcode(packet, opcode);
        
        long timestamp = packet.getLong();
        long now = System.currentTimeMillis();
        
        Assert.assertTrue((now - timestamp) < 20 &&
                          (now - timestamp) >= 0);
    }
    
    @Test
    public void testCreateMoveMePkt() {
        ByteBuffer movePacket = ClientMessages.createMoveMePkt(1.0f, 2.0f, 3.0f, 4.0f);
        movePacket.flip();
        checkOpcodeAndTimestamp(movePacket, EOPCODE.MOVEME);
        
        float startx = movePacket.getFloat();
        float starty = movePacket.getFloat();
        float endx = movePacket.getFloat();
        float endy = movePacket.getFloat();
        
        Assert.assertEquals(1.0f, startx);
        Assert.assertEquals(2.0f, starty);
        Assert.assertEquals(3.0f, endx);
        Assert.assertEquals(4.0f, endy);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(movePacket.hasRemaining());
    }
    
    @Test
    public void testCreateAttackPkt() {
        ByteBuffer attackPacket = ClientMessages.createAttackPkt(5, 1.0f, 2.0f);
        attackPacket.flip();
        checkOpcodeAndTimestamp(attackPacket, EOPCODE.ATTACK);
        
        int id = attackPacket.getInt();
        float x = attackPacket.getFloat();
        float y = attackPacket.getFloat();
        
        Assert.assertEquals(5, id);
        Assert.assertEquals(1.0f, x);
        Assert.assertEquals(2.0f, y);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(attackPacket.hasRemaining());
    }
    
    @Test
    public void testCreateGetFlagPkg() {
        ByteBuffer getFlagPacket = ClientMessages.createGetFlagPkt(10);
        getFlagPacket.flip();
        checkOpcodeAndTimestamp(getFlagPacket, EOPCODE.GETFLAG);
        
        int id = getFlagPacket.getInt();
        
        Assert.assertEquals(10, id);

        //ensure we are at the end of the buffer
        Assert.assertFalse(getFlagPacket.hasRemaining());
    }
    
    @Test
    public void testCreateStopMePkt() {
        ByteBuffer stopPacket = ClientMessages.createStopMePkg(1.0f, 2.0f);
        stopPacket.flip();
        checkOpcodeAndTimestamp(stopPacket, EOPCODE.STOPME);
        
        float x = stopPacket.getFloat();
        float y = stopPacket.getFloat();
        
        Assert.assertEquals(1.0f, x);
        Assert.assertEquals(2.0f, y);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(stopPacket.hasRemaining());
    }
}
