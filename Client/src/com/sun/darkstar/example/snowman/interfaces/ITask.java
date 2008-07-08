package com.sun.darkstar.example.snowman.interfaces;

import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask.ETaskType;

/**
 * <code>ITask</code> defines the interface for all types of tasks which are generated
 * in response to either user inputs received by <code>IController</code> or packets
 * processed by <code>IClientProcessor</code>.
 * <p>
 * <code>ITask</code> is a logic unit which contains execution logic that modifies the
 * state of the <code>Game</code> or the state of an <code>IEntity</code>. It does not
 * contain pre-calculation logic such as finding target or calculating destination.
 * <p>
 * <code>ITask</code> maintains an <code>ETask</code> and an <code>ETaskID</code> which
 * define the base type and logic type of this <code>ITask</code>.
 * <p>
 * <code>ITask</code> is maintained by <code>TaskManager</code> and executed inside the
 * main game update/render loop.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-02-2008 15:15 EST
 * @version Modified date: 07-02-2008 24:22 EST
 */
public interface ITask {
	
	/**
	 * Execute this task.
	 */
	public void execute();
	
	/**
	 * Retrieve the type of this task.
	 * @return The <code>ETaskType</code> of this <code>ITask</code>.
	 */
	public ETaskType getType();
	
	/**
	 * Retrieve the enumeration of this task.
	 * @return The <code>ETask</code> enumeration of this <code>ITask</code>.
	 */
	public ETask getEnumn();
}
