package com.sun.darkstar.example.snowman.game.entity.scene;

import com.sun.darkstar.example.snowman.game.entity.DynamicEntity;
import com.sun.darkstar.example.snowman.game.entity.enumn.EEntity;

/**
 * <code>SnowmanEntity</code> extends <code>DynamicEntity</code> to define
 * the logic data store of a snowman character in game.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-14-2008 16:09 EST
 * @version Modified date: 07-14-2008 16:16 EST
 */
public class SnowmanEntity extends DynamicEntity {
	/**
	 * The current HP of the snowman.
	 */
	private int hp;
	/**
	 * The snow ball count.
	 */
	private int count;

	/**
	 * Constructor of <code>SnowmanEntity</code>.
	 * @param id The ID number of this snowman.
	 */
	public SnowmanEntity(int id) {
		super(EEntity.Snowman, id);
		this.hp = 100;
		this.count = 6;
	}
}
