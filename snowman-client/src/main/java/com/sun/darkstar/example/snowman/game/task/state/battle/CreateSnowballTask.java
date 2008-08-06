package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.entity.enumn.EState;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.interfaces.IEntity;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowballEntity;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.scene.SnowballView;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.task.Task;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.interfaces.IController;

/**
 * <code>CreateSnowballTask</code> extends <code>Task</code> to define the task
 * that generates a throwing snow ball action.
 * <p>
 * <code>CreateSnowballTask</code> execution logic:
 * 1. Retrieve attacker and target entity and view based on IDs.
 * 2. Retrieve attacker and target positions.
 * 3. Create snow ball entity and view.
 * 4. Attach snow ball at correct location.
 * 5. Create and activate a snow ball controller.
 * 6. update snow ball render state.
 * 7. Change attacker state to idle and mark for update.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 15:36 EST
 * @version Modified date: 08-05-2008 14:48 EST
 */
public class CreateSnowballTask extends Task {
	/**
	 * The ID number of the attacker.
	 */
	private final int attackerID;
	/**
	 * The ID number of the target.
	 */
	private final int targetID;
	/**
	 * The offset for snow ball.
	 */
	private final Vector3f offset;

	/**
	 * Constructor of <code>CreateSnowballTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param attackerID The ID number of the attacker.
	 * @param targetID The ID number of the target.
	 */
	public CreateSnowballTask(Game game, int attackerID, int targetID) {
		super(ETask.CreateSnowball, game);
		this.attackerID = attackerID;
		this.targetID = targetID;
		this.offset = new Vector3f(0, 0.8f, -0.3f);
	}

	@Override
	public void execute() {
		try {
			// Step 1.
			IEntity attackerEntity = EntityManager.getInstance().getEntity(this.attackerID);
			View attacker = (View)ViewManager.getInstance().getView(attackerEntity);
			IEntity entity = EntityManager.getInstance().getEntity(this.targetID);
			View target = (View)ViewManager.getInstance().getView(entity);
			// Step 2.
			Vector3f attackerPosition = attacker.getLocalTranslation().clone();
			Vector3f targetPosition = target.getLocalTranslation().clone();
			// Step 3.
			SnowballEntity snowball = (SnowballEntity)EntityManager.getInstance().createEntity(EEntity.Snowball);
			snowball.setDestination(targetPosition);
			SnowballView snowballView = (SnowballView)ViewManager.getInstance().createView(snowball);
			// Step 4.
			snowballView.setLocalTranslation(attackerPosition);
			snowballView.getLocalTranslation().addLocal(this.offset);
			this.game.getGameState(EGameState.BattleState).getWorld().attachChild(snowballView);
			// Step 5.
			IController controller = InputManager.getInstance().getController(snowball);
			controller.setActive(true);
			// Step 6.
			snowballView.updateRenderState();
			// Step 7.
			((CharacterEntity)attackerEntity).setState(EState.Idle);
			ViewManager.getInstance().markForUpdate((CharacterEntity)attackerEntity);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}
