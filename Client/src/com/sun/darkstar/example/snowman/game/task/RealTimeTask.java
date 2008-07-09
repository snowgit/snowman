package com.sun.darkstar.example.snowman.game.task;

import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.interfaces.IRealTimeTask;

/**
 * <code>RealTimeTask</code> implements <code>IRealTimeTask</code> to provide
 * the most basic implementation for time dependent tasks. It maintains the
 * construction time stamp for time stamp check purpose.
 * <p>
 * Subclasses of <code>RealTimeTask</code> need to implement the actual
 * execution logic details.
 * <p>
 * <code>RealTimeTask</code> uses the <code>ETask</code> enumeration for the
 * 'equals' comparison. Subclasses need to provide more detailed comparison
 * logic based on the task specific information.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-02-2008 16:48 EST
 * @version Modified date: 07-09-2008 14:59 EST
 */
public abstract class RealTimeTask extends Task implements IRealTimeTask {
	/**
	 * The time stamp of this <code>RealTimeTask</code>
	 */
	protected final long timestamp;

	/**
	 * Constructor of <code>RealTimeTask</code>
	 * @param enumn The <code>ETask</code> enumeration of this </code>Task</code>.
	 * @param game The <code>Game</code> instance.
	 */
	public RealTimeTask(ETask enumn, Game game, int sourceID) {
		super(enumn, game);
		this.timestamp = System.currentTimeMillis();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof RealTimeTask) {
			RealTimeTask task = (RealTimeTask)o;
			return this.enumn == task.enumn;
		}
		return false;
	}
	
	@Override
	public long getTimestamp() {
		return this.timestamp;
	}
	
	@Override
	public boolean isLaterThan(IRealTimeTask task) {
		// Consider equal to be later than.
		return (this.timestamp >= task.getTimestamp());
	}
}
