package com.sun.darkstar.example.snowman.common.protocol;

import java.nio.ByteBuffer;

import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EOPCODE;
import com.sun.darkstar.example.snowman.common.protocol.processor.IClientProcessor;
import com.sun.darkstar.example.snowman.common.protocol.processor.IProtocolProcessor;

/**
 * <code>ClientProtocol</code> is a singleton class responsible for generating packets
 * that are sent from the client to the server.
 * <p>
 * <code>ClientProtocol</code> is also responsible for parsing the packets received
 * from the server and invoke the corresponding methods in <code>ClientProcessor</code>.
 * 
 * @author Yi Wang (Neakor)
 * @author Jeffrey Kesselman
 * @version Creation date: 05-29-08 11:11 EST
 * @version Modified date: 06-17-08 10:37 EST
 */
public class ClientProtocol extends Protocol{
	/**
	 * The <code>ClientProtocol</code> instance.
	 */
	private static ClientProtocol instance;

	/**
	 * Constructor of <code>ClientProtocol</code>.
	 */
	private ClientProtocol(){};

	/**
	 * Retrieve the <code>ClientProtocol</code> instance.
	 * @return The <code>ClientProtocol</code> instance.
	 */
	public static ClientProtocol getInstance() {
		if(ClientProtocol.instance == null) {
			ClientProtocol.instance = new ClientProtocol();
		}
		return ClientProtocol.instance;
	}

	/**
	 * Create a "ready" packet which notifies the server that this client is
	 * ready for the battle.
	 * @return The <code>ByteBuffer</code> "ready" packet.
	 */
	public ByteBuffer createReadyPkt() {
		byte[] bytes = new byte[1];
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.put((byte)EOPCODE.READY.ordinal());
		return buffer;
	}

	/**
	 * Create a "match me" packet which notifies the server to match this client
	 * into a battle.
	 * @return The <code>ByteBuffer</code> "match me" packet.
	 */
	public ByteBuffer createMatchMePkt() {
		byte[] bytes = new byte[1];
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.put((byte)EOPCODE.MATCHME.ordinal());
		return buffer;
	}

	/**
	 * Create a "move me" packet which notifies the server that this client is trying
	 * to move towards the given destination.
	 * @param x The x coordinate of the destination.
	 * @param y The y coordinate of the destination.
	 * @return The <code>ByteBuffer</code> "move me" packet.
	 */
	public ByteBuffer createMoveMePkt(float x, float y) {
		byte[] bytes = new byte[1+8];
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.put((byte)EOPCODE.MOVEME.ordinal());
		buffer.putFloat(x);
		buffer.putFloat(y);
		return buffer;
	}

	/**
	 * Create an "attack" packet which notifies the server that the sending
	 * client is attacking the object with given ID. 
	 * @param targetID The ID number of the target.
	 * @return The <code>ByteBuffer</code> "attack" packet.
	 */
	public ByteBuffer createAttackPkt(int targetID) {
		byte[] bytes = new byte[1+4];
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.put((byte)EOPCODE.ATTACK.ordinal());
		buffer.putInt(targetID);
		return buffer;
	}


	/**
	 * Create a "get flag" packet which notifies the server that this client is
	 * trying to pick up the flag with given ID.
	 * @param flagID The ID number of the flag the client is picking up.
	 * @return The <code>ByteBuffer</code> "get flag" packet.
	 */
	public ByteBuffer createGetFlagPkt(int flagID) {
		byte[] bytes = new byte[1+4];
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.put((byte)EOPCODE.GETFLAG.ordinal());
		buffer.putInt(flagID);
		return buffer;
	}

	/**
	 * Create a "request info" packet which requests the server to send back the
	 * information about the object with given ID.
	 * @param objID The ID number of the object that is being queried for its info.
	 * @return The <code>ByteBuffer</code> "request info" packet.
	 */
	public ByteBuffer createReqInfoPkt(int objID) {
		byte[] bytes = new byte[1+4];
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.put((byte)EOPCODE.REQINFO.ordinal());
		buffer.putInt(objID);
		return buffer;
	}

	@Override
	protected void parsePacket(EOPCODE code, ByteBuffer packet, IProtocolProcessor processor) {
		IClientProcessor unit = (IClientProcessor)processor;
		switch(code){
		case ENTERLOUNGE:
			unit.enterLounge(packet.getInt());
		case NEWGAME:
			int myID = packet.getInt();
			byte[] mapname = new byte[packet.getInt()];
			packet.get(mapname);
			unit.newGame(myID, new String(mapname));
			break;
		case STARTGAME:
			unit.startGame();
			break;
		case ENDGAME:
			unit.endGame(EEndState.values()[packet.getInt()]);
			break;
		case ADDMOB:
			unit.addMOB(packet.getInt(), packet.getFloat(), packet.getFloat(), EMOBType.values()[packet.getInt()]);
			break;
		case MOVEMOB:
			unit.moveMOB(packet.getInt(), packet.getFloat(), packet.getFloat(), packet.getFloat(), packet.getFloat());
			break;
		case REMOVEMOB:
			unit.removeMOB(packet.getInt());
			break;
		case ATTACHOBJ:
			unit.attachObject(packet.getInt(), packet.getInt());
			break;
		case ATTACKED:
			unit.attacked(packet.getInt(), packet.getInt());
			break;
		case INFO:
			int objectID = packet.getInt();
			byte[] info = new byte[packet.getInt()];
			packet.get(info);
			unit.info(objectID, new String(info));
			break;
		case SETHP:
			unit.setHP(packet.getInt(), packet.getInt());
			break;
		default:
			this.logger.info("Unsupported OPCODE: " + code.toString());
		break;
		}
	}
}
