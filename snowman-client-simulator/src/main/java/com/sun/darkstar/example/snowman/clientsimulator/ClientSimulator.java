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
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
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
 *	<i>Default:</i> {@code 3000}
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
 *  <dt> <i>Property:</i> <code><b>
 *	moveDelay
 *	</b></code><br>
 *	<i>Default:</i> 2000 (two seconds)<br>
 *
 * <dd style="padding-top: .5em"> 
 * Specifies the minimun delay, in milliseconds, between when a player
 * will send a move message.<p>
 * 
 * <dt> <i>Property:</i> <code><b>
 *	newClientDelay
 *	</b></code><br>
 *	<i>Default:</i> 175<br>
 *
 * <dd style="padding-top: .5em"> 
 * Specifies the delay, in milliseconds, between new client
 * creation. This delay also applies to client destruction.<p>
 * </dl> <p>
 * 
 * @author Jeffrey Kesselman
 * @author Keith Thompson
 */
public class ClientSimulator extends JFrame implements ChangeListener {
    static final long serialVersionUID = 1L;

    static private final Logger logger = Logger.getLogger(
            ClientSimulator.class.getName());

    private final String serverHost;
    private final String serverPort;
    private final int moveDelay;
    private final int newClientDelay;
    
    private final JSlider usersSlider;
    private final JLabel userCount;
    
    private final Map<SimulatedPlayer, SimulatedPlayer> players =
            new ConcurrentHashMap<SimulatedPlayer, SimulatedPlayer>();
    
    private final ChangeThread changeThread;
    
    /**
     * Create and display the client simulator slider.
     */
    ClientSimulator() {
        super("Client Simulator Controls");
        
        serverHost = System.getProperty("host", "localhost");
        serverPort = System.getProperty("port", "3000");
        logger.log(Level.CONFIG, "Clients to use server at {0}:{1}",
                   new Object[] {serverHost, serverPort});
                
        moveDelay = Integer.getInteger("moveDelay", 2000);
        logger.log(Level.CONFIG, "Move delay set to {0} milliseconds", moveDelay);
        
        newClientDelay = Integer.getInteger("newClientDelay", 175);
        logger.log(Level.CONFIG,
                   "New client delay set to {0} milliseconds", newClientDelay);

        final int maxClients = Integer.getInteger("maxClients", 100);
        logger.log(Level.CONFIG, "Max number of clients set to {0}", maxClients);

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.add(new JLabel("Number of Clients:"));
        usersSlider = new JSlider(0, maxClients);
        usersSlider.setValue(0);
        c.add(usersSlider);
        userCount = new JLabel("0");
        c.add(userCount);
        usersSlider.addChangeListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        changeThread = new ChangeThread();
        changeThread.start();
        new MoveThread().start();
        setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int val = usersSlider.getValue();
        String numstr = Integer.toString(val);
        userCount.setText(numstr);
        ClientSimulator.this.pack();
        userCount.repaint();
        changeThread.wakeUp();
    }
    
    // Thread to handle change in players
    private class ChangeThread extends Thread {

        private final Lock lock = new ReentrantLock();
        private final Condition change = lock.newCondition();
        
        @Override
        public void run() {
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
                        SimulatedPlayer player =
                                new SimulatedPlayer(properties, moveDelay);
                        players.put(player, player);
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE,
                                   "Exception creating simulated player",
                                   ex);
                    }
                    pause(); // avoid storm
                } else if (usersSlider.getValue() < players.size()) {
                    Iterator<SimulatedPlayer> iter = players.values().iterator();
                    if (iter.hasNext()) {
                        iter.next().quit();
                        iter.remove();
                        pause(); // avoid storm
                    }
                } else {
                    lock.lock();
                    try {
                        change.await();
                    } catch (InterruptedException ignore) {
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
        
        void wakeUp() {
            lock.lock();
            change.signal();
            lock.unlock();
        }
        
        private void pause() {
            try {
                sleep(newClientDelay);
            } catch (InterruptedException ignore) {}
        }
    }
       
    // Thread to invoke player activity
    private class MoveThread extends Thread {

        @Override
        public void run() {

            while (true) {

                Iterator<SimulatedPlayer> iter = players.values().iterator();
                while (iter.hasNext()) {
                    try {
                        if (iter.next().move() == SimulatedPlayer.PLAYERSTATE.Quit) {
                            iter.remove();
                            changeThread.wakeUp();
                        }
                    } catch (IOException ex) {
                        logger.log(Level.SEVERE,
                                   "IO exception from player", ex);
                        iter.remove();
                        changeThread.wakeUp();
                    }
                    try {
                        sleep(50);
                    } catch (InterruptedException ignore) {}
                }
            }
        }
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