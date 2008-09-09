package com.sun.darkstar.example.snowman.game.entity.scene;

import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.game.entity.DynamicEntity;

/**
 * <code>SnowballEntity</code> extends <code>DynamicEntity</code> to define
 * the trail of a snow ball that is thrown by a character.
 * 
 * @author Owen Kellett
 */
public class SnowballTrailEntity extends DynamicEntity {

	/**
	 * Constructor of <code>SnowballTrailEntity</code>.
	 * @param id The ID number of this entity.
	 */
	public SnowballTrailEntity(int id) {
		super(EEntity.SnowballTrail, id);
	}
}

