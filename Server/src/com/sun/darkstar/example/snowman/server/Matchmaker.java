/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.darkstar.example.snowman.server;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 *
 * @author Jeffrey Kesselman
 */
public class Matchmaker implements Serializable, ManagedObject {

    private static Logger logger = Logger.getLogger(Matchmaker.class.getName());
    public static final long serialVersionUID = 1L;
    ManagedReference<SnowmanPlayer>[] waiting =
            new ManagedReference[4];

    public Matchmaker() {
        clearQueue();
    }

    private void clearQueue() {
        for (int i = 0; i < waiting.length; i++) {
            waiting[i] = null;
        }
    }

    private int getNullIdx() {
        for (int i = 0; i < waiting.length; i++) {
            if (waiting[i] == null) {
                return i;
            }
        }
        return -1;
    }

    public void addWaitingPlayer(SnowmanPlayer player) {
        int idx = getNullIdx();
        if (idx == -1){
            logger.severe("Error: tried to add player to full wait quwue");
            return;
        }
        waiting[idx] = AppContext.getDataManager().createReference(player);
        if (getNullIdx()==-1){ // full queue
            launchGameSession();
        }
    }
    
    public void removeWaitingPlayer(SnowmanPlayer player){
        ManagedReference<SnowmanPlayer> playerRef = 
                AppContext.getDataManager().createReference(player);
        for (int i=0;i<waiting.length;i++){
            if (waiting[i].equals(playerRef)){
                waiting[i] = null;
                return;
            }
        }
    }
    
    private void launchGameSession(){
        SnowmanGame.create(waiting);
        clearQueue();
    }
}
