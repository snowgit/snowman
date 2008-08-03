package com.sun.darkstar.example.snowman.common.entity.enumn;

/**
 * <code>EState</code> defines the enumerations of all the possible states
 * that a <code>SnowmanEntity</code> can be in.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-18-2008 11:07 EST
 * @version Modified date: 07-21-2008 11:52 EST
 */
public enum EState {
	/**
	 * The idle state.
	 */
	Idle,
	/**
	 * The moving state.
	 */
	Moving,
	/**
	 * The targeting other snowman state.
	 */
	Targeting,
	/**
	 * The grabbing flag state.
	 */
	Grabbing
}
