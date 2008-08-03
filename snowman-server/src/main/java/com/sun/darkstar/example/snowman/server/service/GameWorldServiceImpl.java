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

package com.sun.darkstar.example.snowman.server.service;

import com.sun.sgs.kernel.ComponentRegistry;
import com.sun.sgs.service.Transaction;
import com.sun.sgs.service.TransactionProxy;
import com.sun.sgs.service.NonDurableTransactionParticipant;
import com.sun.sgs.kernel.TaskScheduler;
import com.sun.sgs.kernel.TransactionScheduler;
import com.sun.sgs.kernel.TaskReservation;
import com.sun.sgs.app.TransactionException;
import com.sun.sgs.app.TaskRejectedException;
import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;
import com.jme.scene.Spatial;
import com.jme.system.dummy.DummySystemProvider;
import com.jme.system.DisplaySystem;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;


/**
 * The <code>GameWorldServiceImpl</code> is an implementation of a Project
 * Darkstar service that provides utility methods to calculate
 * collision detection and spatial information against the static
 * game world geometry.
 * 
 * @author Owen Kellett
 */
public class GameWorldServiceImpl implements GameWorldService, 
                                             NonDurableTransactionParticipant {
    
    /** The logger for this class. */
    private static Logger logger = Logger.getLogger(GameWorldServiceImpl.class.getName());
    
    /** The TaskScheduler from the registry */
    private final TaskScheduler taskScheduler;
    /** The TransactionScheduler from the registry */
    private final TransactionScheduler txnScheduler;
    /** The TransactionProxy of the system*/
    private final TransactionProxy txnProxy;
    
    /** Map used to map calling transactions to reserved task */
    private final ConcurrentHashMap<Transaction, HashSet<TaskReservation>> txnMap =
            new ConcurrentHashMap<Transaction, HashSet<TaskReservation>>();
    
    /** Current game world **/
    private Spatial gameWorld;
    
    
    public GameWorldServiceImpl(Properties properties,
                                ComponentRegistry registry,
                                TransactionProxy txnProxy) {
        this.taskScheduler = registry.getComponent(TaskScheduler.class);
        this.txnScheduler = registry.getComponent(TransactionScheduler.class);
        this.txnProxy = txnProxy;
        
        //create dummy display system so that the JME importer doesn't complain
        DummySystemProvider provider = new DummySystemProvider();
	DisplaySystem.setSystemProvider(provider);
        this.gameWorld = SingletonRegistry.getDataImporter().getWorld(EWorld.Battle);
    }

    /** @inheritDoc **/
    public String getName() {
        return this.getClass().getName();
    }

    /** @inheritDoc **/
    public void ready() throws Exception {

    }

    /** @inheritDoc **/
    public boolean shutdown() {
        return true;
    }
    
    /** {@inheritDoc} */
    public void trimPath(int playerId,
                         float startx,
                         float starty, 
                         float endx, 
                         float endy, 
                         long timestart,
                         GameWorldServiceCallback callback) {
        //first we create a new task to calculate the trimmed path
        TrimPathTask task = new TrimPathTask(playerId,
                                             startx,
                                             starty,
                                             endx,
                                             endy,
                                             timestart,
                                             callback,
                                             gameWorld,
                                             SingletonRegistry.getCollisionManager());
        
        //join the current transaction if it exists
        //and save the task for use during commit
        try {
            HashSet<TaskReservation> reservations = joinCurrentTransaction();
            TaskReservation reservation = taskScheduler.reserveTask(task, txnProxy.getCurrentOwner());
            reservations.add(reservation);
        } catch (TransactionException e) {
            //if there is no transaction, schedule the task immediately
            taskScheduler.scheduleTask(task, txnProxy.getCurrentOwner());
        } catch (TaskRejectedException e) {
            //if a reservation is denied, log the error and callback a failure
            logger.warning("Unable to reserve task: "+e.getMessage());
            callback.trimPathFailure(playerId, startx, starty, timestart);
        }
    }
    
    /** {@inheritDoc} */
    public boolean validThrow(float startx,
                              float starty, 
                              float endx,
                              float endy) {
        return SingletonRegistry.getCollisionManager().validate(startx, starty, endx, endy, gameWorld);
    }

    private HashSet<TaskReservation> joinCurrentTransaction() {
        Transaction txn = txnProxy.getCurrentTransaction();
        HashSet<TaskReservation> set = txnMap.get(txn);
        
        if(set == null) {
            set = new HashSet<TaskReservation>();
            txnMap.put(txn, set);
            txn.join(this);
        }
        
        return set;
    }
    
    /** @inheritDoc */
    public void abort(Transaction txn) {
        for (TaskReservation r : txnMap.remove(txn))
            r.cancel();
    }

    /** @inheritDoc */
    public void commit(Transaction txn) {
        for(TaskReservation r : txnMap.remove(txn))
            r.use();
    }

    /** @inheritDoc */
    public String getTypeName() {
        return getName();
    }

    /** @inheritDoc */
    public boolean prepare(Transaction txn) throws Exception {
        return false;
    }

    /** @inheritDoc */
    public void prepareAndCommit(Transaction txn) throws Exception {
        commit(txn);
    }
}
