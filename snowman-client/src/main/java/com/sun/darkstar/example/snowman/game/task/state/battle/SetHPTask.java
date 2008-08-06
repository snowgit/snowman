package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.sun.darkstar.example.snowman.common.entity.enumn.EState;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>SetHPTask</code> extends <code>RealTimeTask</code> to update
 * the HP value of a character.
 * <p>
 * <code>SetHPTask</code> execution logic:
 * 1. Retrieve the character entity and view based on ID number.
 * 2. Set the character into being hit state.
 * 3. Set the HP value of the character to the new value.
 * 4. Set the mass value of the character based on the new HP value.
 * 5. Set the scale of the view based on the new HP value.
 * 6. Update the geometric state of the view.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 12:15 EST
 * @version Modified date: 08-04-2008 12:23 EST
 */
public class SetHPTask extends RealTimeTask {
	/**
	 * The ID number of the character to be updated.
	 */
	private final int id;
	/**
	 * The new HP value to be set.
	 */
	private final int hp;
	
	/**
	 * Constructor of <code>UpdateHPTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param id The ID number of the character to be updated.
	 * @param hp The new HP value to be set.
	 */
	public SetHPTask(Game game, int id, int hp) {
		super(ETask.SetHP, game);
		this.id = id;
		this.hp = hp;
	}

	@Override
	public void execute() {
		try {
			// Step 1.
			CharacterEntity entity = (CharacterEntity)EntityManager.getInstance().getEntity(this.id);
			// Step 2.
			entity.setState(EState.Hit);
			ViewManager.getInstance().markForUpdate(entity);
			// Step 3.
			View view = (View)ViewManager.getInstance().getView(entity);
			entity.setHP(this.hp);
			// Step 4.
			entity.setMass(SingletonRegistry.getHPConverter().convertMass(this.hp));
			// Step 5.
			view.setLocalScale(SingletonRegistry.getHPConverter().convertScale(this.hp));
			// Step 6.
			view.updateGeometricState(0, false);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
}
