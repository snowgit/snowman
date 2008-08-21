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
	 * The maximum height of the snow ball.
	 */
	private final float maxHeight;
	/**
	 * The <code>Vector3f</code> destination of this snow ball.
	 */
	private Vector3f destination;
        /**
	 * The target <code>CharacterEntity</code> instance.
	 */
	protected CharacterEntity target;

	/**
	 * Constructor of <code>SnowballEntity</code>.
	 * @param id The ID number of this entity.
	 */
	public SnowballEntity(int id) {
		super(EEntity.Snowball, id);
		this.maxHeight = 1.2f;
	}
	
	/**
	 * Set the destination of this snow ball.
	 * @param destination The <code>Vecto3f</code> destination.
	 */
	public void setDestination(Vector3f destination) {
		this.destination = destination;
	}
        
        /**
	 * Set the current target of this character.
	 * @param target The target <code>CharacterEntity</code> instance.
	 */
	public void setTarget(CharacterEntity target) {
		this.target = target;
	}
	
	/**
	 * Retrieve the maximum height the snow ball will reach.
	 * @return The float maximum height value.
	 */
	public float getMaxHeight() {
		return this.maxHeight;
	}
	
	/**
	 * Retrieve the destination of this snow ball.
	 * @return The <code>Vector3f</code> destination.
	 */
	public Vector3f getDestination() {
		return this.destination;
	}
        
        /**
	 * Retrieve the target <code>CharacterEntity</code> instance.
	 * @return The target <code>CharacterEntity</code> instance.
	 */
	public CharacterEntity getTarget() {
		return this.target;
	}
}
