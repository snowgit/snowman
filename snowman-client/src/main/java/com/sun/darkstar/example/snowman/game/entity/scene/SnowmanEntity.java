package com.sun.darkstar.example.snowman.game.entity.scene;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.game.entity.DynamicEntity;
import com.sun.darkstar.example.snowman.game.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.game.entity.enumn.EState;

/**
 * <code>SnowmanEntity</code> extends <code>DynamicEntity</code> to define
 * the logic data store of a snowman character in game.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-14-2008 16:09 EST
 * @version Modified date: 07-21-2008 17:44 EST
 */
public class SnowmanEntity extends DynamicEntity {
	/**
	 * The current HP of the snowman.
	 */
	private int hp;
	/**
	 * The current <code>EState</code>.
	 */
	private EState state;
	/**
	 * The current <code>Vector3f</code> destination.
	 */
	private Vector3f destination;

	/**
	 * Constructor of <code>SnowmanEntity</code>.
	 * @param id The ID number of this snowman.
	 */
	public SnowmanEntity(int id) {
		super(EEntity.Snowman, id);
		this.hp = 100;
	}
	
	/**
	 * Set the HP of this snowman.
	 * @param hp The new HP value to set.
	 */
	public void setHP(int hp) {
		this.hp = hp;
	}
	
	/**
	 * Set the destination of this snowman.
	 * @param destination The <code>Vector3f</code> destination to be set.
	 */
	public void setDestination(Vector3f destination) {
		this.destination = destination;
	}
	
	/**
	 * Set the current state of the snowman.
	 * @param state The <code>EState</code> enumeration.
	 */
	public void setState(EState state) {
		this.state = state;
	}
	
	/**
	 * Retrieve the current HP value.
	 * @return The integer HP value.
	 */
	public int getHP() {
		return this.hp;
	}
	
	/**
	 * Retrieve the current state of the snowman.
	 * @return The <code>EState</code> enumeration.
	 */
	public EState getState() {
		return this.state;
	}
	
	/**
	 * Retrieve the destination of this snowman.
	 * @return The <code>Vector3f</code> destination.
	 */
	public Vector3f getDestination() {
		return this.destination;
	}
}
