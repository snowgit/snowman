/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.snowman.server;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import java.io.Serializable;
import java.util.Properties;

/**
 *
 * @author Jeffrey Kesselman
 */
public class SnowmanServer implements ManagedObject, Serializable, AppListener{
    public static long serialVersionUID = 1L;
    private ManagedReference<SnowmanArea> snowmanLoungeRef;
    
    public void initialize(Properties arg0) {
        snowmanLoungeRef = AppContext.getDataManager().createReference(
                new SnowmanArea("Lounge"));
    }

    public ClientSessionListener loggedIn(ClientSession arg0) {
        SnowmanPlayer player =  SnowmanPlayer.find(arg0);
        snowmanLoungeRef.get().enterSpace(player);
        return player;
    }

}
