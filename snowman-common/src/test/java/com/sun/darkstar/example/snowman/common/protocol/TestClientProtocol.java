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
import com.sun.darkstar.example.snowman.common.protocol.processor.IClientProcessor;
import java.nio.ByteBuffer;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.easymock.EasyMock;

/**
 * Test packet creation with the Client Protocol
 * 
 * @author Owen Kellett
 */
public class TestClientProtocol extends AbstractTestProtocol
{

    @Before
    public void initializeSource() {
        this.setSource(ClientProtocol.getInstance());
    }
    
    
    @Test
    public void testCreateMoveMePkt() {
        ClientProtocol test = (ClientProtocol)this.getSource();
        ByteBuffer movePacket = test.createMoveMePkt(1.0f, 2.0f, 3.0f, 4.0f);
        movePacket.flip();
        this.checkOpcodeAndTimestamp(movePacket, EOPCODE.MOVEME);
        
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
        ClientProtocol test = (ClientProtocol)this.getSource();
        ByteBuffer attackPacket = test.createAttackPkt(5, 1.0f, 2.0f);
        attackPacket.flip();
        this.checkOpcodeAndTimestamp(attackPacket, EOPCODE.ATTACK);
        
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
        ClientProtocol test = (ClientProtocol)this.getSource();
        ByteBuffer getFlagPacket = test.createGetFlagPkt(10);
        getFlagPacket.flip();
        this.checkOpcodeAndTimestamp(getFlagPacket, EOPCODE.GETFLAG);
        
        int id = getFlagPacket.getInt();
        
        Assert.assertEquals(10, id);

        //ensure we are at the end of the buffer
        Assert.assertFalse(getFlagPacket.hasRemaining());
    }
    
    @Test
    public void testCreateStopMePkt() {
        ClientProtocol test = (ClientProtocol)this.getSource();
        ByteBuffer stopPacket = test.createStopMePkg(1.0f, 2.0f);
        stopPacket.flip();
        this.checkOpcodeAndTimestamp(stopPacket, EOPCODE.STOPME);
        
        float x = stopPacket.getFloat();
        float y = stopPacket.getFloat();
        
        Assert.assertEquals(1.0f, x);
        Assert.assertEquals(2.0f, y);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(stopPacket.hasRemaining());
    }
    
    /**
     * Test that the proper processor methods are called when
     * packets are sent to the ClientProtocol
     */
    @Test
    public void parseNewgame() {
        ServerProtocol packetGenerator = ServerProtocol.getInstance();
        IClientProcessor mockProcessor = EasyMock.createMock(IClientProcessor.class);

        // generate packet
        ByteBuffer newgame = packetGenerator.createNewGamePkt(10, "map");
        newgame.flip();
        // record expected processor calls
        mockProcessor.newGame(10, "map");
        EasyMock.replay(mockProcessor);
        // send it to the parser
        getSource().parsePacket(newgame, mockProcessor);
        //verify
        EasyMock.verify(mockProcessor);
        
        //ensure we are at the end of the buffer
        Assert.assertFalse(newgame.hasRemaining());
    }
    
    /**
     * Test that the proper processor methods are called when
     * packets are sent to the ClientProtocol
     */
    @Test
    public void parseStartgame() {
        ServerProtocol packetGenerator = ServerProtocol.getInstance();
        IClientProcessor mockProcessor = EasyMock.createMock(IClientProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createStartGamePkt();
        packet.flip();
        // record expected processor calls
        mockProcessor.startGame();
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
    public void parseEndgame() {
        ServerProtocol packetGenerator = ServerProtocol.getInstance();
        IClientProcessor mockProcessor = EasyMock.createMock(IClientProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createEndGamePkt(EEndState.WIN);
        packet.flip();
        // record expected processor calls
        mockProcessor.endGame(EEndState.WIN);
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
    public void parseAddMOB() {
        ServerProtocol packetGenerator = ServerProtocol.getInstance();
        IClientProcessor mockProcessor = EasyMock.createMock(IClientProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createAddMOBPkt(10, 1.0f, 2.0f, EMOBType.SNOWMAN);
        packet.flip();
        // record expected processor calls
        mockProcessor.addMOB(10, 1.0f, 2.0f, EMOBType.SNOWMAN);
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
    public void parseRemoveMOB() {
        ServerProtocol packetGenerator = ServerProtocol.getInstance();
        IClientProcessor mockProcessor = EasyMock.createMock(IClientProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createRemoveMOBPkt(10);
        packet.flip();
        // record expected processor calls
        mockProcessor.removeMOB(10);
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
    public void parseMoveMOB() {
        ServerProtocol packetGenerator = ServerProtocol.getInstance();
        IClientProcessor mockProcessor = EasyMock.createMock(IClientProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createMoveMOBPkt(10, 1.0f, 2.0f, 3.0f, 4.0f, 54321l);
        packet.flip();
        // record expected processor calls
        mockProcessor.moveMOB(10, 1.0f, 2.0f, 3.0f, 4.0f, 54321l);
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
    public void parseStopMOB() {
        ServerProtocol packetGenerator = ServerProtocol.getInstance();
        IClientProcessor mockProcessor = EasyMock.createMock(IClientProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createStopMOBPkt(10, 1.0f, 2.0f);
        packet.flip();
        // record expected processor calls
        mockProcessor.stopMOB(10, 1.0f, 2.0f);
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
    public void parseAttachObj() {
        ServerProtocol packetGenerator = ServerProtocol.getInstance();
        IClientProcessor mockProcessor = EasyMock.createMock(IClientProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createAttachObjPkt(10, 20);
        packet.flip();
        // record expected processor calls
        mockProcessor.attachObject(10, 20);
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
    public void parseAttacked() {
        ServerProtocol packetGenerator = ServerProtocol.getInstance();
        IClientProcessor mockProcessor = EasyMock.createMock(IClientProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createAttackedPkt(10, 20);
        packet.flip();
        // record expected processor calls
        mockProcessor.attacked(10, 20);
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
    public void parseSetHP() {
        ServerProtocol packetGenerator = ServerProtocol.getInstance();
        IClientProcessor mockProcessor = EasyMock.createMock(IClientProcessor.class);

        // generate packet
        ByteBuffer packet = packetGenerator.createSetHPPkt(10, 100);
        packet.flip();
        // record expected processor calls
        mockProcessor.setHP(10, 100);
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
