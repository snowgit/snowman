package com.sun.darkstar.example.snowman.client.handler.message;

import com.sun.darkstar.example.snowman.client.handler.ClientHandler;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.processor.IClientProcessor;

/**
 * <code>MessageProcessor</code> is a processing unit responsible for
 * processing all the messages received by the <code>MessageListener</code>.
 * <p>
 * <code>MessageProcessor</code> generates <code>ITask</code> based on
 * the received messages and buffers these <code>ITask</code> inside
 * <code>TaskManager</code> for processing.
 * <p>
 * <code>MessageProcessor</code> is created and attached to its parent
 * <code>ClientHandler</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-27-2008 11:57 EST
 * @version Modified date: 05-29-2008 16:11 EST
 */
public class MessageProcessor implements IClientProcessor {
	/**
	 * The <code>ClientHandler</code> this processor is attached to.
	 */
	private final ClientHandler handler;
	
	/**
	 * Constructor of <code>MessageProcessor</code>.
	 * @param handler The <code>ClientHandler</code> this processor is attached to.
	 */
	public MessageProcessor(ClientHandler handler) {
		this.handler = handler;
	}

	@Override
	public void enterLounge(int myID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void newGame(int myID, String mapname) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endGame(EEndState endState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMOB(int objectID, float x, float y, EMOBType objType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveMOB(int objectID, float startx, float starty, float endx, float endy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMOB(int objectID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attachObject(int sourceID, int targetID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void attacked(int sourceID, int targetID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(int objectID, String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHP(int objectID, int hp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chatText(String text) {
		// TODO Auto-generated method stub
		
	}
}
