package com.sun.darkstar.example.snowman.common.protocol.processor;

import com.sun.darkstar.example.snowman.common.protocol.enumn.EEndState;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;

/**
 * <code>IClientProcessor</code> defines the interface which handles processing
 * messages received by the client from the server.
 * <p>
 * <code>IClientProcessor</code> is directly invoked by <code>ClientProtocol</code>
 * after parsing a received packet.
 * 
 * @author Yi Wang (Neakor)
 * @author Jeffrey Kesselman
 * @version Creation date: 05-29-08 11:23 EST
 * @version Modified date: 06-03-08 10:59 EST
 */
public interface IClientProcessor extends IProtocolProcessor {
	
	/**
	 * Enter lounge state and assign me with given ID.
	 * @param myID The ID number assigned by the server to me.
	 */
	public void enterLounge(int myID); // Certified.

	/**
	 * Create a new battle with given map name and assign me with given ID number.
	 * @param myID The ID number the server assigned me to.
	 * @param mapname The name of the map for the battle.
	 */
	public void newGame(int myID, String mapname);// Certified.

	/**
	 * Start the current battle.
	 */
	public void startGame();// Certified.

	/**
	 * End a battle with given <code>EndState</code>.
	 * @param endState The <cod>EndState</code> of the battle.
	 */
	public void endGame(EEndState endState);// Certified.

	/**
	 * Add a MOB with given ID and <code>MOBType</code> at the given position.
	 * @param objectID The ID number of the newly added MOB.
	 * @param x The x coordinate of the position.
	 * @param y The y coordinate of the position.
	 * @param objType The <code>MOBType</code> of the newly added MOB.
	 */
    public void addMOB(int objectID, float x, float y, EMOBType objType);// Certified.

    /**
	 * Move the MOB with given ID from given starting position towards given ending position.
	 * @param objectID The ID number of the MOB to be moved.
	 * @param startx The x coordinate of the starting position.
	 * @param starty The y coordinate of the starting position.
	 * @param endx The x coordinate of the ending position.
	 * @param endy The y coordinate of the ending position.
	 */
	public void moveMOB(int objectID, float startx, float starty, float endx, float endy);// RealTime.

	/**
	 * Remove the MOB with given ID number.
	 * @param objectID The ID number of the MOB to be removed.
	 */
	public void removeMOB(int objectID);// Certified.

	/**
     * Attach the object with given source ID to the object with given target ID. 
     * @param sourceID The ID number of the object to be moved.
     * @param targetID The ID number of the new parent.
     */
    public void attachObject(int sourceID, int targetID);// Certified.

    /**
     * Attack the target with given target ID with the object with given source ID.
     * @param sourceID The ID number of the attacker.
     * @param targetID The ID number of the target.
     */
    public void attacked(int sourceID, int targetID);// RealTime.

    /**
     * Information of the object with given ID.
     * @param objectID The ID number of the object.
     * @param string The information text of the object.
     */
    public void info(int objectID, String string);// Certified.

    /**
     * Set the HP of the object with given ID number to the given HP value.
     * @param objectID The ID number of the object to be set.
     * @param hp The new HP value.
     */
    public void setHP(int objectID, int hp);// RealTime.
}
