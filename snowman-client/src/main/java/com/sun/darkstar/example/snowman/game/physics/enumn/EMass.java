package com.sun.darkstar.example.snowman.game.physics.enumn;

/**
 * <code>EMass</code> defines the mass values for all entities.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-14-2008 14:43 EST
 * @version Modified date: 07-14-2008 16:01 EST
 */
public enum EMass {
	/**
	 * The terrain mass.
	 */
	Terrain(Float.POSITIVE_INFINITY),
	/**
	 * The snowman mass.
	 */
	Snowman(10);
	
	/**
	 * The mass float value.
	 */
	private float value;
	
	/**
	 * Constructor of <code>EMass</code>.
	 * @param value The mass float value.
	 */
	private EMass(float value) {
		this.value = value;
	}
	
	/**
	 * Retrieve the mass value.
	 * @return The float value.
	 */
	public float getValue() {
		return this.value;
	}
}
