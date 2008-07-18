package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowmanEntity;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>SnowmanStateTask</code> extends <code>RealTimeTask</code> to change
 * the state of the given <code>SnowmanEntity</code>.
 * <p>
 * <code>SnowmanStateTask</code> execution logic:
 * 1. 
 * <p>
 * <code>SnowmanStateTask</code> does not have detailed 'equal' comparison.
 * All instances of <code>SnowmanStateTask</code> are considered 'equal'.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-18-2008 11:36 EST
 * @version Modified date: 07-18-2008 11:36 EST
 */
public class SnowmanStateTask extends RealTimeTask {
	/**
	 * The <code>SnowmanEntity</code> instance.
	 */
	private final SnowmanEntity snowman;
	/**
	 * The new x screen coordinate of the mouse.
	 */
	private final int x;
	/**
	 * The new y screen coordinate of the mouse.
	 */
	private final int y;

	/**
	 * Constructor of <code>SnowmanStateTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param snowman The <code>SnowmanEntity</code> to be updated.
	 * @param x The new x screen coordinate of the mouse.
	 * @param y The new y screen coordinate of the mouse.
	 */
	public SnowmanStateTask(Game game, SnowmanEntity snowman, int x, int y) {
		super(ETask.SnowmanState, game);
		this.snowman = snowman;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}
}
