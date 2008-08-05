package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.entity.enumn.EState;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.protocol.messages.ClientMessages;
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
 * 1. Set attacker to attack state.
 * 2. Retrieve the attacker <code>View</code> based on ID number.
 * 3. Retrieve the target <code>View</code> based on ID number.
 * 4. Rotate attacker towards the throwing direction.
 * 5. Create a <code>SnowballEntity</code> with target position.
 * 6. Create a <code>SnowballView</code>.
 * 7. Attach the <code>SnowballView</code> to the world with local translation
 * set to the local translation of the <code>CharacterView</code> plus the offset.
 * 8. Create and activate a <code>SnowballController</code> with the snow ball entity.
 * 9. Send out 'attack' message.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 15:36 EST
 * @version Modified date: 08-04-2008 12:10 EST
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
	 * The flag indicates if this snow ball is created by local controller.
	 */
	private final boolean local;
	/**
	 * The height offset for snow ball.
	 */
	private final float offset;

	/**
	 * Constructor of <code>CreateSnowballTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param attackerID The ID number of the attacker.
	 * @param targetID The ID number of the target.
	 * @param local True if the snow ball is created by local controller.
	 */
	public CreateSnowballTask(Game game, int attackerID, int targetID, boolean local) {
		super(ETask.CreateSnowball, game);
		this.attackerID = attackerID;
		this.targetID = targetID;
		this.offset = 0.6f;
		this.local = local;
	}

	@Override
	public void execute() {
		try {
			// Step 1.
			CharacterEntity entity = (CharacterEntity) EntityManager.getInstance().getEntity(this.attackerID);
			entity.setState(EState.Attacking);
			// Step 2.
			View attacker = (View)ViewManager.getInstance().getView(entity);
			// Step 3.
			entity = (CharacterEntity) EntityManager.getInstance().getEntity(this.targetID);
			View target = (View)ViewManager.getInstance().getView(entity);
			// Step 4.
			Vector3f attackerPosition = attacker.getLocalTranslation().clone();
			Vector3f targetPosition = target.getLocalTranslation().clone();
			attacker.getLocalRotation().lookAt(targetPosition.subtract(attackerPosition).normalizeLocal(), Vector3f.UNIT_Y);
			// Step 5.
			SnowballEntity snowball = (SnowballEntity)EntityManager.getInstance().createEntity(EEntity.Snowball);
			snowball.setDestination(targetPosition);
			// Step 6.
			SnowballView snowballView = (SnowballView)ViewManager.getInstance().createView(snowball);
			// Step 7.
			snowballView.setLocalTranslation(attackerPosition);
			snowballView.getLocalTranslation().y += this.offset;
			this.game.getGameState(EGameState.BattleState).getWorld().attachChild(snowballView);
			// Step 8.
			IController controller = InputManager.getInstance().getController(snowball);
			controller.setActive(true);
			// Step 9.
			if(this.local) this.game.getClient().send(ClientMessages.createAttackPkt(this.targetID, targetPosition.x, targetPosition.z));
			snowballView.updateRenderState();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}
