package com.sun.darkstar.example.snowman.common.protocol;

import java.nio.ByteBuffer;
import java.util.logging.Logger;

import com.sun.darkstar.example.snowman.common.protocol.enumn.EOPCODE;
import com.sun.darkstar.example.snowman.common.protocol.processor.IProtocolProcessor;

/**
 * <code>Protocol</code> defines the base abstraction for all protocols shared by the
 * server and the client.
 * <p>
 * Subclasses of <code>Protocol</code> have to implement the particular parsePacket method
 * in order to be able to parsing received packets.
 * 
 * @author Yi Wang (Neakor)
 * @author Jeffrey Kesselman
 * @version Creation date: 05-29-08 11:09 EST
 * @version Modified date: 06-17-08 10:45 EST
 */
public abstract class Protocol {
    /**
     * The ID number of the Game terrain.
     */
    public static final int TERRAINID = 0;
    /**
     * The <code>Logger</code> instance.
     */
    protected final Logger logger = Logger.getLogger(Protocol.class.toString());
	
    /**
     * Create a chat message packet. It is considered as "chat input" when this is being
     * sent from client to server, but as "chat output" when being sent from server to client.
     * @param text The chat message text
     * @return A <code>ByteBuffer</code> packet in just-written state.
     */
    public ByteBuffer createChatPkt(String text) {
        byte[] bytes = new byte[1+4+text.length()];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        buffer.put((byte)EOPCODE.CHATTEXT.ordinal());
        buffer.putInt(text.length());
        buffer.put(text.getBytes());
        return buffer;
    }
    
	/**
	 * Parse the given <code>ByteBuffer</code> packet then invoke the corresponding
	 * method in given <code>ProtocolProcessor</code>.
	 * @param packet The <code>ByteBuffer</code> packet to be parsed.
	 * @param processor The <code>ProtocolProcessor</code> to be invoked.
	 */
    public void parsePacket(ByteBuffer packet, IProtocolProcessor processor) {
		byte opbyte = packet.get();
		if ((opbyte<0)||(opbyte>EOPCODE.values().length-1)){
			this.logger.severe("Unknown op value: "+opbyte);
			return;
		}
		EOPCODE code = EOPCODE.values()[opbyte];
		// Parse common code.
		switch(code) {
		case CHATTEXT:
			byte[] text = new byte[packet.getInt()];
			packet.get(text);
			processor.chatText(new String(text));
			break;
		// Pass message down to subclasses.
		default:
			this.parsePacket(code, packet, processor);
		break;
		}
    }
    
    /**
     * Parse the given packet with given processor based on given code.
     * @param code The <code>EOPCODE</code> enumeration.
     * @param packet The <code>ByteBuffer</code> packet.
     * @param processor The <code>IProtocolProcessor</code> unit.
     */
    protected abstract void parsePacket(EOPCODE code, ByteBuffer packet, IProtocolProcessor processor);
}
