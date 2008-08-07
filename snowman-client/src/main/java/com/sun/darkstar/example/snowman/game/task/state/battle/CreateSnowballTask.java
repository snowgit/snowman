package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.entity.enumn.EState;
import com.sun.darkstar.example.snowman.common.entity.view.View;
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
 * 1. Retrieve attacker and target view based on entity.
 * 2. Retrieve attacker and target positions.
 * 3. Set target to be in hit state and top the character.
 * 4. Create snow ball entity and view.
 * 5. Attach snow ball at correct location.
 * 6. Create and activate a snow ball controller.
 * 7. update snow ball render state.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 15:36 EST
 * @version Modified date: 08-06-2008 11:30 EST
 */
public class CreateSnowballTask extends Task {
	/**
	 * The ID number of the attacker.
	 */
	private final CharacterEntity attackerEntity;
	/**
	 * The ID number of the target.
	 */
	private final CharacterEntity targetEntity;

	/**
	 * Constructor of <code>CreateSnowballTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param attacker The attacker <code>CharacterEntity</code> instance.
	 * @param target The target <code>CharacterEntity</code> instance.
	 */
	public CreateSnowballTask(Game game, CharacterEntity attacker, CharacterEntity target) {
		super(ETask.CreateSnowball, game);
		this.attackerEntity = attacker;
		this.targetEntity = target;
	}

	@Override
	public void execute() {
		try {
			// Step 1.
			View attacker = (View)ViewManager.getInstance().getView(this.attackerEntity);
			View target = (View)ViewManager.getInstance().getView(this.targetEntity);
			// Step 2.
			Vector3f attackerPosition = attacker.getLocalTranslation().clone();
			Vector3f targetPosition = target.getLocalTranslation().clone();
			// Step 3.
			if(this.targetEntity.isAlive()) {
				this.targetEntity.setState(EState.Hit);
				ViewManager.getInstance().markForUpdate(this.targetEntity);
			}
			this.targetEntity.resetForce();
			this.targetEntity.resetVelocity();
			// Step 4.
			SnowballEntity snowball = (SnowballEntity)EntityManager.getInstance().createEntity(EEntity.Snowball);
			snowball.setDestination(targetPosition);
			SnowballView snowballView = (SnowballView)ViewManager.getInstance().createView(snowball);
			// Step 5.
			snowballView.setLocalTranslation(attackerPosition);
			Vector3f right = new Vector3f();
			attacker.getLocalRotation().getRotationColumn(0, right);
			right.normalizeLocal();
			right.multLocal(((BoundingBox)attacker.getWorldBound()).xExtent*-0.65f);
			snowballView.getLocalTranslation().y += ((BoundingBox)attacker.getWorldBound()).yExtent*0.8f;
			snowballView.getLocalTranslation().addLocal(right);
			this.game.getGameState(EGameState.BattleState).getWorld().attachChild(snowballView);
			// Step 6.
			IController controller = InputManager.getInstance().getController(snowball);
			controller.setActive(true);
			// Step 7.
			snowballView.updateRenderState();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}
