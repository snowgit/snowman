package com.sun.darkstar.example.snowman.interfaces;

import com.jme.math.Vector3f;

/**
 * <code>IDynamicEntity</code> defines the interface for all types of dynamic
 * entity in the game world.
 * <p>
 * <code>IDynamicEntity</code> maintains all the physics information of the
 * dynamic entity that is essential for any physics calculations.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-04-2008 14:54 EST
 * @version Modified date: 06-06-2008 16:10 EST
 */
public interface IDynamicEntity extends IEntity {
	
	/**
	 * Add the given force to this entity.
	 * @param force The force in <code>Vector3f</code> form.
	 */
	public void addForce(Vector3f force);
	
	/**
	 * Retrieve the mass value of this dynamic entity.
	 * @return The mass value of this <code>IDynamicEntity</code>.
	 */
	public float getMass();
	
	/**
	 * Retrieve the net force of this dynamic entity.
	 * @return The net force in <code>Vector3f</code> form.
	 */
	public Vector3f getNetForce();
}
