package com.sun.darkstar.example.snowman.game.entity.scene;

import com.sun.darkstar.example.snowman.game.entity.DynamicEntity;
import com.sun.darkstar.example.snowman.game.entity.enumn.EEntity;

/**
 * <code>SnowmanEntity</code> extends <code>DynamicEntity</code> to define
 * the logic data store of a snowman character in game.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-14-2008 16:09 EST
 * @version Modified date: 07-17-2008 16:56 EST
 */
public class SnowmanEntity extends DynamicEntity {
	/**
	 * The current HP of the snowman.
	 */
	private int hp;

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
	 * Retrieve the current HP value.
	 * @return The integer HP value.
	 */
	public int getHP() {
		return this.hp;
	}
}
