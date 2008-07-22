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

/**
 * Callback interface to notify users of the <code>GameWorldService</code>
 * of results.
 * 
 * @author Owen Kellett
 */
public interface GameWorldServiceCallback {

    /**
     * Called when the <code>GameWorldService</code> completes trimming
     * the path for the given player id from the start position at the
     * given start time.  The endx and endy parameters are equivalent to the
     * new destination location of the move path.
     * 
     * @param playerId id of the player being moved
     * @param startx x coordinate of the start position
     * @param starty y coordinate of the start position
     * @param endx x coordinate of the trimmed destination position
     * @param endy y coordinate of the trimmed destination position
     * @param timestart timestamp that the player began moving
     */
    public void trimPathComplete(int playerId,
                                 float startx,
                                 float starty,
                                 float endx,
                                 float endy,
                                 long timestart);
    
    /**
     * Called when the <code>GameWorldService</code> is unable to complete
     * a trim path calculation request due to resource limitations or some
     * other problem.
     * 
     * @param playerId id of the player being moved
     * @param startx x coordinate of the start position
     * @param starty y coordinate of the start position
     * @param timestart timestamp that the player began moving
     */
    public void trimPathFailure(int playerId,
                                float startx, 
                                float stary,
                                long timestart);
}
