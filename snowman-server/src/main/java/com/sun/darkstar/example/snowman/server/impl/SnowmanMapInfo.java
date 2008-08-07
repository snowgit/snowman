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

import com.sun.darkstar.example.snowman.common.protocol.enumn.ETeamColor;
import com.sun.darkstar.example.snowman.common.util.Coordinate;
import java.util.Random;

/**
 * Static class that is used to retrieve information about snowman
 * maps.
 * 
 * @author Owen Kellett
 */
public class SnowmanMapInfo 
{
    public static String DEFAULT = "default_map";
    private static float[] defaultXY = new float[]{50, 50};
    private static Random generator = new Random();
    
    /**
     * Retrieve the x,y dimensions of the map with the given name.
     * @param map name of the map
     * @return array of floats representing the x,y dimensions of the map
     */
    public static float[] getDimensions(String map) {
        return defaultXY;
    }
    
    /**
     * Return the player'th spawn position for the given team on the given
     * map assuming there are totalPlayers per team.
     * This method will return positions equidistant from eachother along
     * the X axis and at a fixed Y coordinate.
     * 
     * @param map name of the map
     * @param team team to retrieve a spawn point for
     * @param player player'th player on the team (starting with 1)
     * @param teamPlayers number of players on the team
     * @return
     */
    public static Coordinate getSpawnPosition(String map,
                                              ETeamColor team,
                                              int player,
                                              int teamPlayers) {
        float[] dimensions = getDimensions(map);
        float xAxis = dimensions[0];
        float yAxis = dimensions[1];
        
        //set the y coordinate to be either near the bottom or near
        //the top depending on the team
        float y = 0;
        switch(team) {
            case RED:
                y = yAxis/8.0f;
                break;
            case GREEN:
                y = yAxis/8.0f*7.0f;
                break;
        }
        
        //set the x position to be equidistant
        float x = xAxis/(float)(teamPlayers+1.0f)*player;
        
        return new Coordinate(x,y);
    }
    
    /**
     * Return a random respawn position for the given team.
     * 
     * @param map name of the map
     * @param team team to retrieve a spawn point for
     * @return
     */
    public static Coordinate getRespawnPosition(String map,
                                                ETeamColor team) {
        return getSpawnPosition(map, 
                                team, 
                                generator.nextInt(10) + 1,
                                10);
    }
    
    public static Coordinate getFlagStart(String map,
                                          ETeamColor team) {
        float[] dimensions = getDimensions(map);
        float xAxis = dimensions[0];
        float yAxis = dimensions[1];
        
        switch(team) {
            case RED:
                return new Coordinate(xAxis / 2.0f, yAxis / 10.0f);
            case GREEN:
                return new Coordinate(xAxis / 2.0f, yAxis / 10.0f * 9.0f);
        }
        return null;
    }
    
    public static Coordinate getFlagGoal(String map,
                                         ETeamColor team) {
        switch(team) {
            case RED:
                return getFlagStart(map, ETeamColor.GREEN);
            case GREEN:
                return getFlagStart(map, ETeamColor.RED);
        }
        return null;
    }
}
