package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.sun.darkstar.example.snowman.common.entity.enumn.EState;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>RespawnTask</code> extends <code>RealTimeTask</code> to respawn
 * a dead character.
 * <p>
 * <code>RespawnTask</code> execution logic:
 * 1. Retrieve the character entity and view based on ID number.
 * 2. Set the character HP to maximum HP value.
 * 3. Set the character to idle state.
 * 4. Mark the entity as dirty for view update.
 * 5. Teleport the character to given location.
 * 6. If character is controlled locally, activate input.
 * <p>
 * <code>RespawnTask</code> are considered 'equal' if and only if the
 * character that is being respawned is the 'equal'.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 08-07-2008 15:44 EST
 * @version Modified date: 08-07-2008 15:54 EST
 */
public class RespawnTask extends RealTimeTask {
	/**
	 * The ID number of the character to be respawned.
	 */
	private final int id;
	/**
	 * The x coordinate of the respawn location.
	 */
	private final float x;
	/**
	 * The z coordinate of the respawn location.
	 */
	private final float z;
	/**
	 * The flag indicates if the character is controlled locally.
	 */
	private final boolean local;

	/**
	 * Constructor of <code>RespawnTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param id The ID number of the character to be respawned.
	 * @param x The x coordinate of the respawn location.
	 * @param z The z coordinate of the respawn location.
	 * @param local True if the character is controlled locally. False otherwise.
	 */
	public RespawnTask(Game game, int id, float x, float z, boolean local) {
		super(ETask.Respawn, game);
		this.id = id;
		this.x = x;
		this.z = z;
		this.local = local;
	}

	@Override
	public void execute() {
		// Step 1.
		CharacterEntity entity = (CharacterEntity)EntityManager.getInstance().getEntity(this.id);
		View view = (View)ViewManager.getInstance().getView(entity);
		// Step 2.
		entity.setHP(SingletonRegistry.getHPConverter().getMaxHP());
		// Step 3.
		entity.setState(EState.Idle);
		// Step 4.
		ViewManager.getInstance().markForUpdate(entity);
		// Step 5.
		view.getLocalTranslation().x = this.x;
		view.getLocalTranslation().z = this.z;
		// Step 6.
		if(this.local) InputManager.getInstance().setInputActive(true);
	}

	@Override
	public boolean equals(Object object) {
		if(super.equals(object)) {
			if(object instanceof RespawnTask) {
				RespawnTask given = (RespawnTask)object;
				return (this.id == given.id);
			}
		}
		return false;
	}
}
