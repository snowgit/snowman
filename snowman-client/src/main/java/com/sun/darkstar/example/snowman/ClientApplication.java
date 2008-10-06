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
package com.sun.darkstar.example.snowman;

import java.net.URL;

import com.jme.app.AbstractGame.ConfigShowMode;
import com.sun.darkstar.example.snowman.client.Client;
import com.sun.darkstar.example.snowman.client.handler.ClientHandler;
import com.sun.darkstar.example.snowman.exception.MissingComponentException;
import com.sun.darkstar.example.snowman.game.Game;

/**
 * <code>ClientApplication</code> is the main class for the client program.
 * It initializes all the <code>Component</code> and starts the program.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-23-2008 14:00 EST
 * @version Modified date: 06-06-2008 17:31 EST
 */
public class ClientApplication {

	/**
	 * Main entry point of client program.
	 * @param args Nothing needs to be passed in.
	 */
	public static void main(String[] args) {
		// Construct components.
		ClientHandler handler = new ClientHandler();
		Client client = new Client();
		Game game = new Game();
		if (System.getProperty("snowman.config.noshow") == null) {
			game.setConfigShowMode(ConfigShowMode.AlwaysShow, (URL)null);
		} else {
			game.setConfigShowMode(ConfigShowMode.ShowIfNoConfig, (URL)null);
		}

		// Establish component connections.
		handler.connect(game);
		client.connect(handler);
		game.connect(client);
		// Initialize components.
		try {
			handler.activate();
			client.activate();
			game.activate();
		} catch (MissingComponentException e) {
			e.printStackTrace();
		}
	}
}
