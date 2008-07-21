package com.sun.darkstar.example.snowman.common.util;

/**
 * <code>IHPConverter</code> defines the interface of the singleton utility
 * class that is responsible for converting HP values into scale, speed,
 * and attack range values.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-21-2008 17:50 EST
 * @version Modified date: 07-21-2008 17:52 EST
 */
public interface IHPConverter {

	/**
	 * Convert the given HP value into scale value.
	 * @param hp The integer HP value to be converted
	 * @return The float scale converted from the given HP value.
	 */
	public float convertScale(int hp);
	
	/**
	 * Convert the given HP value into speed value.
	 * @param hp The integer HP value to be converted
	 * @return The float speed converted from the given HP value.
	 */
	public float convertSpeed(int hp);
	
	/**
	 * Convert the given HP value into range value.
	 * @param hp The integer HP value to be converted
	 * @return The float range converted from the given HP value.
	 */
	public float convertRange(int hp);
}
