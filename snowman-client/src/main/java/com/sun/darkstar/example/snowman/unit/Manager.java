package com.sun.darkstar.example.snowman.unit;

import java.util.logging.Logger;

import com.sun.darkstar.example.snowman.unit.enumn.EManager;

/**
 * <code>Manager</code> defines the most basic abstraction for all types of
 * managing systems in the game. All managing systems should follow the
 * singleton design pattern.
 * <p>
 * <code>Manager</code> maintains an unique <code>EManager</code> that defines
 * the type of this managing system and a <code>Logger</code> instance used for
 * logging any useful information.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-09-2008 12:15 EST
 * @version Modified date: 06-29-2008 17:55 EST
 */
public abstract class Manager {
	/**
	 * The <code>EManager</code> enumeration.
	 */
	private final EManager type;
	/**
	 * The <code>Logger</code> instance.
	 */
	protected final Logger logger;
	
	/**
	 * Constructor of <code>Manager</code>
	 * @param type The <code>EManager</code> enumeration.
	 */
	protected Manager(EManager type) {
		this.type = type;
		this.logger = Logger.getLogger(this.type.toString());
	}
	
	/**
	 * Clean up the <code>Manager</code>.
	 */
	public abstract void cleanup();
}
