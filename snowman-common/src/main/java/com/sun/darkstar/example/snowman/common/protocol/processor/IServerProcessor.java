package com.sun.darkstar.example.snowman.common.protocol.processor;


/**
 * <code>IServerProcessor</code> defines the interface which handles processing
 * messages received by the server from the client.
 * <p>
 * <code>IServerProcessor</code> is directly invoked by <code>ServerProtocol</code>
 * after parsing a received packet.
 * 
 * @author Yi Wang (Neakor)
 * @author Jeffrey Kesselman
 * @version Creation date: 05-29-08 11:44 EST
 * @version Modified date: 06-03-08 10:58 EST
 */
public interface IServerProcessor extends IProtocolProcessor {

	/**
	 * Flag the sending client to be ready for battle.
	 */
	public void ready();

	/**
	 * Match the sending client to a battle.
	 */
	public void matchMe();

	/**
	 * Move the sending client to the given position.
	 * @param x The x coordinate of the destination.
	 * @param y The y coordinate of the destination.
	 */
	public void moveMe(float x, float y);

	/**
	 * Attack the target with given ID number with the sending client.
	 * @param targetID The ID number of the target.
	 */
	public void attack(int targetID);

	/**
     * Attach the flag with given ID number to the sending client.
     * @param flagID The ID number of the flag.
     */
    public void getFlag(int flagID);

    /**
     * Request information about the object with given ID.
     * @param objectID The ID number of the object being requested.
     */
    public void requestInfo(int objectID);
}
