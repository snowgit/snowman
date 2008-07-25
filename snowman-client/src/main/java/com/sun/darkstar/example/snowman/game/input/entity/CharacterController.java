package com.sun.darkstar.example.snowman.game.input.entity;

import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.input.Controller;
import com.sun.darkstar.example.snowman.game.input.enumn.EInputType;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.game.task.util.TaskManager;

/**
 * <code>CharacterController</code> extends <code>Controller</code> to define
 * the base type controller that controls a <code>SnowmanEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-24-2008 11:24 EST
 * @version Modified date: 07-24-2008 11:28 EST
 */
public class CharacterController extends Controller {
	/**
	 * The movement tolerance value.
	 */
	protected final float tolerance;

	/**
	 * Constructor of <code>CharacterController</code>.
	 * @param entity The <code>CharacterEntity</code> instance.
	 * @param type The <code>EInputType</code> enumeration.
	 */
	public CharacterController(CharacterEntity entity, EInputType type) {
		super(entity, type);
		this.tolerance = 0.5f;
	}

	@Override
	protected void updateLogic(float interpolation) {
		if(((CharacterEntity)this.entity).getDestination() == null) return;
		TaskManager.getInstance().createTask(ETask.UpdateMovement, this.entity, this.tolerance);
	}
}
