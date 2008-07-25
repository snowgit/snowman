package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>StopCharacterTask</code> extends <code>RealTimeTask</code> to stop
 * a distributed character.
 * <p>
 * <code>StopCharacterTask</code> execution logic:
 * 1. Get the character entity and its view.
 * 2. Set the character to the given position.
 * 3. Set the current destination of the character to null;
 * 4. Clear all forces on the entity.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-24-2008 16:30 EST
 * @version Modified date: 07-24-2008 16:47 EST
 */
public class StopCharacterTask extends RealTimeTask {
	/**
	 * The ID number of the character entity.
	 */
	private final int id;
	/**
	 * The x coordinate of the stopped position.
	 */
	private float x;
	/**
	 * The z coordinate of the stopped position.
	 */
	private float z;
	
	/**
	 * Constructor of <code>StopCharacterTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param id The ID number of the character entity.
	 * @param x The x coordinate of the stopped position.
	 * @param z The z coordinate of the stopped position.
	 */
	public StopCharacterTask(Game game, int id, float x, float z) {
		super(ETask.StopCharacter, game);
		this.id = id;
		this.x = x;
		this.z = z;
	}

	@Override
	public void execute() {
		try {
			CharacterEntity entity = (CharacterEntity) EntityManager.getInstance().getEntity(this.id);
			View view = (View) ViewManager.getInstance().getView(entity);
			view.getLocalTranslation().x = this.x;
			view.getLocalTranslation().z = this.z;
			entity.setDestination(null);
			entity.resetForce();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
