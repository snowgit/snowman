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

package com.sun.darkstar.example.snowman.server.tasks;

import com.sun.darkstar.example.snowman.server.interfaces.SnowmanPlayer;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanGame;
import com.sun.darkstar.example.snowman.server.interfaces.GameFactory;
import com.sun.darkstar.example.snowman.server.interfaces.EntityFactory;
import com.sun.darkstar.example.snowman.common.protocol.enumn.ETeamColor;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.Task;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.ObjectNotFoundException;
import com.sun.sgs.app.RunWithNewIdentity;
import com.sun.sgs.app.util.ManagedSerializable;
import com.sun.sgs.app.util.ScalableList;
import java.io.Serializable;
import java.util.Deque;
import java.util.List;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 *
 */
@RunWithNewIdentity
public class StartGameOnNewNodeTask implements Task, Serializable {
    
    private ManagedReference<ManagedSerializable<Integer>> gameCount;
    private final int numPlayersPerGame;
    private final int numRobotsPerGame;
    private final int robotDelay;
    private GameFactory gameFactory;
    private EntityFactory entityFactory;

    private ManagedReference<List<ManagedReference<SnowmanPlayer>>>
            waitingPlayers;

    StartGameOnNewNodeTask(ManagedSerializable<Integer> gameCount,
                           int numPlayersPerGame,
                           int numRobotsPerGame,
                           int robotDelay,
                           GameFactory gameFactory,
                           EntityFactory entityFactory,
                           ManagedReference<List<ManagedReference<SnowmanPlayer>>> waitingPlayers) {
        this.gameCount = AppContext.getDataManager().createReference(gameCount);
        this.numPlayersPerGame = numPlayersPerGame;
        this.numRobotsPerGame = numRobotsPerGame;
        this.robotDelay = robotDelay;
        this.gameFactory = gameFactory;
        this.entityFactory = entityFactory;
        this.waitingPlayers = waitingPlayers;
    }


    public void run() throws Exception {
        AppContext.getTaskManager().scheduleTask(new StartGameTask(gameCount.get(), numPlayersPerGame, numRobotsPerGame, robotDelay, gameFactory, entityFactory, waitingPlayers));
    }
}
