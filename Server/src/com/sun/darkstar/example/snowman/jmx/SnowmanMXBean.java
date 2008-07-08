/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.snowman.jmx;

/**
 *
 * @author Jeffrey Kesselman
 */
public interface SnowmanMXBean {
    public int getNumberOfGamesSessions();
    public int getNumberOfAIperSession();
    public void setNumberOfAIperSession(int numAI);
}
