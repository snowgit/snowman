package com.sun.darkstar.example.snowman.common.physics.enumn;

/**
 * <code>EForce</code> defines the enumerations of all types of forces that
 * may be applied on <code>IDynamicEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-13-2008 14:50 EST
 * @version Modified date: 07-28-2008 17:05 EST
 */
public enum EForce {
	/**
	 * The gravity force.
	 */
	Gravity(30),
	/**
	 * The movement force.
	 */
	Movement(100);
	
	/**
	 * The float magnitude value of the force.
	 */
	private final float magnitude;
	
	/**
	 * Constructor of <code>EForce</code>.
	 * @param magnitude The float magnitude value of the force.
	 */
	private EForce(float magnitude) {
		this.magnitude = magnitude;
	}
	
	/**
	 * Retrieve the magnitude value of the force.
	 * @return The float magnitude value of the force.
	 */
	public float getMagnitude() {
		return this.magnitude;
	}
}
