package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.enumn.EState;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.protocol.messages.ClientMessages;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>AttackingTask</code> extends <code>RealTimeTask</code> to define
 * the task that initiates the attacking process.
 * <p>
 * <code>AttackingTask</code> execution logic:
 * 1. Retrieve the attacker and target entity and view based on IDs.
 * 2. Set attacker to attacking state.
 * 3. Rotate attacker towards the target.
 * 4. Send 'attack' packet to server if attacker is locally controlled.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 08-05-2008 14:39 EST
 * @version Modified date: 08-05-2008 14:48 EST
 */
public class AttackingTask extends RealTimeTask {
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
	 * Constructor of <code>AttackingTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param attackerID The ID number of the attacker.
	 * @param targetID The ID number of the target.
	 * @param local True if the snow ball is created by local controller.
	 */
	public AttackingTask(Game game, int attackerID, int targetID, boolean local) {
		super(ETask.Attacking, game);
		this.attackerID = attackerID;
		this.targetID = targetID;
		this.local = local;
	}

	@Override
	public void execute() {
		try {
			// Step 1.
			CharacterEntity attackerEntity = (CharacterEntity)EntityManager.getInstance().getEntity(this.attackerID);
			View attacker = (View)ViewManager.getInstance().getView(attackerEntity);
			CharacterEntity entity = (CharacterEntity)EntityManager.getInstance().getEntity(this.targetID);
			View target = (View)ViewManager.getInstance().getView(entity);
			// Step 2.
			attackerEntity.setState(EState.Attacking);
			ViewManager.getInstance().markForUpdate(attackerEntity);
			// Step 3.
			Vector3f attackerPosition = attacker.getLocalTranslation().clone();
			Vector3f targetPosition = target.getLocalTranslation().clone();
			attacker.getLocalRotation().lookAt(targetPosition.subtract(attackerPosition).normalizeLocal(), Vector3f.UNIT_Y);
			// Step 4.
			if(this.local) this.game.getClient().send(ClientMessages.createAttackPkt(this.targetID, targetPosition.x, targetPosition.z));
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}
