package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.physics.enumn.EForce;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.physics.util.PhysicsManager;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>UpdateMovementTask</code> extends <code>RealTimeTask</code> to check
 * if the character has reached its destination.
 * <p>
 * <code>UpdateMovementTask</code> execution logic:
 * 1. Check if the character has a destination. Return if not.
 * 2. Check if the character has reached the area within the tolerance value
 * of the destination.
 * 	  1. If yes, then set the destination to null send out a 'stopme'
 *    message and return.
 *    2. Otherwise add a movement force the direction of the destination
 *    and mark the character for update with <code>PhysicsManager</code>.
 * <p>
 * Two <code>UpdateMovementTask</code> are considered 'equal' if and only
 * if both of them are setting on the same <code>CharacterEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-22-2008 17:01 EST
 * @version Modified date: 07-24-2008 11:50 EST
 */
public class UpdateMovementTask extends RealTimeTask {
	/**
	 * The <code>CharacterEntity</code> instance.
	 */
	private final CharacterEntity character;

	/**
	 * Constructor of <code>CheckStopTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param character The <code>CharacterEntity</code> instance.
	 */
	public UpdateMovementTask(Game game, CharacterEntity character) {
		super(ETask.UpdateMovement, game);
		this.character = character;
	}

	@Override
	public void execute() {
		if(this.character.getDestination() == null) return;
		try {
			View view = (View)ViewManager.getInstance().getView(this.character);
			Vector3f direction = this.character.getDestination().subtract(view.getLocalTranslation()).normalizeLocal();
			direction.y = 0;
			view.getLocalRotation().lookAt(direction, Vector3f.UNIT_Y);
			Vector3f force = direction.multLocal(EForce.Movement.getMagnitude());
			this.character.addForce(force);
			PhysicsManager.getInstance().markForUpdate(this.character);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object object) {
		if(super.equals(object)) {
			if(object instanceof UpdateMovementTask) {
				UpdateMovementTask given = (UpdateMovementTask)object;
				return given.character.equals(this.character);
			}
		}
		return false;
	}
}
