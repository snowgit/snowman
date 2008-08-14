package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity.EEntityType;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.common.interfaces.IEntity;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>RemoveMOBTask</code> extends <code>RealTimeTask</code> to define the
 * task that removes an entity.
 * <p>
 * <code>RemoveMOBTask</code> execution logic:
 * 1. Retrieve the entity with given ID number.
 * 2. Remove the view of the entity.
 * 3. Remove the controller of the entity.
 * 4. Remove the entity itself.
 * <p>
 * <code>RemoveMOBTask</code> are considered 'equal' if and only if the ID
 * number is the same.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 08-14-2008 16:24 EST
 * @version Modified date: 08-14-2008 16:29 EST
 */
public class RemoveMOBTask extends RealTimeTask {
	/**
	 * The ID number of the entity to be removed.
	 */
	private final int id;

	/**
	 * Constructor of <code>RemoveMOBTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param id The ID number of the entity to be removed.
	 */
	public RemoveMOBTask(Game game, int id) {
		super(ETask.Remove, game);
		this.id = id;
	}

	@Override
	public void execute() {
		// Step 1.
		IEntity entity = EntityManager.getInstance().getEntity(this.id);
		// Step 2.
		ViewManager.getInstance().removeView(entity);
		// Step 3.
		if(entity.getType() == EEntityType.Dynamic) {
			InputManager.getInstance().removeController((IDynamicEntity)entity);
		}
		// Step 4.
		EntityManager.getInstance().removeEntity(this.id);
	}

	@Override
	public boolean equals(Object object) {
		if(super.equals(object)) {
			if(object instanceof RemoveMOBTask) {
				RemoveMOBTask given = (RemoveMOBTask)object;
				return (given.id == this.id);
			}
		}
		return false;
	}
}
