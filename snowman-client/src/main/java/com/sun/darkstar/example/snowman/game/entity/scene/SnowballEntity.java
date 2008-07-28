package com.sun.darkstar.example.snowman.game.entity.scene;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.game.entity.DynamicEntity;

/**
 * <code>SnowballEntity</code> extends <code>DynamicEntity</code> to define
 * a snow ball that is thrown by a character.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 16:40 EST
 * @version Modified date: 07-28-2008 16:50 EST
 */
public class SnowballEntity extends DynamicEntity {
	/**
	 * The <code>Vector3f</code> destination of this snow ball.
	 */
	private Vector3f destination;

	/**
	 * Constructor of <code>SnowballEntity</code>.
	 * @param id The ID number of this entity.
	 */
	public SnowballEntity(int id) {
		super(EEntity.Snowball, id);
	}
	
	/**
	 * Set the destination of this snow ball.
	 * @param destination The <code>Vecto3f</code> destination.
	 */
	public void setDestination(Vector3f destination) {
		this.destination = destination;
	}
	
	/**
	 * Retrieve the destination of this snow ball.
	 * @return The <code>Vector3f</code> destination.
	 */
	public Vector3f getDestination() {
		return this.destination;
	}
}