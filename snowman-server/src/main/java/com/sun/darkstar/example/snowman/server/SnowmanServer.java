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

import com.sun.darkstar.example.snowman.server.interfaces.GameFactory;
import com.sun.darkstar.example.snowman.server.interfaces.EntityFactory;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanPlayer;
import com.sun.darkstar.example.snowman.server.impl.EntityFactoryImpl;
import com.sun.darkstar.example.snowman.server.impl.GameFactoryImpl;
import com.sun.darkstar.example.snowman.server.tasks.MatchmakerTask;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.util.ScalableDeque;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Deque;
import java.math.BigInteger;

/**
 * This class is the app listener for Project Snowman
 * It is sort of like a "main class" in a traditional Java applciation
 * @author Jeffrey Kesselman
 */
public class SnowmanServer implements ManagedObject, Serializable, AppListener {

    public static long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(SnowmanServer.class.getName());
    
    private static final int NUMDEQUES = 10;
    private static final String PLAYERS_PER_GAME_PROP = "numPlayersPerGame";
    private static final int DEFAULT_PLAYERS_PER_GAME = 2;
    private static final String ROBOTS_PER_GAME_PROP = "numRobotsPerGame";
    private static final int DEFAULT_ROBOTS_PER_GAME = 2;
    private static final String ROBOT_DELAY_PROP = "robotDelay";
    private static final int DEFAULT_ROBOT_DELAY = 2000;
    
    private int numPlayersPerGame;
    private int numRobotsPerGame;
    private int robotDelay;
    
    private ManagedReference<Deque<ManagedReference<SnowmanPlayer>>>[] waitingDeques;
    private GameFactory gameFactory;
    private EntityFactory entityFactory;

    @SuppressWarnings("unchecked")
    public void initialize(Properties arg0) {
        this.gameFactory = new GameFactoryImpl();
        this.entityFactory = new EntityFactoryImpl();
        this.waitingDeques = new ManagedReference[NUMDEQUES];
        for (int i = 0; i < waitingDeques.length; i++) {
            Deque<ManagedReference<SnowmanPlayer>> deque = new ScalableDeque<ManagedReference<SnowmanPlayer>>();
            waitingDeques[i] = AppContext.getDataManager().createReference(deque);
        }

        this.config();
        AppContext.getTaskManager().scheduleTask(new MatchmakerTask(numPlayersPerGame,
                                                                    numRobotsPerGame,
                                                                    robotDelay,
                                                                    gameFactory,
                                                                    entityFactory,
                                                                    waitingDeques));
    }

    private void config() {
        numPlayersPerGame = Integer.getInteger(PLAYERS_PER_GAME_PROP,
                                               DEFAULT_PLAYERS_PER_GAME);
        if (numPlayersPerGame <= 0) {
            throw new IllegalArgumentException(PLAYERS_PER_GAME_PROP + " must be > 0");
        }
        logger.log(Level.CONFIG,
                   "Number of players required to start a game set to {0}",
                   numPlayersPerGame);
        numRobotsPerGame = Integer.getInteger(ROBOTS_PER_GAME_PROP,
                                              DEFAULT_ROBOTS_PER_GAME);
        if (numRobotsPerGame < 0) {
            throw new IllegalArgumentException(ROBOTS_PER_GAME_PROP + " must be >= 0");
        }
        robotDelay = Integer.getInteger(ROBOT_DELAY_PROP,
                                        DEFAULT_ROBOT_DELAY);
        if (robotDelay < 0) {
            throw new IllegalArgumentException(ROBOT_DELAY_PROP + " must be >= 0");
        }
        logger.log(Level.CONFIG,
                   "Number of robots per game: {0}, with delay of {1} milliseconds",
                   new Object[]{numRobotsPerGame, robotDelay});
    }

    public ClientSessionListener loggedIn(ClientSession session) {
        if (logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Player {0} logged in", session.getName());
        }
        SnowmanPlayerListener player =
                new SnowmanPlayerListener(entityFactory.createSnowmanPlayer(session));
        BigInteger id = player.getSnowmanPlayerRef().getId();
        BigInteger index = id.mod(BigInteger.valueOf((long) NUMDEQUES));

        waitingDeques[index.intValue()].get().add(player.getSnowmanPlayerRef());
        return player;
    }
}
