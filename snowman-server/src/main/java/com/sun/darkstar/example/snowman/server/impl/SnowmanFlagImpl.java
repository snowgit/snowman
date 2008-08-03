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

import com.sun.darkstar.example.snowman.server.interfaces.SnowmanFlag;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanPlayer;
import com.sun.darkstar.example.snowman.server.interfaces.TeamColor;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import java.io.Serializable;

/**
 * This class represents a team's flag.  It can be on the map or be held by
 * a snowman
 * @author Jeffrey Kesselman
 * @author Owen Kellett
 */
public class SnowmanFlagImpl implements SnowmanFlag, ManagedObject, Serializable {

    static public final long serialVersionbUID = 1L;

    /**
     * This enum gives the possible colors for a flag/team in the snowman
     * game
     */
    static public enum TEAMCOLOR {

        RED, GREEN
    }
    
    /**
     * The X location of a flag that is on the map
     */
    private float x;
    /**
     * The Y location of a flag on the mao
     */
    private float y;
    /**
     * The X location of the centroid of the goal circle this flag must be
     * carried into to win the game
     */
    private float goalX;
     /**
     * The Y location of the centroid of the goal circle this flag must be
     * carried into to win the game
     */
    private float goalY;
    /**
     * The radius of the goal circle
     */
    private float goalRadius;
    /**
     * This is either a reference to a SnowmanPlayer who is currently holding
     * th flsg or null if it is currently on the board
     */
    private ManagedReference<SnowmanPlayer> heldByRef = null;
    /**
     * The team color of this particular flag
     */
    private TeamColor flagColor;
    /**
     * The id of the flag.  This is unique among flags in the game but not
     * necc unique among all objects in the game
     */
    private int id;

    /**
     * The constructor for a flag
     * 
     * @param teamColor The color of the team that owns the flag
     * @param flagGoalX the X coordinate of the centroid of the winning circle
     * @param flagGoalY the Y coordinate of the centroid of the winning circle
     * @param flagGoalRadius the radius of the winning circle
     */
    public SnowmanFlagImpl(TeamColor teamColor, float flagGoalX, float flagGoalY,
            float flagGoalRadius) {
        flagColor = teamColor;
        goalX = flagGoalX;
        goalY = flagGoalY;
        goalRadius = flagGoalRadius;
    }

    /**
     * Returns the color of the team that owns this flag
     * @return the flag's color
     */
    public TeamColor getTeamColor() {
        return flagColor;
    }

    /**
     * This sets the flag's location on the map
     * @param x the X coord of the flag
     * @param y the Y coord of the flag
     */
    public void setLocation(float x, float y) {
        AppContext.getDataManager().markForUpdate(this);
        this.x = x;
        this.y = y;
    }

    /**
     * Ths returns the current X coordof the flag.  If the flag is held by a
     * snowman then its X coord is the snowman's X coord.  Otherwise its the
     * last X coord set for this flag.
     * @param time the time at which the position should be checked (
     * necessary if held by a snowman.)
     * @return
     */
    public float getX(long time) {
        if (heldByRef == null) {
            return x;
        } else {
            return heldByRef.get().getX(time);
        }
    }

    /**
     * Ths returns the current Y coordof the flag.  If the flag is held by a
     * snowman then its Y coord is the snowman's Y coord.  Otherwise its the
     * last Y coord set for this flag.
     * @param time the time at which the position should be checked (
     * necessary if held by a snowman.)
     * @return
     */
    public float getY(long time) {
        if (heldByRef == null ) {
            return goalY;
        } else {
            return heldByRef.get().getY(time);
        }
    }
    
    /**
     * This method sets the flag as held by a snowman.  Passing null sets it as 
     * held by no snowman.
     * @param player the snowman who holds the flag, or null
     */
    public void setHeldBy(SnowmanPlayer player){
        AppContext.getDataManager().markForUpdate(this);
        if (player == null){
            if (heldByRef != null){
                setLocation(heldByRef.get().getX(System.currentTimeMillis()),
                        heldByRef.get().getY(System.currentTimeMillis()));
            }
            heldByRef = null;
        } else {
            heldByRef = AppContext.getDataManager().createReference(player);
        }
    }
    
    /**
     * Checks to see if the flag is within its goal circle
     * @return true if the flag is within the goal circle.  otherwise false
     */
    public boolean isAtGoal(){
        //we compare in distacne sqd space for efficiency
        // for more efficiency we could cache goalRadiusSqd
        float deltaX = x-goalX;
        float deltaY = y-goalY;
        return((goalRadius*goalRadius)<= ((deltaX*deltaX)+(deltaY*deltaY)));
    }

    /**
     * This method sets the Flag ID.  A flag's ID must be unique among flags in
     * a game but may not be unique among all the objects in the game.
     * @param i the ID
     */
    public void setID(int i) {
        id = i;
    }
    
    /**
     * This method sets the Flag ID.  A flag's ID must be unique among flags in
     * a game but may not be unique among all the objects in the game.
     * @returns the flag's ID
     */
    
    public int getID(){
        return id;
    }
}
