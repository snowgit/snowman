/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.snowman.jmx;

/**
 *
 * @author Jeffrey Kesselman
 */
public class SnowmanJMX implements SnowmanMXBean {
    private int numberOfPlayers = 0;
    int NumberOfGamesSessions = 0;
    SnowmanJMXHelperService helper;

    public SnowmanJMX(SnowmanJMXHelperService helper) {
        this.helper = helper;
    }

    public int getNumberOfGamesSessions() {
        return helper.getSessionCount();
    }

    public int getNumberOfAIperSession() {
       return helper.getAICount();
    }

    public void setNumberOfAIperSession(int numAI) {
        //TODO
    }

}
