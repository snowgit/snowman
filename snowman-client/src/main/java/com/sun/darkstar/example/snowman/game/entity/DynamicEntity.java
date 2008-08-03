package com.sun.darkstar.example.snowman.game.entity;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.Entity;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;

/**
 * <code>DynamicEntity</code> extends <code>Entity</code> and implements
 * <code>IDynamicEntity</code> to represent an actual dynamic entity in
 * the game world.
 * <p>
 * <code>DynamicEntity</code> does not provide a default constructor for
 * binary import and export. This prevents any attempts of exporting
 * <code>DynamicEntity</code> into a binary format.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-13-2008 14:41 EST
 * @version Modified date: 07-28-2008 17:00 EST
 */
public class DynamicEntity extends Entity implements IDynamicEntity {
	/**
	 * The mass value of this <code>DynamicEntity</code>.
	 */
	protected float mass;
	/**
	 * The current <code>Vector3f</code> velocity.
	 */
	protected final Vector3f velocity;
	/**
	 * The force <code>Vector3f</code> currently in effect.
	 */
	protected final Vector3f force;
	
	/**
	 * Constructor of <code>DynamicEntity</code>
	 * @param enumn The <code>EEntity</code> enumeration.
	 * @param id The integer ID number of this entity.
	 */
	public DynamicEntity(EEntity enumn, int id) {
		super(enumn, id);
		this.mass = enumn.getMass();
		this.velocity = new Vector3f();
		this.force = new Vector3f();
	}

	@Override
	public void addForce(Vector3f force) {
		this.force.addLocal(force);
	}

	@Override
	public void resetForce() {
		this.force.zero();
	}

	@Override
	public void setMass(float mass) {
		this.mass = mass;
	}

	@Override
	public void addVelocity(Vector3f velocity) {
		this.velocity.addLocal(velocity);
	}

	@Override
	public void resetVelocity() {
		this.velocity.zero();
	}

	@Override
	public float getMass() {
		return this.mass;
	}

	@Override
	public Vector3f getVelocity() {
		return this.velocity;
	}

	@Override
	public Vector3f getNetForce() {
		return this.force;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class getClassTag() {
		return DynamicEntity.class;
	}
}
