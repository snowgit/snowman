package com.sun.darkstar.example.snowman.common.util;

/**
 * <code>IHPConverter</code> defines the interface of the singleton utility
 * class that is responsible for converting HP values into scale, speed,
 * and attack range values.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-21-2008 17:50 EST
 * @version Modified date: 07-25-2008 12:08 EST
 */
public interface IHPConverter {

	/**
	 * Convert the given HP value into scale value.
	 * @param hp The integer HP value to be converted
	 * @return The float scale converted from the given HP value.
	 */
	public float convertScale(int hp);
	
	/**
	 * Convert the given HP value into mass value.
	 * @param hp The integer HP value to be converted
	 * @return The float mass converted from the given HP value.
	 */
	public float convertMass(int hp);
	
	/**
	 * Convert the given HP value into range value.
	 * @param hp The integer HP value to be converted
	 * @return The float range converted from the given HP value.
	 */
	public float convertRange(int hp);
	
	/**
	 * Retrieve the maximum HP value.
	 * @return The integer maximum HP value.
	 */
	public int getMaxHP();
	
	/**
	 * Retrieve the default mass value.
	 * @return The float default mass value.
	 */
	public float getDefaultMass();
	
	/**
	 * Retrieve the default range value.
	 * @return The float default range value.
	 */
	public float getDefaultRange();
}
