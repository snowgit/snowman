package com.sun.darkstar.example.snowman.interfaces;

import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>ITask</code> defines the interface for all types of tasks that are
 * generated in response to either user inputs received by various types of
 * <code>IController</code> or packets processed by <code>IProtocolProcessor</code>.
 * <p>
 * <code>ITask</code> is a logic unit which contains execution logic that
 * modifies the state of the <code>Game</code> or an <code>IEntity</code>.
 * It does not contain any pre-calculation logic such as finding target or
 * calculating destination.
 * <p>
 * <code>ITask</code> maintains an <code>ETask</code> which defines the base
 * type and logic type of this <code>ITask</code>.
 * <p>
 * <code>ITask</code> is created separately but maintained and executed inside
 * the main game update/render loop by <code>TaskManager</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-02-2008 15:15 EST
 * @version Modified date: 07-09-2008 13:50 EST
 */
public interface ITask {
	
	/**
	 * Execute this task.
	 */
	public void execute();
	
	/**
	 * Retrieve the enumeration of this task.
	 * @return The <code>ETask</code> enumeration of this <code>ITask</code>.
	 */
	public ETask getEnumn();
}
