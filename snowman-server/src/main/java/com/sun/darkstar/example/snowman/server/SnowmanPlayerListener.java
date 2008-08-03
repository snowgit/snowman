/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.snowman.server;

import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.darkstar.example.snowman.server.interfaces.SnowmanPlayer;
import com.sun.darkstar.example.snowman.server.interfaces.Matchmaker;
import com.sun.darkstar.example.snowman.server.interfaces.EntityFactory;
import com.sun.darkstar.example.snowman.server.context.SnowmanAppContext;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.NameNotBoundException;
import java.nio.ByteBuffer;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * The <code>SnowmanPlayerListener</code> is responsible for receiving
 * incoming messages and forwarding them to the appropriate 
 * {@link SnowmanPlayer} for processing.
 * 
 * @author Owen Kellett
 */
public class SnowmanPlayerListener implements ManagedObject, Serializable,
        ClientSessionListener
{
    private static Logger logger = Logger.getLogger(SnowmanPlayerListener.class.getName());
    public static final long serialVersionUID = 1L;
    private static final String PREFIX = "__PLAYER_";
    
    private ManagedReference<SnowmanPlayer> playerRef;
    private ManagedReference<Matchmaker> matchmakerRef;
    
    /**
     * Lookup the SnowmanPlayerListener in the datastore.  If it is not
     * there, create a new one and return it
     * @param session ClientSession of the connecting client
     * @param appContext the application context
     * @param entityFactory factory used to create a SnowmanPlayer if necessary
     * @param matchmaker the matchmaker requesting the player
     * @return
     */
    public static SnowmanPlayerListener find(ClientSession session,
                                             SnowmanAppContext appContext,
                                             EntityFactory entityFactory,
                                             Matchmaker matchmaker) {
        String pname = PREFIX + session.getName();
        SnowmanPlayerListener player;
        try {
            player = (SnowmanPlayerListener) appContext.getDataManager().getBinding(pname);
            player.getSnowmanPlayer().setSession(session);
        } catch (NameNotBoundException e) {
            player = new SnowmanPlayerListener(appContext,
                                               entityFactory.createSnowmanPlayer(appContext, session),
                                               matchmaker);
            appContext.getDataManager().setBinding(pname, player);
        }
        return player;
    }
    
    private SnowmanPlayerListener(SnowmanAppContext appContext,
                                  SnowmanPlayer player,
                                  Matchmaker matchmaker)
    {
        playerRef = appContext.getDataManager().createReference(player);
        matchmakerRef = appContext.getDataManager().createReference(matchmaker);
    }
    
    public void receivedMessage(ByteBuffer arg0) {
        SingletonRegistry.getMessageHandler().parseServerPacket(arg0,playerRef.get().getProcessor());
    }

    public void disconnected(boolean arg0) {
        if (matchmakerRef!=null){
            matchmakerRef.get().removeWaitingPlayer(playerRef.get());
            matchmakerRef = null;
        }
        if (playerRef.get().getGame() != null) {
            playerRef.get().getGame().removePlayer(playerRef.get());
            playerRef.get().setGame(null);
        }
        playerRef.get().setReadyToPlay(false);
        logger.info("Player "+playerRef.get().getName()+" logged out");
    }
    
    public SnowmanPlayer getSnowmanPlayer() {
        return playerRef.get();
    }
}
