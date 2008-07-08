package com.sun.darkstar.example.snowman.game.task;

import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.interfaces.IEntity;
import com.sun.darkstar.example.snowman.interfaces.IRealTimeTask;

/**
 * <code>RealTimeTask</code> implements <code>IRealTimeTask</code> to provide the
 * most basic implementation for time dependent tasks.
 * <p>
 * <code>RealTimeTask</code> maintains the construction time stamp and a source
 * <code>IEntity</code> that initiated the task action.
 * <p>
 * Subclasses of <code>RealTimeTask</code> need to implement the actual execution
 * logic details.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-02-2008 16:48 EST
 * @version Modified date: 07-02-2008 24:23 EST
 */
public abstract class RealTimeTask extends Task implements IRealTimeTask {
	/**
	 * The time stamp of this <code>RealTimeTask</code>
	 */
	protected final long timestamp;
	/**
	 * The source <code>IEntity</code> instance.
	 */
	protected IEntity source;

	/**
	 * Constructor of <code>RealTimeTask</code>
	 * @param enumn The <code>ETask</code> enumeration of this </code>Task</code>.
	 * @param game The <code>Game</code> instance.
	 * @param sourceID The ID number of the source.
	 */
	public RealTimeTask(ETask enumn, Game game, int sourceID) {
		super(enumn, game);
		this.timestamp = System.currentTimeMillis();
		this.setSource(sourceID);
	}
	
	/**
	 * Set the source entity of this task based on the given source ID number.
	 * @param sourceID The ID number of the source <code>IEntity</code>.
	 */
	private void setSource(int sourceID) {
		try {
			this.source = EntityManager.getInstance().getEntity(sourceID);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof RealTimeTask) {
			RealTimeTask task = (RealTimeTask)o;
			if(task.enumn.equals(this.enumn)) {
				if(task.source == null) {
					return this.source == null;
				} else {
					return task.source.equals(this.source);
				}
			} else {
				return false;
			}
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
