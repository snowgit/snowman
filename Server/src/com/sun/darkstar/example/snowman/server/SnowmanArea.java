/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.snowman.server;

import com.sun.darkstar.example.snowman.common.protocol.ServerProtocol;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.Channel;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.Delivery;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.util.ScalableHashSet;
import java.io.Serializable;
import java.nio.ByteBuffer;


/**
 *
 * @author Jeffrey Kesselman
 */
public class SnowmanArea implements Serializable, ManagedObject {
    static final long serialVersionUID = 1L;
    private static String PREFIX="__ARENA_";
    private static String CHANNEL_PREFIX = "__ARENA_CHANNEL_";
    private String name;
    private ManagedReference<Channel> channelRef;
    private ManagedReference<ScalableHashSet<SnowmanPlayer>>
            playersRef;
    
    public SnowmanArea(String arenaName){
        name = arenaName;
        channelRef = AppContext.getDataManager().createReference(
                AppContext.getChannelManager().createChannel(
                    CHANNEL_PREFIX+name, null, Delivery.RELIABLE));
        playersRef = AppContext.getDataManager().createReference(
                new ScalableHashSet<SnowmanPlayer>());
    }
    
    public void enterSpace(SnowmanPlayer newPlayer){
        ScalableHashSet<SnowmanPlayer> playerSet = 
              playersRef.getForUpdate();
        for (SnowmanPlayer player : playerSet){
            newPlayer.send(ServerProtocol.getInstance().createAddMOBPkt(
                    player.getID(), player.getX(), player.getY(), 
                    EMOBType.SNOWMAN));
        }
        Channel chan = channelRef.getForUpdate();
        chan.join(newPlayer.getSession());
        newPlayer.setArea(this);
        send(null, ServerProtocol.getInstance().createAddMOBPkt(
                    newPlayer.getID(), newPlayer.getX(), newPlayer.getY(), 
                    EMOBType.SNOWMAN));

    }
    
    public void leaveSpace(SnowmanPlayer player){
       Channel chan = channelRef.getForUpdate();
       chan.leave(player.getSession());
       player.setArea(null);
       send(null,ServerProtocol.getInstance().createRemoveMOBPkt(
               player.getID()));
    }

    public void send(ClientSession sess, ByteBuffer buff) {
        buff.flip();
        Channel chan = channelRef.get();
        chan.send(sess,buff);
    }
    
    
}
