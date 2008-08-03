package com.sun.darkstar.example.snowman.game.entity.controller;

import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.input.Controller;
import com.sun.darkstar.example.snowman.game.input.enumn.EInputType;
import com.sun.darkstar.example.snowman.game.physics.util.PhysicsManager;

/**
 * <code>CharacterController</code> extends <code>Controller</code> to define
 * the base type controller that controls a <code>SnowmanEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-24-2008 11:24 EST
 * @version Modified date: 07-28-2008 17:31 EST
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
		if(this.validatePosition()) {
			((CharacterEntity)this.entity).setDestination(null);
			this.entity.resetVelocity();
		} else {
			PhysicsManager.getInstance().markForUpdate(this.entity);
		}
	}
	
	/**
	 * Validate if the current position is within the tolerance range of the destination.
	 * @return True if the character is considered reached the destination. False otherwise.
	 */
	private boolean validatePosition() {
		CharacterEntity character = ((CharacterEntity)this.entity);
		if(character.getDestination() == null) return true;
		try {
			View view = (View)ViewManager.getInstance().getView(character);
			float dx = view.getLocalTranslation().x - character.getDestination().x;
			float dz = view.getLocalTranslation().z - character.getDestination().z;
			if((dx * dx) + (dz * dz) <= this.tolerance) return true;
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}
