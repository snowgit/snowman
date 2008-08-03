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

import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Program which will start and stop multiple simulated snowman game players.
 * It supports the following properties: <p>
 *
 * <dl style="margin-left: 1em">
 *
 * <dt> <i>Property:</i> <code><b>
 *	host
 *	</b></code><br>
 *	<i>Default:</i> {@code localhost}
 *
 * <dd style="padding-top: .5em">
 *      Specifies the host name of the snowman game server.<p>
 *
 * <dt> <i>Property:</i> <code><b>
 *	port
 *	</b></code><br>
 *	<i>Default:</i> {@code 1139}
 *
 * <dd style="padding-top: .5em"> 
 *	Specifies the port of the snowman game server.<p>
 *
 * <dt> <i>Property:</i> <code><b>
 *	maxClients
 *	</b></code><br>
 *	<i>Default:</i> {@code 100}
 *
 * <dd style="padding-top: .5em"> 
 *	Specifies maximun number of clients that can be started.<p>
 *
 * </dl> <p>
 * 
 * @author Jeffrey Kesselman
 * @author Keith Thompson
 */
public class ClientSimulator extends JFrame {
    static final long serialVersionUID = 1L;

    static private final Logger logger = Logger.getLogger(
            ClientSimulator.class.getName());

    private final String serverHost;
    private final String serverPort;
    
    private final JSlider usersSlider;
    private final JLabel userCount;

    /**
     * Create and display the client simulator slider.
     */
    public ClientSimulator() {
        super("Client Simulator Controls");
        
        serverHost = System.getProperty("host", "localhost");
        serverPort = System.getProperty("port", "1139");
        
        logger.log(Level.CONFIG, "Clients to use server at {0}:{1}",
                   new Object[] {serverHost, serverPort});
                
        Container c = getContentPane();
        //c.setLayout(new GridLayout(1,3));
        c.setLayout(new FlowLayout());
        c.add(new JLabel("Number of Clients:"));
        usersSlider = new JSlider(0, Integer.getInteger(System.getProperty("maxClients"), 100));
        usersSlider.setValue(0);
        c.add(usersSlider);
        userCount = new JLabel("0");
        c.add(userCount);

        usersSlider.addChangeListener(new ChangeListener() {

            @Override
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
                final List<SimulatedPlayer> players =
                        new ArrayList<SimulatedPlayer>();
                int userId = 0;
                
                while (true) {

                    if (usersSlider.getValue() > players.size()) {
                        Properties properties = new Properties();
                        properties.setProperty("host",
                                System.getProperty("host", serverHost));
                        properties.setProperty("port",
                                System.getProperty("port", serverPort));
                        properties.setProperty("name", "Robot" + userId++);
                        try {
                            players.add(new SimulatedPlayer(properties));
                        } catch (Exception ex) {
                            logger.log(Level.SEVERE,
                                       "Exception creating simulated player",
                                       ex);
                        }
                    } else if (usersSlider.getValue() < players.size()) {
                        // 0 cannot be out of bounds if size > slider
                        // minimum i.e. size > zero.
                        players.remove(0).quit();
                    }

                    // do a move
                    Iterator<SimulatedPlayer> iter = players.iterator();
                    while (iter.hasNext()) {
                        if (iter.next().move() == SimulatedPlayer.PLAYERSTATE.Quit)
                            iter.remove();
                    }
                    try {
                        sleep(200);
                    } catch (InterruptedException ignore) {}
                }
            }
        }).start();
    }

    /**
     * Main.
     * @param args no arguments supported. See class description for
     * the list of properties supported.
     */
    public static void main(String[] args) {
        new ClientSimulator();
    }
}