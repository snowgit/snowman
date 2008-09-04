package com.sun.darkstar.example.snowman.client.handler.message;

import java.net.PasswordAuthentication;
import java.nio.ByteBuffer;

import com.sun.darkstar.example.snowman.client.handler.ClientHandler;
import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.game.task.util.TaskManager;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.BattleState;
import com.sun.sgs.client.ClientChannel;
import com.sun.sgs.client.ClientChannelListener;
import com.sun.sgs.client.simple.SimpleClientListener;

/**
 * <code>MessageListener</code> is a listener which monitors packets received
 * from the the <code>Server</code>.
 * <p>
 * <code>MessageListener</code> passes received packets to <code>MessageProcessor</code>
 * for logic processing.
 * <p>
 * <code>MessageListener</code> is created and attached to its parent
 * <code>ClientHandler</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-23-2008 15:24 EST
 * @version Modified date: 07-14-2008 16:20 EST
 */
public class MessageListener implements SimpleClientListener, ClientChannelListener {
	/**
	 * The <code>ClientHandler</code> this listener is attached to.
	 */
	private final ClientHandler handler;
	
	/**
	 * Constructor of <code>MessageListener</code>.
	 * @param handler The <code>ClientHandler</code> this listener is attached to.
	 */
	public MessageListener(ClientHandler handler) {
		this.handler = handler;
	}

	@Override
	public PasswordAuthentication getPasswordAuthentication() {
		return this.handler.getAuthentication();
	}

	@Override
	public void loggedIn() {
            TaskManager.getInstance().createTask(ETask.LoginSuccess);
        }

	@Override
	public void loginFailed(String reason) {
		TaskManager.getInstance().createTask(ETask.ResetLogin, reason);
	}

	@Override
	public void disconnected(boolean graceful, String reason) {
            this.handler.getGame().finish();
	}

	@Override
	public ClientChannelListener joinedChannel(ClientChannel channel) {
		return this;
	}

	@Override
	public void receivedMessage(ByteBuffer message) {
		SingletonRegistry.getMessageHandler().parseClientPacket(message, this.handler.getProcessor());
	}

	@Override
	public void reconnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reconnecting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftChannel(ClientChannel channel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivedMessage(ClientChannel channel, ByteBuffer message) {
		SingletonRegistry.getMessageHandler().parseClientPacket(message, this.handler.getProcessor());
	}
}
