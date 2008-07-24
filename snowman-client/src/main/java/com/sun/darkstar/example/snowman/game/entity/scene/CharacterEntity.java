package com.sun.darkstar.example.snowman.game.entity.scene;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.game.entity.DynamicEntity;

/**
 * <code>CharacterEntity</code> extends <code>DynamicEntity</code> to define
 * the base snowman character in the game.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-24-2008 11:34 EST
 * @version Modified date: 07-24-2008 11:35 EST
 */
public class CharacterEntity extends DynamicEntity {
	/**
	 * The current HP of the snowman.
	 */
	protected int hp;
	/**
	 * The current <code>Vector3f</code> destination.
	 */
	protected Vector3f destination;

	/**
	 * Constructor of <code>CharacterEntity</code>.
	 * @param enumn The <code>EEntity</code> enumeration.
	 * @param id The ID number of this snowman.
	 */
	public CharacterEntity(EEntity enumn, int id) {
		super(enumn, id);
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
	 * Retrieve the current HP value.
	 * @return The integer HP value.
	 */
	public int getHP() {
		return this.hp;
	}
	
	/**
	 * Retrieve the destination of this snowman.
	 * @return The <code>Vector3f</code> destination.
	 */
	public Vector3f getDestination() {
		return this.destination;
	}
}
