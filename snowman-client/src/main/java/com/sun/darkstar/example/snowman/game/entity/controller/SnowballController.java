package com.sun.darkstar.example.snowman.game.entity.controller;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowballEntity;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.input.Controller;
import com.sun.darkstar.example.snowman.game.input.enumn.EInputType;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.physics.util.PhysicsManager;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.game.task.util.TaskManager;

/**
 * <code>SnowballController</code> extends <code>Controller</code> to define
 * the controlling unit that is responsible for controlling the movement
 * of a <code>SnowballEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 17:00 EST
 * @version Modified date: 07-29-2008 12:18 EST
 */
public class SnowballController extends Controller {
	/**
	 * The movement tolerance value.
	 */
	protected final float tolerance;
	
	/**
	 * Constructor of <code>SnowballController</code>.
	 * @param entity The <code>SnowballEntity</code> instance.
	 */
	public SnowballController(SnowballEntity entity) {
		super(entity, EInputType.None);
		this.tolerance = 0.5f;
	}

	@Override
	protected void updateLogic(float interpolation) {
		if(((SnowballEntity)this.entity).getVelocity().equals(Vector3f.ZERO)) {
			TaskManager.getInstance().createTask(ETask.Throw, this.entity);
		} else if(!this.validatePosition()) {
			PhysicsManager.getInstance().markForUpdate(this.entity);
		} else {
			EntityManager.getInstance().removeEntity(this.entity.getID());
			System.out.println("ID: " + this.entity.getID() + "Name: " + this.entity.getEnumn().toString());
			ViewManager.getInstance().removeView(this.entity);
			InputManager.getInstance().removeController((IDynamicEntity)this.entity);
		}
	}
	
	/**
	 * Validate if the current position is within the tolerance range of the destination.
	 * @return True if the snow ball is considered reached the destination. False otherwise.
	 */
	private boolean validatePosition() {
		SnowballEntity snowball = ((SnowballEntity)this.entity);
		if(snowball.getDestination() == null) return true;
		try {
			View view = (View)ViewManager.getInstance().getView(snowball);
			float dx = view.getLocalTranslation().x - snowball.getDestination().x;
			float dz = view.getLocalTranslation().z - snowball.getDestination().z;
			if((dx * dx) + (dz * dz) <= this.tolerance) return true;
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}
