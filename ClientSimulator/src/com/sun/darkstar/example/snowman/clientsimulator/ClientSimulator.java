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

import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.processor.IClientProcessor;
import com.sun.sgs.client.ClientChannel;
import com.sun.sgs.client.ClientChannelListener;
import com.sun.sgs.client.simple.SimpleClient;
import com.sun.sgs.client.simple.SimpleClientListener;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.List;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.protocol.ClientProtocol;
import com.sun.darkstar.example.snowman.common.util.CollisionManager;

/**
 *
 * @author Jeffrey Kesselman
 */
public class ClientSimulator extends JFrame {
    static final int MOVEDISTANCE = 10;
    static final Random random = new Random();
    static final boolean NORACE = false;
    static final Logger logger = Logger.getLogger(
            ClientSimulator.class.getName());

    static enum PLAYERSTATE {

        LoggingIn, Paused, Playing, Quit
    }
    JSlider usersSlider;
    JLabel userCount;
    List<FakePlayer> players = new ArrayList<FakePlayer>();
    volatile int userID = 0;

    public ClientSimulator() {
        super("Client Simulator Controls");
        Container c = getContentPane();
        //c.setLayout(new GridLayout(1,3));
        c.setLayout(new FlowLayout());
        c.add(new JLabel("Number of Clients:"));
        usersSlider = new JSlider(0, 500);
        usersSlider.setValue(0);
        c.add(usersSlider);
        userCount = new JLabel("0");
        c.add(userCount);

        usersSlider.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                int val = usersSlider.getValue();
                String numstr = Integer.toString(val);
                userCount.setText(numstr);
                ClientSimulator.this.pack();
                userCount.repaint();
            }
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
        (new Thread() {

            @Override
            public void run() {
                // adjust number of players
                while (true) {

                    while (usersSlider.getValue() != players.size()) {
                        if (usersSlider.getValue() > players.size()) {
                            Properties properties = new Properties();
                            properties.setProperty("host",
                                    System.getProperty("host", "localhost"));
                            properties.setProperty("port",
                                    System.getProperty("port", "1139"));
                            properties.setProperty("name", "Robot" + (userID++));
                            try {
                                players.add(new FakePlayer(properties));
                                if (NORACE) {
                                    try {
                                        sleep(10);
                                    } catch (InterruptedException ex) {
                                        Logger.getLogger(ClientSimulator.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            } catch (IOException ex) {
                                Logger.getLogger(ClientSimulator.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (usersSlider.getValue() < players.size()) {
                            FakePlayer player = players.remove(players.size() - 1);
                            player.quit();
                        }
                    }

                    // do a move
                    for (FakePlayer player : players) {
                        player.doit();
                    }
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        }).start();
    }

    class FakePlayer implements SimpleClientListener {
        
        String name;
        String password = "";
        SimpleClient simpleClient;
        PLAYERSTATE state;
        IClientProcessor pktHandler;
        float x;
        float y;
      

        public FakePlayer(Properties props) throws IOException {
            name = props.getProperty("name");
            password = props.getProperty("password", "");
            simpleClient = new SimpleClient(this);
            state = PLAYERSTATE.LoggingIn;
            simpleClient.login(props);
            pktHandler = new IClientProcessor() {

                public void enterLounge(int myID) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void newGame(int myID, String mapname) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void startGame() {
                    state = PLAYERSTATE.Playing;
                }

                public void endGame(EEndState endState) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void addMOB(int objectID, float x, float y, EMOBType objType) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void moveMOB(int objectID, float startx, float starty, float endx, float endy) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void removeMOB(int objectID) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void attachObject(int sourceID, int targetID) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void attacked(int sourceID, int targetID) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void info(int objectID, String string) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void setHP(int objectID, int hp) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }

                public void chatText(String text) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(name, password.toCharArray());
        }

        public void loggedIn() {
            state = PLAYERSTATE.Paused;
             logger.info("Connected player: "+name);
        }

        public void loginFailed(String arg0) {
        }

        public ClientChannelListener joinedChannel(ClientChannel arg0) {
            return null;
        }

        public void receivedMessage(ByteBuffer arg0) {
        }

        public void reconnecting() {
        }

        public void reconnected() {
        }

        public void disconnected(boolean arg0, String arg1) {
            logger.info("Disconnected player: "+name);
        }

        public void doit() {
            if (state != PLAYERSTATE.Playing) {
                return;
            }
        // player AI logic goes here
            Vector3f ray = new Vector3f(random.nextFloat()-0.5f,
                    random.nextFloat()-0.5f,0.0f);
            ray = ray.normalize();    
            ray = ray.mult(MOVEDISTANCE);
            ray = ray.add(new Vector3f(x,y,0.0f));
            ray = lookForBlocking(ray);
            try {
                simpleClient.send(ClientProtocol.getInstance().createMoveMePkt(ray.x, ray.y));
            } catch (IOException ex) {
                Logger.getLogger(ClientSimulator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private Vector3f lookForBlocking(Vector3f ray) {
            return ray;
        }

        private void quit() {
            if ((state == PLAYERSTATE.Playing)||
                (state == PLAYERSTATE.Paused)){
                simpleClient.logout(false);
                state = PLAYERSTATE.Quit;
            }
        }
    }

    public static void main(String[] args) {
        new ClientSimulator();
    }
}
