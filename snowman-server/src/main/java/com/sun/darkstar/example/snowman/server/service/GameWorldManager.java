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

import com.sun.sgs.app.Channel;

/**
 * The <code>GameWorldManager</code> provides application level access
 * to the {@link GameWorldService} running inside of the 
 * Project Darkstar stack.  
 * 
 * @author Owen Kellett
 */
public class GameWorldManager {
    
    private final GameWorldService backingService;
    
    public GameWorldManager(GameWorldService backingService) {
        this.backingService = backingService;
    }

    /**
     * <p>
     * Call through to the backing {@link GameWorldService} 
     * trimPath method.
     * </p>
     * 
     * @see GameWorldService#trimPath trimPath
     */
    public void trimPath(int playerId,
                         float startx,
                         float starty, 
                         float endx, 
                         float endy, 
                         long timestart,
                         Channel gameChannel) {
        backingService.trimPath(playerId,
                                startx, 
                                starty, 
                                endx, 
                                endy, 
                                timestart,
                                gameChannel);
    }
    
    /**
     * <p>
     * Call through to the backing {@link GameWorldService} 
     * validThrow method.
     * </p>
     * 
     * @see GameWorldService#validThrow validThrow
     */
    public boolean validThrow(float startx,
                              float starty, 
                              float endx,
                              float endy) {
        return backingService.validThrow(startx, starty, endx, endy);
    }
}
