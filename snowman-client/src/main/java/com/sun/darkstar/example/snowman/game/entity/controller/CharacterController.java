package com.sun.darkstar.example.snowman.game.entity.controller;

import com.model.md5.JointAnimation;
import com.model.md5.controller.JointController;
import com.sun.darkstar.example.snowman.common.entity.enumn.EState;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.data.enumn.EAnimation;
import com.sun.darkstar.example.snowman.data.util.DataManager;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.view.scene.CharacterView;
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
 * @version Modified date: 08-04-2008 12:34 EST
 */
public class CharacterController extends Controller {
	/**
	 * The movement tolerance value.
	 */
	protected final float tolerance;
	/**
	 * The <code>JointController</code> for controlling the character.
	 */
	protected JointController jointController;
	/**
	 * The idle animation.
	 */
	private final JointAnimation animIdle;
	/**
	 * The movement animation.
	 */
	private final JointAnimation animMove;
	/**
	 * The attack animation.
	 */
	private final JointAnimation animAttack;
	/**
	 * The hit animation.
	 */
	private final JointAnimation animHit;

	/**
	 * Constructor of <code>CharacterController</code>.
	 * @param entity The <code>CharacterEntity</code> instance.
	 * @param type The <code>EInputType</code> enumeration.
	 */
	public CharacterController(CharacterEntity entity, EInputType type) {
		super(entity, type);
		this.tolerance = 0.2f;
		try {
			CharacterView view = (CharacterView) ViewManager.getInstance().getView(this.entity);
			this.jointController = new JointController(view.getMesh().getJoints());
			this.jointController.setActive(true);
			view.getMesh().addController(this.jointController);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		this.animIdle = DataManager.getInstance().getAnimation(EAnimation.Idle);
		this.animMove = DataManager.getInstance().getAnimation(EAnimation.Move);
		this.animAttack = DataManager.getInstance().getAnimation(EAnimation.Attack);
		this.animHit = DataManager.getInstance().getAnimation(EAnimation.Hit);
		this.jointController.setFading(this.animIdle, 0, false);
		this.jointController.setRepeatType(com.jme.scene.Controller.RT_WRAP);
	}

	@Override
	protected void updateLogic(float interpolation) {
		switch(((CharacterEntity)this.entity).getState()) {
		case Attacking:
			if(this.jointController.getActiveAnimation() != this.animAttack) {
				((CharacterEntity)this.entity).setDestination(null);
				this.entity.resetVelocity();
				this.jointController.setFading(this.animAttack, 0, false);
				this.jointController.setRepeatType(com.jme.scene.Controller.RT_CLAMP);
			} else if(this.animAttack.isCyleComplete()) {
				this.jointController.setFading(this.animIdle, 0, false);
				this.jointController.setRepeatType(com.jme.scene.Controller.RT_WRAP);
				((CharacterEntity)this.entity).setState(EState.Idle);
			}
			break;
		case Hit:
			if(this.jointController.getActiveAnimation() != this.animHit) {
				((CharacterEntity)this.entity).setDestination(null);
				this.entity.resetVelocity();
				this.jointController.setFading(this.animHit, 0, false);
				this.jointController.setRepeatType(com.jme.scene.Controller.RT_CLAMP);
			} else if(this.animHit.isCyleComplete()) {
				this.jointController.setFading(this.animIdle, 0, false);
				this.jointController.setRepeatType(com.jme.scene.Controller.RT_WRAP);
				((CharacterEntity)this.entity).setState(EState.Idle);
			}
			break;
		case Grabbing:
			break;
		default:
			if(this.validatePosition() && this.jointController.getActiveAnimation() != this.animIdle) {
				((CharacterEntity)this.entity).setDestination(null);
				this.entity.resetVelocity();
				this.jointController.setFading(this.animIdle, 0, false);
				this.jointController.setRepeatType(com.jme.scene.Controller.RT_WRAP);
			} else if(((CharacterEntity)this.entity).getDestination() != null) {
				PhysicsManager.getInstance().markForUpdate(this.entity);
				if(this.jointController.getActiveAnimation() != this.animMove) {
					this.jointController.setFading(this.animMove, 0, false);
					this.jointController.setRepeatType(com.jme.scene.Controller.RT_WRAP);
				}
			}
			break;
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
