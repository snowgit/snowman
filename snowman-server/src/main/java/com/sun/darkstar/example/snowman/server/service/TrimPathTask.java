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

import com.sun.darkstar.example.snowman.common.util.CollisionManager;
import com.sun.sgs.kernel.KernelRunnable;
import com.jme.scene.Spatial;
import com.jme.math.Vector3f;

/**
 * Task that queries the <code>CollisionManager</code> to
 * verify that a move between two points in the game world is valid.
 * The task trims down the destination point so that it stops
 * the move at any blocking static entity in the path.  It then
 * broadcasts a MOVEMOB message on the given Channel to indicate
 * that the move has been made.
 * 
 * @author Owen Kellett
 */
public class TrimPathTask implements KernelRunnable
{
    private int playerId;
    private float startx;
    private float starty;
    private float endx;
    private float endy;
    private long timestart;
    private GameWorldServiceCallback callback;
    private Spatial gameWorld;
    private CollisionManager collisionManager;

    public TrimPathTask(int playerId,
                        float startx,
                        float starty,
                        float endx,
                        float endy,
                        long timestart,
                        GameWorldServiceCallback callback,
                        Spatial gameWorld,
                        CollisionManager collisionManager) {
        this.playerId = playerId;
        this.startx = startx;
        this.starty = starty;
        this.endx = endx;
        this.endy = endy;
        this.timestart = timestart;
        this.callback = callback;
        this.gameWorld = gameWorld;
        this.collisionManager = collisionManager;
    }
    
    public String getBaseTaskType() {
        return this.getClass().getName();
    }

    /**
     * Calculates the truncated destination based on the start and end
     * points given in the constructor.  This new destination is
     * then used in a broadcast MOVEMOB message to the channel notifying
     * that the move has occurred.
     * 
     * @throws java.lang.Exception
     */
    public void run() throws Exception {
        Vector3f destination = collisionManager.getDestination(startx, starty, endx, endy, gameWorld);
        callback.trimPathComplete(playerId, startx, starty, destination.getX(), destination.getZ(), timestart);
    }
}
