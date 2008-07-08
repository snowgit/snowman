/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.snowman.clientsimulator;

import com.sun.sgs.client.ClientChannel;
import com.sun.sgs.client.ClientChannelListener;
import com.sun.sgs.client.simple.SimpleClient;
import com.sun.sgs.client.simple.SimpleClientListener;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.nio.ByteBuffer;
import java.util.Properties;

/**
 *
 * @author Jeffrey Kesselman
 */
public class ClientSimulator {
    static enum PLAYERSTATE {LoggingIn,Playing}
    
    class FakePlayer implements SimpleClientListener {
        
        String name;
        String password;
        SimpleClient simpleClient;
        PLAYERSTATE state;
        
        public FakePlayer(Properties props) throws IOException{
            name = props.getProperty("name");
            password = props.getProperty("password");
            simpleClient = new SimpleClient(this);
            state = PLAYERSTATE.LoggingIn;
            simpleClient.login(props);
        }
        

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(name,password.toCharArray());
        }

        public void loggedIn() {
            state = PLAYERSTATE.Playing;
        }

        public void loginFailed(String arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public ClientChannelListener joinedChannel(ClientChannel arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void receivedMessage(ByteBuffer arg0) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void reconnecting() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void reconnected() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        public void disconnected(boolean arg0, String arg1) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
    }

}
