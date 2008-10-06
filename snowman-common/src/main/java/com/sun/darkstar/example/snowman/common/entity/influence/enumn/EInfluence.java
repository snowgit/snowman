package com.sun.darkstar.example.snowman.common.entity.influence.enumn;

/**
 * <code>EInfluence</code> defines the enumerations of all types of influences
 * that <code>IStaticEntity</code> may have.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-06-2008 11:12 EST
 * @version Modified date: 06-09-2008 12:01 EST
 */
public enum EInfluence {
	/**
	 * <code>Burn</code> influence damages the target, reduces the
	 * size of the target and increases the motion of the target including
	 * both animation speed and movement speed.
	 */
	Burned,
	/**
	 * <code>Slippery</code> influence slows down the target in movement
	 * speed.
	 */
	Slippery
}
