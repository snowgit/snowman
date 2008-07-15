package com.sun.darkstar.example.snowman.game.task;

import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.interfaces.ITask;

/**
 * <code>Task</code> defines the most basic abstraction of all types of tasks.
 * Each <code>Task</code> has an unique pair of <code>ETaskType</code> and
 * <code>ETask</code> enumeration defined at construction time.
 * <p>
 * <code>Task</code> also maintains a reference to <code>Game</code> in order
 * to allow subclasses to access game data for logic execution.
 * <p>
 * Subclasses of <code>Task</code> needs to implement execution logic details.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-02-2008 16:47 EST
 * @version Modified date: 07-09-2008 13:51 EST
 */
public abstract class Task implements ITask {
	/**
	 * The <code>ETask</code> enumeration of this <code>Task</code>.
	 */
	protected final ETask enumn;
	/**
	 * The <code>Game</code> instance.
	 */
	protected final Game game;
	
	/**
	 * Constructor of <code>Task</code>.
	 * @param enumn The <code>ETask</code> of this </code>Task</code>.
	 * @param game The <code>Game</code> instance.
	 */
	public Task(ETask enumn, Game game) {
		this.enumn = enumn;
		this.game = game;
	}

	@Override
	public ETask getEnumn() {
		return this.enumn;
	}
}
