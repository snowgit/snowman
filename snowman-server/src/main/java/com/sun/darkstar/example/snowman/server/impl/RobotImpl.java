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
package com.sun.darkstar.example.snowman.server.impl;

import com.sun.darkstar.example.snowman.common.util.Coordinate;
import com.sun.darkstar.example.snowman.common.util.HPConverter;
import com.sun.darkstar.example.snowman.server.context.SnowmanAppContext;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanGame;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanPlayer;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.ObjectNotFoundException;
import com.sun.sgs.app.Task;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Random;

/**
 * Robot player
 * @author kbt
 */
public class RobotImpl extends SnowmanPlayerImpl {
    
    private final static int MOVE_DELAY_DEFAULT = 2000;
    
    private final int moveDelay;
    private final Random random = new Random();
    
    // Player IDs of potential targets
    private ArrayList<Integer> targets = null;
    
    public RobotImpl(SnowmanAppContext appContext, String name) {
        super(appContext, name, null);
        moveDelay = Integer.getInteger("robotMoveDelay", MOVE_DELAY_DEFAULT);
        scheduleMove(10000);// TODO need to find out when the game starts
    }
    
    private void scheduleMove(int delay) {
        appContext.getTaskManager().scheduleTask(
                new MoveTask(appContext.getDataManager().createReference((RobotImpl)this)),
                                delay);
    }
    
    private void moveRobot() {
        if (currentGameRef == null) // game over
            return;
        
        if (targets == null) {
            appContext.getDataManager().markForUpdate(this);
            SnowmanGame game = currentGameRef.get();
            targets = new ArrayList<Integer>();
            for (int id : game.getPlayerIds()) {
                if (game.getPlayer(id).getTeamColor() != getTeamColor())
                    targets.add(id);
            }
        }
        
        if (state != PlayerState.NONE || state != PlayerState.DEAD) {
            appContext.getDataManager().markForUpdate(this);
            SnowmanGame game = currentGameRef.get();
            long now = System.currentTimeMillis();
            Coordinate currentPos = getExpectedPositionAtTime(now);
            SnowmanPlayer target =
                    game.getPlayer(targets.get(random.nextInt(targets.size())));
            
            // If a target is available, move towards it. Attack if it's
            // within range
            if (target != null) {
                Coordinate targetPos = target.getExpectedPositionAtTime(now);

                float dx = currentPos.getX() - targetPos.getX();
                float dy = currentPos.getY() - targetPos.getY();
                float range = HPConverter.getInstance().convertRange(hitPoints);
                if (((dx * dx) + (dy * dy)) < (range * range)) {
                    attack(now, target.getID(), currentPos.getX(), currentPos.getY());
                }
                moveMe(now,
                       currentPos.getX(), currentPos.getY(),
                       targetPos.getX() + 10 * (random.nextFloat() - 0.5f),
                       targetPos.getY() + 10 * (random.nextFloat() - 0.5f));
            } else
                moveMe(now,
                       currentPos.getX(), currentPos.getY(),
                       currentPos.getX() + 10 * (random.nextFloat() - 0.5f),
                       currentPos.getY() + 10 * (random.nextFloat() - 0.5f));
            scheduleMove(moveDelay);
        } else
            scheduleMove(moveDelay * 4);

    }
    
    static private class MoveTask implements Task, Serializable {
        final ManagedReference<RobotImpl> robotRef;
        
        MoveTask(ManagedReference<RobotImpl> robotRef) {
            this.robotRef = robotRef;
        }

        public void run() throws Exception {
            try {
                robotRef.get().moveRobot();
            } catch (ObjectNotFoundException gameDone) {}
        }
    }
    
    // There is no session associated with the robot, so related methods
    // are overriden with noops
    
    @Override
    public void send(ByteBuffer buff) {}
    
    @Override
    public void setSession(ClientSession arg0) {}
    
    @Override
    public ClientSession getSession() {
        return null;
    }
}