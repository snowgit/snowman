package com.sun.darkstar.example.snowman.game.entity.controller;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowballEntity;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.input.Controller;
import com.sun.darkstar.example.snowman.game.input.enumn.EInputType;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.game.task.util.TaskManager;

/**
 * <code>SnowballController</code> extends <code>Controller</code> to define
 * the controlling unit that is responsible for controlling the movement
 * of a <code>SnowballEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 17:00 EST
 * @version Modified date: 07-28-2008 17:44 EST
 */
public class SnowballController extends Controller {
	
	/**
	 * Constructor of <code>SnowballController</code>.
	 * @param entity The <code>SnowballEntity</code> instance.
	 */
	public SnowballController(SnowballEntity entity) {
		super(entity, EInputType.None);
	}

	@Override
	protected void updateLogic(float interpolation) {
		if(((SnowballEntity)this.entity).getDestination() == null) {
			EntityManager.getInstance().removeEntity(this.entity.getID());
			ViewManager.getInstance().removeView(this.entity);
			InputManager.getInstance().removeController((IDynamicEntity)this.entity);
			return;
		} else if(((SnowballEntity)this.entity).getVelocity().equals(Vector3f.ZERO)) {
			TaskManager.getInstance().createTask(ETask.Throw, this.entity);
		}
	}
}
