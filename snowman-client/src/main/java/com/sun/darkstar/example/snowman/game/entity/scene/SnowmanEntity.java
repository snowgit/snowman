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
	 * The current <code>EState</code>.
	 */
	private EState state;
	/**
	 * The last movement update time.
	 */
	private long timestamp;
	/**
	 * The current target.
	 */
	private CharacterEntity target;

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
	 * Set the current state of the snowman.
	 * @param state The <code>EState</code> enumeration.
	 */
	public void setState(EState state) {
		this.state = state;
	}
	
	/**
	 * Set the snowman that is being targeted.
	 * @param target The targeted <code>CharacterEntity</code>.
	 */
	public void setTarget(CharacterEntity target) {
		this.target = target;
	}
	
	/**
	 * Retrieve the current state of the snowman.
	 * @return The <code>EState</code> enumeration.
	 */
	public EState getState() {
		return this.state;
	}
	
	/**
	 * Retrieve the snowman that is being targeted.
	 * @return The targeted <code>CharacterEntity</code>.
	 */
	public CharacterEntity getTaregt() {
		return this.target;
	}
	
	/**
	 * Retrieve the time stamp when the last movement was performed.
	 * @return The long time stamp.
	 */
	public long getTimeStamp() {
		return this.timestamp;
	}
}
