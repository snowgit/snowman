/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.snowman.server;

import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import java.io.Serializable;

/**
 *
 * @author Jeffrey Kesselman
 */
class SnowmanGame implements ManagedObject, Serializable {

    static SnowmanGame create(ManagedReference<SnowmanPlayer>[] playerRefs) {
        return new SnowmanGame(playerRefs);
    }
    
    private ManagedReference<SnowmanPlayer>[] playerRefs;
    
    private SnowmanGame(ManagedReference<SnowmanPlayer>[] playerRefs){
        this.playerRefs = 
                new ManagedReference[playerRefs.length];
        System.arraycopy(playerRefs, 0, this.playerRefs, 0,
                playerRefs.length);
    }

}
