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

import com.sun.sgs.service.Service;


/**
 * The <code>GameWorldService</code> provides utility methods to calculate
 * collision detection and spatial information against the static
 * game world geometry.
 * 
 * @author Owen Kellett
 */
public interface GameWorldService extends Service {
    
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
     * <code>Task</code> to calculate the new destination position.
     * The <code>Task</code> will not be
     * executed until the calling transaction (if there is one) commits.
     * The given callback interface will then be notified of the results.
     * </p>
     * 
     * @param playerId id of the player being moved
     * @param startx x coordinate of the start position
     * @param starty y coordinate of the start position
     * @param endx x coordinate of the destination position
     * @param endy y coordinate of the destination position
     * @param timestart timestamp that the player began moving
     * @param callback callback interface to notify of results
     */
    public void trimPath(int playerId,
                         float startx,
                         float starty, 
                         float endx, 
                         float endy, 
                         long timestart,
                         GameWorldServiceCallback callback);
    
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
                              float endy);

}
