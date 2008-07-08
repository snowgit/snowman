package com.sun.darkstar.example.snowman.client.handler;

import com.sun.darkstar.example.snowman.client.handler.message.MessageListener;
import com.sun.darkstar.example.snowman.client.handler.message.MessageProcessor;
import com.sun.darkstar.example.snowman.exception.MissingComponentException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.interfaces.IComponent;
import com.sun.darkstar.example.snowman.unit.Component;

/**
 * <code>ClientHandler</code> is a composed <code>Component</code> that is
 * responsible for handling all the messages received from the server.
 * <p>
 * <code>ClientHandler</code> is composed by a single <code>MessageListener</code>
 * and a single <code>MessageProcessor</code>. The sub-components are created
 * at construction time since they define <code>ClientHandler</code>.
 * <p>
 * <code>ClientHandler</code> needs to be connected with <code>Game</code> before
 * initialization.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-28-2008 16:40 EST
 * @version Modified date: 06-06-2008 17:26 EST
 */
public class ClientHandler extends Component {
	/**
	 * The single <code>MessageListener</code> instance.
	 */
	private final MessageListener listener;
	/**
	 * The <code>MessageProcessor</code> instance.
	 */
	private final MessageProcessor processor;
	/**
	 * The <code>Game</code> instance.
	 */
	private Game game;
	
	/**
	 * Constructor of <code>ClientHandler</code>.
	 */
	public ClientHandler() {
		this.listener = new MessageListener(this);
		this.processor = new MessageProcessor(this);
	}

	@Override
	public boolean validate() throws MissingComponentException {
		if(this.game == null) {
			throw new MissingComponentException(Game.class.toString());
		}
		return true;
	}

	@Override
	@SuppressWarnings("unused")
	public void initialize() {}

	@Override
	public void connect(IComponent component) {
		if(component instanceof Game) {
			this.game = (Game)component;
		}
	}
	
	/**
	 * Retrieve the <code>Game</code> <code>Component</code>.
	 * @return The <code>Game</code> instance.
	 */
	public Game getGame() {
		return this.game;
	}
	
	/**
	 * Retrieve the <code>MessageListener</code> sub-component.
	 * @return The <code>MessageListener</code> instance.
	 */
	public MessageListener getListener() {
		return this.listener;
	}
	
	/**
	 * Retrieve the <code>MessageProcessor</code> sub-component.
	 * @return The <code>MessageProcessor</code> instance.
	 */
	public MessageProcessor getProcessor() {
		return this.processor;
	}
}
