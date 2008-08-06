package com.sun.darkstar.example.snowman.game.entity.scene;

import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.entity.enumn.EState;

/**
 * <code>SnowmanEntity</code> extends <code>DynamicEntity</code> to define
 * the logic data store of a snowman character in game.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-14-2008 16:09 EST
 * @version Modified date: 07-28-2008 17:38 EST
 */
public class SnowmanEntity extends CharacterEntity {
	/**
	 * The last movement update time.
	 */
	private long timestamp;

	/**
	 * Constructor of <code>SnowmanEntity</code>.
	 * @param id The ID number of this snowman.
	 */
	public SnowmanEntity(int id) {
		super(EEntity.SnowmanLocal, id);
		this.state = EState.Idle;
	}
	
	/**
	 * Update the maintained time stamp to store the time stamp of movement.
	 */
	public void updateTimeStamp() {
		this.timestamp = System.currentTimeMillis();
	}
	
	/**
	 * Retrieve the time stamp when the last movement was performed.
	 * @return The long time stamp.
	 */
	public long getTimeStamp() {
		return this.timestamp;
	}
}
