package com.sun.darkstar.example.snowman.client;

import com.sun.darkstar.example.snowman.client.handler.ClientHandler;
import com.sun.darkstar.example.snowman.exception.MissingComponentException;
import com.sun.darkstar.example.snowman.interfaces.IComponent;
import com.sun.darkstar.example.snowman.unit.Component;

import com.sun.sgs.client.simple.SimpleClient;

/**
 * <code>Client</code> is a <code>Component</code> which represents the communication
 * protocol between the client application and the server application.
 * <p>
 * <code>Client</code> is responsible for handling outgoing messages from the <code>Game</code>
 * to the server.
 * <p>
 * <code>Client</code> needs to be connected with <code>ClientHandler</code> before
 * initialization in order to establish connection with the server.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-23-2008 14:52 EST
 * @version Modified date: 07-10-2008 14:26 EST
 */
public class Client extends Component{
	/**
	 * The <code>ClientHandler</code> <code>Component</code>.
	 */
	private ClientHandler handler;
	/**
	 * The <code>SimpleClient</code> instance.
	 */
	private SimpleClient connection;

	/**
	 * Constructor of <code>Client</code>.
	 */
	public Client() {}

	@Override
	public boolean validate() throws MissingComponentException {
		if(this.handler == null) {
			throw new MissingComponentException(ClientHandler.class.toString());
		}
		return true;
	}

	@Override
	public void initialize() {
		this.connection = new SimpleClient(this.handler.getListener());
	}

	@Override
	public void connect(IComponent component) {
		if(component instanceof ClientHandler) {
			this.handler = (ClientHandler)component;
		}
	}
	
	/**
	 * Retrieve the actual client server connection.
	 * @return The <code>SimpleClient</code> connection.
	 */
	public SimpleClient getConnection() {
		return this.connection;
	}
}
