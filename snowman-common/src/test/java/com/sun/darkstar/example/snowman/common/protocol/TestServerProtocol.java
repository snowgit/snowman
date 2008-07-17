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

import com.sun.darkstar.example.snowman.common.protocol.enumn.EOPCODE;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.processor.IServerProcessor;
import java.nio.ByteBuffer;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.easymock.EasyMock;

/**
 * Test the ServerProtocol
 * 
 * @author Owen Kellett
 */
public class TestServerProtocol extends AbstractTestProtocol
{
    @Before
    public void initializeSource() {
        this.setSource(ServerProtocol.getInstance());
    }
    
    @Test
    public void testCreateNewgamePkt() {
        ServerProtocol test = (ServerProtocol)this.getSource();
        ByteBuffer packet = test.createNewGamePkt(10, "map");
        packet.flip();
        this.checkOpcodeAndTimestamp(packet, EOPCODE.NEWGAME);
        
        int id = packet.getInt();
        int length = packet.getInt();
        byte[] mapname = new byte[length];
        packet.get(mapname);
        String mapnameString = new String(mapname);
        
        Assert.assertEquals(id, 10);
        Assert.assertEquals(length, "map".length());
        Assert.assertEquals(mapnameString, "map");
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    @Test
    public void testCreateStartgamePkt() {
        ServerProtocol test = (ServerProtocol)this.getSource();
        ByteBuffer packet = test.createStartGamePkt();
        packet.flip();
        this.checkOpcodeAndTimestamp(packet, EOPCODE.STARTGAME);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    @Test
    public void testCreateEndgamePkt() {
        ServerProtocol test = (ServerProtocol)this.getSource();
        ByteBuffer packet = test.createEndGamePkt(EEndState.WIN);
        packet.flip();
        this.checkOpcodeAndTimestamp(packet, EOPCODE.ENDGAME);
        
        EEndState state = EEndState.values()[packet.getInt()];
        
        Assert.assertEquals(state, EEndState.WIN);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    @Test
    public void testCreateAddMOBPkt() {
        ServerProtocol test = (ServerProtocol)this.getSource();
        ByteBuffer packet = test.createAddMOBPkt(10, 1.0f, 2.0f, EMOBType.SNOWMAN);
        packet.flip();
        this.checkOpcodeAndTimestamp(packet, EOPCODE.ADDMOB);
        
        int id = packet.getInt();
        float x = packet.getFloat();
        float y = packet.getFloat();
        EMOBType type = EMOBType.values()[packet.getInt()];
        
        Assert.assertEquals(id, 10);
        Assert.assertEquals(x, 1.0f);
        Assert.assertEquals(y, 2.0f);
        Assert.assertEquals(type, EMOBType.SNOWMAN);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    @Test
    public void testCreateRemoveMOBPkt() {
        ServerProtocol test = (ServerProtocol)this.getSource();
        ByteBuffer packet = test.createRemoveMOBPkt(10);
        packet.flip();
        this.checkOpcodeAndTimestamp(packet, EOPCODE.REMOVEMOB);
        
        int id = packet.getInt();

        Assert.assertEquals(id, 10);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    @Test
    public void testCreateMoveMOBPkt() {
        ServerProtocol test = (ServerProtocol)this.getSource();
        ByteBuffer packet = test.createMoveMOBPkt(10, 1.0f, 2.0f, 3.0f, 4.0f, 1234l);
        packet.flip();
        this.checkOpcodeAndTimestamp(packet, EOPCODE.MOVEMOB);
        
        int id = packet.getInt();
        float startx = packet.getFloat();
        float starty = packet.getFloat();
        float endx = packet.getFloat();
        float endy = packet.getFloat();
        long timestart = packet.getLong();

        Assert.assertEquals(id, 10);
        Assert.assertEquals(startx, 1.0f);
        Assert.assertEquals(starty, 2.0f);
        Assert.assertEquals(endx, 3.0f);
        Assert.assertEquals(endy, 4.0f);
        Assert.assertEquals(timestart, 1234l);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    @Test
    public void testCreateStopMOBPkt() {
        ServerProtocol test = (ServerProtocol)this.getSource();
        ByteBuffer packet = test.createStopMOBPkt(10, 1.0f, 2.0f);
        packet.flip();
        this.checkOpcodeAndTimestamp(packet, EOPCODE.STOPMOB);
        
        int id = packet.getInt();
        float x = packet.getFloat();
        float y = packet.getFloat();

        Assert.assertEquals(id, 10);
        Assert.assertEquals(x, 1.0f);
        Assert.assertEquals(y, 2.0f);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    @Test
    public void testCreateAttachObjPkt() {
        ServerProtocol test = (ServerProtocol)this.getSource();
        ByteBuffer packet = test.createAttachObjPkt(10, 20);
        packet.flip();
        this.checkOpcodeAndTimestamp(packet, EOPCODE.ATTACHOBJ);
        
        int source = packet.getInt();
        int target = packet.getInt();

        Assert.assertEquals(source, 10);
        Assert.assertEquals(target, 20);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    @Test
    public void testCreateAttackedPkt() {
        ServerProtocol test = (ServerProtocol)this.getSource();
        ByteBuffer packet = test.createAttackedPkt(10, 20);
        packet.flip();
        this.checkOpcodeAndTimestamp(packet, EOPCODE.ATTACKED);
        
        int source = packet.getInt();
        int target = packet.getInt();

        Assert.assertEquals(source, 10);
        Assert.assertEquals(target, 20);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    @Test
    public void testCreateSetHPPkt() {
        ServerProtocol test = (ServerProtocol)this.getSource();
        ByteBuffer packet = test.createSetHPPkt(10, 20);
        packet.flip();
        this.checkOpcodeAndTimestamp(packet, EOPCODE.SETHP);
        
        int source = packet.getInt();
        int target = packet.getInt();

        Assert.assertEquals(source, 10);
        Assert.assertEquals(target, 20);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    /**
     * Test that the proper processor methods are called when
     * packets are sent to the ClientProtocol
     */
    @Test
    public void parseMoveMe() {
        ClientProtocol packetGenerator = ClientProtocol.getInstance();
        IServerProcessor mockProcessor = EasyMock.createMock(IServerProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createMoveMePkt(1.0f, 2.0f, 3.0f, 4.0f);
        packet.flip();
        // record current time
        long now = System.currentTimeMillis();
        // record expected processor calls
        mockProcessor.moveMe(EasyMock.leq(now),
                             EasyMock.eq(1.0f),
                             EasyMock.eq(2.0f),
                             EasyMock.eq(3.0f),
                             EasyMock.eq(4.0f));
        EasyMock.replay(mockProcessor);        
        // send it to the parser
        getSource().parsePacket(packet, mockProcessor);
        //verify
        EasyMock.verify(mockProcessor);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    /**
     * Test that the proper processor methods are called when
     * packets are sent to the ClientProtocol
     */
    @Test
    public void parseAttack() {
        ClientProtocol packetGenerator = ClientProtocol.getInstance();
        IServerProcessor mockProcessor = EasyMock.createMock(IServerProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createAttackPkt(10, 1.0f, 2.0f);
        packet.flip();
        // record current time
        long now = System.currentTimeMillis();
        // record expected processor calls
        mockProcessor.attack(EasyMock.leq(now),
                             EasyMock.eq(10),
                             EasyMock.eq(1.0f),
                             EasyMock.eq(2.0f));
        EasyMock.replay(mockProcessor);        
        // send it to the parser
        getSource().parsePacket(packet, mockProcessor);
        //verify
        EasyMock.verify(mockProcessor);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    /**
     * Test that the proper processor methods are called when
     * packets are sent to the ClientProtocol
     */
    @Test
    public void parseGetFlag() {
        ClientProtocol packetGenerator = ClientProtocol.getInstance();
        IServerProcessor mockProcessor = EasyMock.createMock(IServerProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createGetFlagPkt(10);
        packet.flip();
        // record expected processor calls
        mockProcessor.getFlag(10);
        EasyMock.replay(mockProcessor);        
        // send it to the parser
        getSource().parsePacket(packet, mockProcessor);
        //verify
        EasyMock.verify(mockProcessor);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    /**
     * Test that the proper processor methods are called when
     * packets are sent to the ClientProtocol
     */
    @Test
    public void parseStop() {
        ClientProtocol packetGenerator = ClientProtocol.getInstance();
        IServerProcessor mockProcessor = EasyMock.createMock(IServerProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createStopMePkg(1.0f, 2.0f);
        packet.flip();
        // record current time
        long now = System.currentTimeMillis();
        // record expected processor calls
        mockProcessor.stopMe(EasyMock.leq(now),
                             EasyMock.eq(1.0f),
                             EasyMock.eq(2.0f));
        EasyMock.replay(mockProcessor);        
        // send it to the parser
        getSource().parsePacket(packet, mockProcessor);
        //verify
        EasyMock.verify(mockProcessor);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(packet.hasRemaining());
    }
    
    @After
    public void clearSource() {
        this.setSource(null);
    }
}
