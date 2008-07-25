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

package com.sun.darkstar.example.snowman.clientsimulator;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.protocol.messages.ClientMessages;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.processor.IClientProcessor;
import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.sgs.client.ClientChannel;
import com.sun.sgs.client.ClientChannelListener;
import com.sun.sgs.client.simple.SimpleClient;
import com.sun.sgs.client.simple.SimpleClientListener;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simulated player. An instance of this class will play the snowman
 * game by moving randomly.
 * 
 * @author Jeffrey Kesselman
 * @author Keith Thompson
 */
class SimulatedPlayer implements SimpleClientListener {
    static final Logger logger =
            Logger.getLogger(SimulatedPlayer.class.getName());
    
    static private final Random random = new Random();
    static private final int MOVEDISTANCE = 10;
    
    static enum PLAYERSTATE {
        LoggingIn, Paused, Playing, Quit
    }
    
    private final String name;
    private final String password;
    private final SimpleClient simpleClient;
    private final IClientProcessor pktHandler;
    private PLAYERSTATE state;
    private float x;
    private float y;
    private int id; // ID provided by server
    
    /**
     * Construct a simulated player. The {@code props} argument supports the
     * following properties:
     * 
     * <dl style="margin-left: 1em">
     *
     * <dt> <i>Property:</i> <code><b>
     *	host
     *	</b></code><br>
     *	<i>Default:</i> required
     *
     * <dd style="padding-top: .5em">
     *      Specifies the host name of the snowman game server.<p>
     *
     * <dt> <i>Property:</i> <code><b>
     *	port
     *	</b></code><br>
     *	<i>Default:</i> required<br>
     *
     * <dd style="padding-top: .5em"> 
     *	Specifies the port of the snowman game server.<p>
     *
     *  <dt> <i>Property:</i> <code><b>
     *	name
     *	</b></code><br>
     *	<i>Default:</i> required<br>
     *
     *  <dd style="padding-top: .5em"> 
     *	The user name used to authenticate with the server<p>
     * 
     *  <dt> <i>Property:</i> <code><b>
     *	password
     *	</b></code><br>
     *	<i>Default:</i> null (no password)<br>
     *
     *  <dd style="padding-top: .5em"> 
     *	The user password used to authenticate with the server<p>
     * </dl> <p>
     * 
     * @param props properties to configure this player
     * @throws java.lang.Exception if a required property is missing or an
     * an error occurs communiticating with the server.
     */
    public SimulatedPlayer(Properties props) throws Exception {
        name = props.getProperty("name");
        if (name == null)
            throw new Exception("name property required");
        
        logger.log(Level.FINE, "New simulated player: {0}", name);
        password = props.getProperty("password", "");
        pktHandler = new ClientProcessor();
        state = PLAYERSTATE.LoggingIn;
        simpleClient = new SimpleClient(this);
        simpleClient.login(props);
    }
    
    private class ClientProcessor implements IClientProcessor {

        @Override
        public void ready() {
            logger.log(Level.FINEST, "Ready message for {0}", name);
            try{
                ByteBuffer buff = ClientMessages.createReadyPkt();
                buff.flip();
                simpleClient.send(buff);
            } catch (IOException ioe) {
                logger.log(Level.SEVERE, "" + name, ioe);
            }
        }

        public void enterLounge(int myID) {
            logger.log(Level.FINEST, "Enter lounge message for {0}", name);
        }

        @Override
        public void newGame(int myID, String mapname) {
            logger.log(Level.FINEST, "New game for {0}, id is {1}",
                       new Object[] {name, myID});
            id = myID;
        }

        @Override
        public void startGame() {
            if (setState(PLAYERSTATE.Playing))
                logger.log(Level.FINEST, "Start game for {0}", name);
            else
                logger.log(Level.WARNING, "Received start, but {0} has quit",
                           name);
        }

        @Override
        public void endGame(EEndState endState) {
            logger.log(Level.FINEST,
                       "Game ended for {0} with outcome of {1}",
                       new Object[] {name, endState});
            quit();
        }

        @Override
        public void addMOB(int objectID, float x, float y, EMOBType objType) {
            logger.log(Level.FINEST, "Message to {0}: Add MOB {1}",
                       new Object[] {name, objectID});
        }

        @Override
        public void moveMOB(int objectID, float startx, float starty, float endx, float endy) {
            logger.log(Level.FINEST, "Message to {0}: Move MOB {1}",
                       new Object[] {name, objectID});
        }

        @Override
        public void removeMOB(int objectID) {
            logger.log(Level.FINEST, "Message to {0}: Remove MOB {1}",
                       new Object[] {name, objectID});
        }

        @Override
        public void stopMOB(int objectID, float x, float y) {
            logger.log(Level.FINEST, "Message to {0}:, Stop MOB {1}",
                       new Object[] {name, objectID});
        }

        @Override
        public void attachObject(int sourceID, int targetID) {
            logger.log(Level.FINEST, "Message to {0}: Attach object {1} to {2}",
                       new Object[] {name, sourceID, targetID});
        }

        @Override
        public void attacked(int sourceID, int targetID) {
            logger.log(Level.FINEST, "Message to {0}: {1} attacked {2}",
                       new Object[] {name, sourceID, targetID});
        }

        public void info(int objectID, String string) {
            logger.log(Level.FINEST, "Message to {0}: Info on object {1} is {2}",
                       new Object[] {name, objectID, string});    
        }

        @Override
        public void setHP(int objectID, int hp) {
            logger.log(Level.FINEST, "Message to {0}: Set HP on object {1} to {2}",
                       new Object[] {name, objectID, hp});
        }
    }
    
    /**
     * Make a move. A move is made only if in the {@code Playing} state.
     * @return the current state
     */
    synchronized PLAYERSTATE move() {
        logger.log(Level.FINEST, "Calling move, {0} is {1}",
                   new Object[] {name, state});
        if (state != PLAYERSTATE.Playing)
            return state;

        // player AI logic goes here
        // NOTE: this is no longer correct, we need to pass
        // the starting position of the player in the packet
        // as well
        Vector3f ray = new Vector3f(random.nextFloat()-0.5f,
                                    random.nextFloat()-0.5f,
                                    0.0f);
        ray = ray.normalize();    
        ray = ray.mult(MOVEDISTANCE);
        ray = ray.add(new Vector3f(x, y, 0.0f));
        ray = lookForBlocking(ray);
        try {
            ByteBuffer buff = ClientMessages.createMoveMePkt(0.0f, 0.0f, ray.x, ray.y);
            buff.flip();
            simpleClient.send(buff);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        return state;
    }

    private Vector3f lookForBlocking(Vector3f ray) {
        return ray;
    }

    /**
     * Set the state of the player. Returns {@code true} if the state was
     * set, otherwise it returns {@code false}. The state can only be set
     * if the current state is not {@code PLAYERSTATE.Quit}.
     * @param newState
     * @return true if the state was set
     */
    private synchronized boolean setState(PLAYERSTATE newState) {
        if (state != PLAYERSTATE.Quit) {
            state = newState;
            return true;
        } else
            return false;
    }
    
    /**
     * Quit the game. The player will logout from the server and will no
     * longer be able to move, i.e {@code move} will become a no-op.
     */
    synchronized void quit() {
        logger.log(Level.FINE, "Player: {0} quit", name);
        if ((state == PLAYERSTATE.Playing) ||
            (state == PLAYERSTATE.Paused)) {
            simpleClient.logout(false);
        }
        state = PLAYERSTATE.Quit;
    }
    
    /* -- SimpleClientListener -- */
    
    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(name, password.toCharArray());
    }

    @Override
    public void loggedIn() {
        if (setState(PLAYERSTATE.Paused))
            logger.log(Level.FINE, "Player {0} logged in", name);
        else
            logger.log(Level.WARNING, "Player {0} received login after quit",
                       name);
    }

    @Override
    public void loginFailed(String arg0) {
        logger.log(Level.WARNING, "Login failed for {0}", name);
        quit();
    }

    @Override
    public ClientChannelListener joinedChannel(ClientChannel arg0) {
        return new ClientChannelListener() {

            @Override
            public void receivedMessage(ClientChannel channel, ByteBuffer buff) {
                SingletonRegistry.getMessageHandler().parseClientPacket(buff, pktHandler);
            }

            @Override
            public void leftChannel(ClientChannel channel) {
                logger.log(Level.FINE, "Player {0} left channel", name);
                quit();
            }
        };
    }
    
    @Override
    public void receivedMessage(ByteBuffer buff) {
        SingletonRegistry.getMessageHandler().parseClientPacket(buff, pktHandler);
    }

    @Override
    public void reconnecting() {
        logger.log(Level.FINE, "Re-connecting player: {0}", name);
    }

    @Override
    public void reconnected() {
        logger.log(Level.FINE, "Re-connected player: {0}", name);
    }

    @Override
    public void disconnected(boolean arg0, String arg1) {
        logger.log(Level.FINE, "Disconnected player: {0}", name);
        quit();
    }
}