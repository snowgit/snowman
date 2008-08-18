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

import com.sun.darkstar.example.snowman.server.context.MockAppContext;
import com.sun.darkstar.example.snowman.server.context.SnowmanAppContextFactory;
import com.sun.darkstar.example.snowman.server.context.SnowmanAppContext;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanPlayer;
import com.sun.darkstar.example.snowman.server.interfaces.Matchmaker;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanGame;
import com.sun.darkstar.example.snowman.server.interfaces.EntityFactory;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ClientSession;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import org.easymock.EasyMock;

/**
 * Test the SnowmanPlayerListener
 * 
 * @author Owen Kellett
 */
public class SnowmanPlayerListenerTest 
{
    @Before
    public void initializeContext()
    {
        MockAppContext.create();
    }
    
    @After
    public void takeDownContext()
    {
        SnowmanAppContextFactory.setAppContext(null);
    }

    /**
     * Verify that the static find method returns the player from
     * the datastore if one exists
     */
    @Test
    public void testFindHit()
    {
        //setup the dummy player
        String playerName = "name";
        SnowmanAppContext appContext = SnowmanAppContextFactory.getAppContext();
        SnowmanPlayer dummySP = EasyMock.createNiceMock(SnowmanPlayer.class);
        Matchmaker dummyMM = EasyMock.createNiceMock(Matchmaker.class);
        SnowmanPlayerListener dummyPlayer = new SnowmanPlayerListener(appContext,
                                                                      dummySP,
                                                                      dummyMM);
        
        //setup the incoming ClientSession
        ClientSession session = EasyMock.createMock(ClientSession.class);
        EasyMock.expect(session.getName()).andStubReturn(playerName);
        EasyMock.replay(session);
        
        //setup the context
        DataManager dataManager = appContext.getDataManager();
        dataManager.setBinding(SnowmanPlayerListener.PREFIX+playerName, dummyPlayer);
        
        //make the find call
        SnowmanPlayerListener player = SnowmanPlayerListener.find(session, 
                                                                  appContext, 
                                                                  EasyMock.createMock(EntityFactory.class), 
                                                                  EasyMock.createNiceMock(Matchmaker.class));
        
        //verify that the player is who we expect
        Assert.assertSame(player, dummyPlayer);
    }
    
    /**
     * Verify that the static find method creates and returns a new player
     * if one is not found in the datastore
     */
    @Test
    public void testFindMiss()
    {
        //setup the dummy player
        String playerName = "name";
        SnowmanAppContext appContext = SnowmanAppContextFactory.getAppContext();
        SnowmanPlayer dummySP = EasyMock.createNiceMock(SnowmanPlayer.class);
        Matchmaker dummyMM = EasyMock.createNiceMock(Matchmaker.class);
        
        //setup the incoming ClientSession
        ClientSession session = EasyMock.createMock(ClientSession.class);
        EasyMock.expect(session.getName()).andStubReturn(playerName);
        EasyMock.replay(session);
        
        //setup the entityFactory
        EntityFactory entityFactory = EasyMock.createMock(EntityFactory.class);
        EasyMock.expect(entityFactory.createSnowmanPlayer(appContext, session)).andReturn(dummySP);
        EasyMock.replay(entityFactory);
        
        //make the find call
        SnowmanPlayerListener player = SnowmanPlayerListener.find(session, 
                                                                  appContext, 
                                                                  entityFactory,
                                                                  dummyMM);

        //verify that the entityFactory was used to create the player
        EasyMock.verify(entityFactory);
        //verify that the player is who we expect
        Assert.assertSame(player.getSnowmanPlayer(), dummySP);
        //verify that the player is entered into the datamanager
        DataManager dataManager = appContext.getDataManager();
        SnowmanPlayerListener playerListener = (SnowmanPlayerListener)dataManager.getBinding(SnowmanPlayerListener.PREFIX+playerName);
        Assert.assertSame(playerListener.getSnowmanPlayer(), dummySP);
    }
    
    /**
     * Verify that when a player disconnects while still in the Matchmaker,
     * it is removed from the Matchmaker
     */
    @Test
    public void testDisconnectedInMatchmaker()
    {
        //setup the dummy playerListener
        SnowmanAppContext appContext = SnowmanAppContextFactory.getAppContext();
        SnowmanPlayer dummySP = EasyMock.createNiceMock(SnowmanPlayer.class);
        Matchmaker dummyMM = EasyMock.createMock(Matchmaker.class);
        SnowmanPlayerListener dummyPlayer = new SnowmanPlayerListener(appContext,
                                                                      dummySP,
                                                                      dummyMM);
        
        //setup the dummy matchmaker
        dummyMM.removeWaitingPlayer(dummySP);
        EasyMock.replay(dummyMM);
        
        //setup the dummy player
        EasyMock.expect(dummySP.getGame()).andReturn(null);
        EasyMock.replay(dummySP);
        
        //disconnect the player
        dummyPlayer.disconnected(true);
        
        //verify removal
        EasyMock.verify(dummyMM);
    }
    
    /**
     * Verify that when a player disconnects while in a Game
     * it is removed from the Matchmaker and the Game
     */
    @Test
    public void testDisconnectedInGame()
    {
        //setup the dummy playerListener
        SnowmanAppContext appContext = SnowmanAppContextFactory.getAppContext();
        SnowmanPlayer dummySP = EasyMock.createNiceMock(SnowmanPlayer.class);
        Matchmaker dummyMM = EasyMock.createMock(Matchmaker.class);
        SnowmanPlayerListener dummyPlayer = new SnowmanPlayerListener(appContext,
                                                                      dummySP,
                                                                      dummyMM);
        
        //setup the dummy matchmaker
        dummyMM.removeWaitingPlayer(dummySP);
        EasyMock.replay(dummyMM);
        
        //setup the dummy player
        SnowmanGame dummyGame = EasyMock.createMock(SnowmanGame.class);
        dummyGame.removePlayer(dummySP);
        EasyMock.expect(dummySP.getGame()).andReturn(dummyGame).anyTimes();
        dummySP.setGame(null);
        EasyMock.replay(dummySP);
        EasyMock.replay(dummyGame);
        
        //disconnect the player
        dummyPlayer.disconnected(true);
        
        //verify removal
        EasyMock.verify(dummyMM);
        EasyMock.verify(dummyGame);
    }
}
