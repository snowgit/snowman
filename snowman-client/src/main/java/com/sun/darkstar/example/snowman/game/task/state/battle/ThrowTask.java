package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.physics.enumn.EForce;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowballEntity;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.physics.util.PhysicsManager;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>ThrowTask</code> extends <code>RealTimeTask</code> to initiate the
 * throw motion of a snow ball.
 * <p>
 * <code>ThrowTask</code> execution logic:
 * 1. Calculate motion direction based on current position and destination.
 * 2. Rotate previous vector upward 30 degrees.
 * 3. Add a force to the snow ball with calculated direction.
 * 4. Mark the snow ball entity for physics update.
 * <p>
 * <code>ThrowTask</code> are considered 'equal' if and only if the entity
 * of two <code>ThrowTask</code> are 'equal'.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 17:18 EST
 * @version Modified date: 07-29-2008 11:26 EST
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
		try {
			View view = (View)ViewManager.getInstance().getView(this.snowball);
			Vector3f direction = new Vector3f();
			direction.set(this.snowball.getDestination().subtract(view.getLocalTranslation()));
			float length = direction.length();
			float y = length * FastMath.tan(30*FastMath.DEG_TO_RAD);
			direction.y = y;
			direction.normalizeLocal();
			this.snowball.addForce(direction.multLocal(EForce.Throw.getMagnitude()));
			PhysicsManager.getInstance().markForUpdate(this.snowball);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
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
