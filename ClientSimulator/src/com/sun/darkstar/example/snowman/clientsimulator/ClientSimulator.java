/*
* Copyright (c) 2008, Sun Microsystems, Inc.
*
* All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*     * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*     * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer in
*       the documentation and/or other materials provided with the
*       distribution.
*     * Neither the name of Sun Microsystems, Inc. nor the names of its
*       contributors may be used to endorse or promote products derived
*       from this software without specific prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
* A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
* OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
* THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
* OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
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
