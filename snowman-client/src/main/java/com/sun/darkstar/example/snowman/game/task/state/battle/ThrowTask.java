package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowballEntity;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>ThrowTask</code> extends <code>RealTimeTask</code> to update the
 * throw motion of the snow ball.
 * <p>
 * <code>ThrowTask</code> execution logic:
 * 1.
 * 
 * <p>
 * <code>ThrowTask</code> are considered 'equal' if and only if the tasks
 * maintain the same <code>SnowballEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 17:18 EST
 * @version Modified date: MM-DD-2008 HH:mm EST
 */
public class ThrowTask extends RealTimeTask {
	/**
	 * The <code>SnowballEntity</code> that is being thrown.
	 */
	private final SnowballEntity snowball;
	
	/**
	 * Constructor of <code>ThrowTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param snowball The <code>SnowballEntity</code>.
	 */
	public ThrowTask(Game game, SnowballEntity snowball) {
		super(ETask.Throw, game);
		this.snowball = snowball;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object object) {
		if(super.equals(object)) {
			if(object instanceof ThrowTask) {
				ThrowTask given = (ThrowTask)object;
				return given.snowball.equals(this.snowball);
			}
		}
		return false;
	}
}
