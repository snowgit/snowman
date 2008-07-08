/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.darkstar.example.snowman.server;

import com.sun.darkstar.example.snowman.common.protocol.ClientProtocol;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.processor.IClientProcessor;
import com.sun.darkstar.example.snowman.common.protocol.processor.IServerProcessor;
import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedObject;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.NameNotBoundException;
import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * This class is the player's "proxy" in the world of managed
 * objects.  It implements the ClientSessionListener interface so that
 * it can be the reception point for all client session events.
 * For here, it will call other managed ibjects to respond to thsoe
 * events.
 * @author Jeffrey Kesselman
 */
class SnowmanPlayer implements Serializable, ManagedObject, 
        ClientSessionListener, IServerProcessor {

    public static long serialVersionUID = 1L;
    private static final String PREFIX = "__PLAYER_";
    private ClientSession mySession;
    private int wins;
    private int losses;
    float x;
    float y;
    private int id;
    private ManagedReference<SnowmanArea> currentAreaRef;
   
    

    static SnowmanPlayer find(ClientSession arg0) {
        String pname = PREFIX + arg0.getName();
        SnowmanPlayer player;
        try {
            player = (SnowmanPlayer) AppContext.getDataManager().getBinding(pname);
            player.setSession(arg0);
        } catch (NameNotBoundException e) {
            player = new SnowmanPlayer(arg0);
            AppContext.getDataManager().setBinding(pname, player);

        }
        return player;
    }
   
   

    private SnowmanPlayer(ClientSession session) {
        // pevents direct instantiation
    }

    public void receivedMessage(ByteBuffer arg0) {
        ClientProtocol.getInstance().parsePacket(arg0,this);
    }

    public void disconnected(boolean arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void setSession(ClientSession arg0) {
        mySession = arg0;
    }
    
    public void setArea(SnowmanArea area){
        if (area == null){
            currentAreaRef = null;
        } else {
            currentAreaRef = AppContext.getDataManager().createReference(area);
        }
    }
    
    public float ranking(){
        return 1000f*wins/losses;
    }
    
    public float getX(){
        return x;
    }
    
    public float getY(){
        return y;
    }
    
    public int getID(){
        return id;
    }
    
    public void send(ByteBuffer buff){
        buff.flip();
        mySession.send(buff);
    }
    
    public ClientSession getSession(){
        return mySession;
    }
    
     // IServerProcessor Messages

    public void ready() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void matchMe() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void moveMe(float newx, float newy) {
        x = newx;
        y = newy;
        currentAreaRef.get().send(null,
                ClientProtocol.getInstance().createMoveMePkt(x, y));
    }

    public void attack(int targetID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void getFlag(int flagID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void requestInfo(int objectID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void chatText(String text) {
        currentAreaRef.get().send(mySession, 
                ClientProtocol.getInstance().createChatPkt(text));
    }

   
   
    
}
