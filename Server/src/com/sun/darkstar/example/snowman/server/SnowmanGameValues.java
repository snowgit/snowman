/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.snowman.server;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.NameNotBoundException;
import java.io.Serializable;

/**
 *
 * @author Jeffrey Kesselman
 */
public class SnowmanGameValues implements Serializable, ManagedObject{
    public static final long serialVersionUID = 1L;
    private static final String BOUNDNAME = "__SNOWMAN_VALUES";
    int numberOfGameSessions;
    int numberOfAIperSession;
    
    private SnowmanGameValues(){
        // prevents direct instantiation;
    }
    
    static public SnowmanGameValues get(){
        SnowmanGameValues obj = null;
        try {
            obj = (SnowmanGameValues) 
                    AppContext.getDataManager().getBinding(BOUNDNAME);
        } catch (NameNotBoundException e){
            obj = new SnowmanGameValues();
            AppContext.getDataManager().setBinding(BOUNDNAME, obj);
        }
        return obj;
    }
    
    public int getNumberOfGameSessions(){
        return numberOfGameSessions;
    }
    
    public int getNumberOfAIperSession(){
        return numberOfAIperSession;
    }
}
