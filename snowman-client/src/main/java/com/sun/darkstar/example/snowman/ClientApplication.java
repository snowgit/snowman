package com.sun.darkstar.example.snowman;

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
