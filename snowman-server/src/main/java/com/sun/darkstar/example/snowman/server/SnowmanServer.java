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

import com.sun.darkstar.example.snowman.server.interfaces.Matchmaker;
import com.sun.darkstar.example.snowman.server.interfaces.EntityFactory;
import com.sun.darkstar.example.snowman.server.interfaces.GameFactory;
import com.sun.darkstar.example.snowman.server.impl.MatchmakerImpl;
import com.sun.darkstar.example.snowman.server.impl.EntityFactoryImpl;
import com.sun.darkstar.example.snowman.server.impl.GameFactoryImpl;
import com.sun.darkstar.example.snowman.server.context.SnowmanAppContext;
import com.sun.darkstar.example.snowman.server.context.SnowmanAppContextFactory;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * This class is the app listener for Project Snowman
 * It is sort of like a "main class" in a traditional Java applciation
 * @author Jeffrey Kesselman
 */
public class SnowmanServer implements ManagedObject, Serializable, AppListener{
    public static long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(SnowmanServer.class.getName());

    private ManagedReference<Matchmaker> matchMakerRef;
    private EntityFactory entityFactory;
    private GameFactory gameFactory;
    private SnowmanAppContext appContext;
    
    public void initialize(Properties arg0) {
        this.appContext = SnowmanAppContextFactory.getAppContext();
        this.entityFactory = new EntityFactoryImpl();
        this.gameFactory = new GameFactoryImpl();
        Matchmaker matchMaker = new MatchmakerImpl(appContext,
                                                   gameFactory,
                                                   entityFactory);
        matchMakerRef = appContext.getDataManager().createReference(matchMaker);
    }

    public ClientSessionListener loggedIn(ClientSession arg0) {
        logger.info("Player "+arg0.getName()+" logged in");
        SnowmanPlayerListener player =  SnowmanPlayerListener.find(arg0, appContext, entityFactory, matchMakerRef.get());
        matchMakerRef.get().addWaitingPlayer(player.getSnowmanPlayer());
        return player;
    }

}
