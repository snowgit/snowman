/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.darkstar.example.snowman.server;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import java.io.Serializable;

/**
 *
 * @author Jeffrey Kesselman
 */
public class SnowmanFlag implements ManagedObject, Serializable {

    static public final long serialVersionbUID = 1L;

    static public enum TEAMCOLOR {

        RED, GREEN
    }
    private float x;
    private float y;
    private float goalX;
    private float goalY;
    private float goalRadius;
    private ManagedReference<SnowmanPlayer> heldByRef = null;
    private TEAMCOLOR flagColor;
    private int id;

    public SnowmanFlag(TEAMCOLOR teamColor, float flagGoalX, float flagGoalY,
            float flagGoalRadius) {
        flagColor = teamColor;
        goalX = flagGoalX;
        goalY = flagGoalY;
        goalRadius = flagGoalRadius;
    }

    public TEAMCOLOR getColor() {
        return flagColor;
    }

    public void SetLocation(float x, float y) {
        AppContext.getDataManager().markForUpdate(this);
        this.x = x;
        this.y = y;
    }

    public float getX() {
        if (heldByRef == null) {
            return x;
        } else {
            return heldByRef.get().getX();
        }
    }

    public float getY() {
        if (heldByRef == null ) {
            return goalY;
        } else {
            return heldByRef.get().getY();
        }
    }
    
    public void setHeldBy(SnowmanPlayer player){
        AppContext.getDataManager().markForUpdate(this);
        if (player == null){
            heldByRef = null;
        } else {
            heldByRef = AppContext.getDataManager().createReference(player);
        }
    }
    
    public boolean isAtGoal(){
        //we compare in distacne sqd space for efficiency
        // for more efficiency we could cache goalRadiusSqd
        float deltaX = x-goalX;
        float deltaY = y-goalY;
        return((goalRadius*goalRadius)<= ((deltaX*deltaX)+(deltaY*deltaY)));
    }

    public void setID(int i) {
        id = i;
    }
    
    public int getID(){
        return id;
    }
}
