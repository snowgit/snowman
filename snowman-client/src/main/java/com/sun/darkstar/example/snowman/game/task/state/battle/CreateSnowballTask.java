package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.interfaces.IEntity;
import com.sun.darkstar.example.snowman.common.protocol.messages.ClientMessages;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.Game;
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
 * <code>ThrowTask</code> extends <code>Task</code> to define the task that
 * generates a throwing snow ball action.
 * <p>
 * <code>ThrowTask</code> execution logic:
 * 1. Retrieve the attacker <code>View</code> based on ID number.
 * 2. Retrieve the target <code>View</code> based on ID number.
 * 3. Create a <code>SnowballEntity</code> with target position.
 * 4. Create a <code>SnowballView</code>.
 * 5. Attach the <code>SnowballView</code> to the world with local translation
 * set to the local translation of the <code>CharacterView</code> plus the offset.
 * 6. Create a <code>SnowballController</code> with the snow ball entity.
 * 7. Activate the <code>SnowballController</code>.
 * 8. Send out 'attack' message.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 15:36 EST
 * @version Modified date: 07-25-2008 15:36 EST
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
			IEntity entity = EntityManager.getInstance().getEntity(this.attackerID);
			View attacker = (View)ViewManager.getInstance().getView(entity);
			entity = EntityManager.getInstance().getEntity(this.targetID);
			View target = (View)ViewManager.getInstance().getView(entity);
			Vector3f attackerPosition = attacker.getLocalTranslation().clone();
			Vector3f targetPosition = target.getLocalTranslation().clone();
			SnowballEntity snowball = (SnowballEntity)EntityManager.getInstance().createEntity(EEntity.Snowball);
			snowball.setDestination(targetPosition);
			SnowballView snowballView = (SnowballView)ViewManager.getInstance().createView(snowball);
			snowballView.setLocalTranslation(attackerPosition);
			snowballView.getLocalTranslation().y += this.offset;
			this.game.getGameState(EGameState.BattleState).getWorld().attachChild(snowballView);
			IController controller = InputManager.getInstance().getController(snowball);
			controller.setActive(true);
			if(this.local) this.game.getClient().send(ClientMessages.createAttackPkt(this.targetID, targetPosition.x, targetPosition.z));
			snowballView.updateRenderState();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}
