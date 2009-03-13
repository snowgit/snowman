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

import com.sun.darkstar.example.snowman.server.interfaces.EntityFactory;
import com.sun.darkstar.example.snowman.server.impl.EntityFactoryImpl;
import com.sun.darkstar.example.snowman.common.protocol.messages.ServerMessages;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.enumn.ETeamColor;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedObject;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * This class is the app listener for Project Snowman.
 * 
 * @author Jeffrey Kesselman
 * @author Owen Kellett
 * @author Keith Thompson
 */
public class SnowmanServer implements ManagedObject, Serializable, AppListener {

    /** The version of the serialized form. */
    public static final long serialVersionUID = 1L;
    private static final Logger logger = 
            Logger.getLogger(SnowmanServer.class.getName());
    
    private EntityFactory entityFactory;
    
    /**
     * Initializes a Project Snowman server upon first bootup.
     * 
     * @param props a set of {@code Properties} used to configure the 
     *        runtime state of the game
     */
    public void initialize(Properties props) {
        this.entityFactory = new EntityFactoryImpl();
    }

    /**
     * Handles player login.
     * 
     * @param session the {@code ClientSession} of the connecting player
     * @return a {@code ClientSessionListener} associated with the connected
     *         player
     */
    public ClientSessionListener loggedIn(ClientSession session) {
        session.send(ServerMessages.createNewGamePkt(1, "default_map"));
        session.send(ServerMessages.createAddMOBPkt(1,
                                                    10, 
                                                    10,
                                                    EMOBType.SNOWMAN,
                                                    ETeamColor.Blue,
                                                    session.getName()));
        session.send(ServerMessages.createReadyPkt());
        
        return new SnowmanPlayerListener(
                entityFactory.createSnowmanPlayer(session));
    }
}
