package com.sun.darkstar.example.snowman.game.entity.view.scene;

import com.jme.scene.Spatial;
import com.model.md5.JointAnimation;
import com.model.md5.ModelNode;
import com.model.md5.controller.JointController;
import com.sun.darkstar.example.snowman.data.enumn.EAnimation;
import com.sun.darkstar.example.snowman.data.util.DataManager;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;

/**
 * <code>CharacterView</code> extends <code>DynamicView</code> to represent
 * a dynamic animated view of <code>CharacterEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-21-2008 14:54 EST
 * @version Modified date: 07-24-2008 11:45 EST
 */
public class CharacterView extends DynamicView {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1082825580187469809L;
	/**
	 * The <code>ModelNode</code> of this snowman view.
	 */
	private ModelNode model;
	/**
	 * The <code>JointController</code> for controlling the character.
	 */
	private JointController jointController;
	/**
	 * The idle animation.
	 */
	private JointAnimation animIdle;
	/**
	 * The movement animation.
	 */
	private JointAnimation animMove;
	/**
	 * The attack animation.
	 */
	private JointAnimation animAttack;
	/**
	 * The hit animation.
	 */
	private JointAnimation animHit;

	/**
	 * Constructor of <code>CharacterView</code>.
	 * @param snowman The <code>CharacterEntity</code> instance.
	 */
	public CharacterView(CharacterEntity snowman) {
		super(snowman);
	}

	@Override
	public void attachMesh(Spatial mesh) {
		if(!(mesh instanceof ModelNode)) throw new IllegalArgumentException("Mesh is not a dynamic ModelNode.");
		super.attachMesh(mesh);
		this.model = (ModelNode)mesh;
		this.jointController = new JointController(this.model.getJoints());
		this.jointController.setActive(true);
		this.model.addController(this.jointController);
		this.animIdle = DataManager.getInstance().getAnimation(EAnimation.Idle);
		this.animMove = DataManager.getInstance().getAnimation(EAnimation.Move);
		this.animAttack = DataManager.getInstance().getAnimation(EAnimation.Attack);
		this.animHit = DataManager.getInstance().getAnimation(EAnimation.Hit);
		this.jointController.setFading(this.animIdle, 0, false);
		this.jointController.setRepeatType(com.jme.scene.Controller.RT_WRAP);
	}

	@Override
	public void update(float interpolation) {
		switch(((CharacterEntity)this.entity).getState()) {
		case Moving:
			this.jointController.setFading(this.animMove, 0, false);
			this.jointController.setRepeatType(com.jme.scene.Controller.RT_WRAP);
		case Idle:
			this.jointController.setFading(this.animIdle, 0, false);
			this.jointController.setRepeatType(com.jme.scene.Controller.RT_WRAP);
			break;
		case Attacking:
			this.jointController.setFading(this.animAttack, 0, false);
			this.jointController.setRepeatType(com.jme.scene.Controller.RT_CLAMP);
			break;
		case Hit:
			this.jointController.setFading(this.animHit, 0, false);
			this.jointController.setRepeatType(com.jme.scene.Controller.RT_CLAMP);
			break;
		}
	}

	/**
	 * Retrieve the dynamic mesh.
	 * @return The <code>ModelNode</code> instance.
	 */
	public ModelNode getMesh() {
		return this.model;
	}

	/**
	 * Check if the current active animation is complete.
	 * @return True if the current active animation is complete. False otherwise.
	 */
	public boolean isCurrentComplete() {
		return this.jointController.getActiveAnimation().isCyleComplete();
	}
}
