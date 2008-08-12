package com.sun.darkstar.example.snowman.common.util.enumn;

/**
 * <code>EStats</code> defines the enumeration values of all game statistics.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 08-12-2008 11:35 EST
 * @version Modified date: 08-12-2008 11:38 EST
 */
public enum EStats {
	/**
	 * The snow ball damage.
	 */
	SnowballDamage(10f),
        /**
         * The snowman initial hitpoint value
         */
        SnowmanFullStrength(100f),
	/**
	 * The snowman height.
	 */
	SnowmanHeight(1f),
        /**
         * The snowball height
         */
        SnowballHeight(0.8f),
        /**
         * The backoff distance on a collision
         */
        BackoffDistance(0.25f),
	/**
	 * The grabbing range.
	 */
	GrabRange(1f);
	
	/**
	 * The value of this statistic.
	 */
	private final float value;
	
	/**
	 * Constructor of <code>EStats</code>.
	 * @param value The statistic float value.
	 */
	private EStats(float value) {
		this.value = value;
	}
	
	/**
	 * Retrieve the statistic value.
	 * @return The statistic float value.
	 */
	public float getValue() {
		return this.value;
	}
}
