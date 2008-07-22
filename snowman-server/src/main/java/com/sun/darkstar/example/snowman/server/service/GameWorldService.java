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
import com.sun.sgs.service.Service;
import com.sun.sgs.service.Transaction;
import com.sun.sgs.service.TransactionProxy;
import com.sun.sgs.service.NonDurableTransactionParticipant;
import com.sun.sgs.kernel.TaskScheduler;
import com.sun.sgs.kernel.TransactionScheduler;
import com.sun.sgs.kernel.TaskReservation;
import com.sun.sgs.app.Channel;
import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;
import com.jme.scene.Spatial;
import com.jme.math.Vector3f;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;


/**
 * The <code>GameWorldService</code> is an implementation of a Project
 * Darkstar service that provides utility methods to calculate
 * collision detection and spatial information against the static
 * game world geometry.
 * 
 * @author Owen Kellett
 */
public class GameWorldService implements Service, NonDurableTransactionParticipant {
    
    /** The logger for this class. */
    private static Logger logger = Logger.getLogger(GameWorldService.class.getName());
    
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
    
    
    public GameWorldService(Properties properties,
                            ComponentRegistry registry,
                            TransactionProxy txnProxy) {
        this.taskScheduler = registry.getComponent(TaskScheduler.class);
        this.txnScheduler = registry.getComponent(TransactionScheduler.class);
        this.txnProxy = txnProxy;
        
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
    
    /**
     * <p>
     * Calculate the actual path of a snowman attempting to walk
     * from the given start point to the given end point.  This method
     * will check if any barriers are in the snowman's path by checking
     * for an intersection with the <code>Spatial</code> game world by
     * using the <code>CollisionManager</code>. 
     * If there is a collision, then a new destination location will be
     * calculated and returned by the <code>CollisionManager</code>.
     * </p>
     * 
     * <p>
     * This method will schedule a new
     * <code>Task</code> when it completes that will broadcast a 
     * MOVEMOB message to all clients on the channel indicating that
     * the player is attempting to move from the start position to the
     * newly calculated destination position.  The <code>Task</code> will not be
     * executed until the calling transaction (if there is one) commits.
     * </p>
     * 
     * @param playerId id of the player being moved
     * @param startx x coordinate of the start position
     * @param starty y coordinate of the start position
     * @param endx x coordinate of the destination position
     * @param endy y coordinate of the destination position
     * @param timestart timestamp that the player began moving
     * @param gameChannel channel to broadcast the resulting MOVEMOB message
     */
    public void trimPath(int playerId,
                         float startx,
                         float starty, 
                         float endx, 
                         float endy, 
                         long timestart,
                         Channel gameChannel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * <p>
     * Validate that a snowball can be thrown from the start position 
     * to the end position.  This will verify that there are no collisions
     * with the <code>Spatial</code> game world between the two coordinates
     * at the static THROWHEIGHT.
     * </p>
     * 
     * @param startx coordinate of the start position
     * @param starty y coordinate of the start position
     * @param endx x coordinate of the target position
     * @param endy y coordinate of the target position
     * @return true if there are no collisions with static entities between the two points
     */
    public boolean validThrow(float startx,
                              float starty, 
                              float endx,
                              float endy) {
        return SingletonRegistry.getCollisionManager().validate(startx, starty, endx, endy, gameWorld);
    }

    private void joinCurrentTransaction() {
        Transaction txn = txnProxy.getCurrentTransaction();
        HashSet<TaskReservation> set = txnMap.get(txn);
        
        if(set == null) {
            set = new HashSet<TaskReservation>();
            txnMap.put(txn, set);
            txn.join(this);
        }
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
