package com.sun.darkstar.example.snowman.game.physics.util;

import java.util.ArrayList;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.unit.Manager;
import com.sun.darkstar.example.snowman.unit.enumn.EManager;

/**
 * <code>PhysicsManager</code> is a <code>Manager</code> that is responsible for
 * performing all the physics calculations for all <code>IDynamicEntity</code>
 * that are subscribed to the manager through <code>markUpdate(...)</code> method.
 * <p>
 * <code>PhysicsManager</code> is updated by the <code>Game</code> every frame.
 * However, the actual physics simulation updates at a fixed rate defined by the
 * <code>PhysicsManager</code>.
 * <p>
 * <code>PhysicsManager</code> maintains a list of <code>IDynamicEntity</code>
 * which need to be updated by the manager. At the end of each physics update cycle,
 * all net-forces of <code>IDynamicEntity</code> in the list is cleared and the
 * list itself is also cleared.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-27-2008 15:22 EST
 * @version Modified date: 07-23-2008 12:20 EST
 */
public class PhysicsManager extends Manager {
	/**
	 * The <code>PhysicsManager</code> instance.
	 */
	private static PhysicsManager instance;
	/**
	 * The <code>ArrayList</code> of <code>IDynamicEntity</code> to be updated in the next iteration.
	 */
	private final ArrayList<IDynamicEntity> entities;
	/**
	 * The temporary velocity <code>Vector3f</code>.
	 */
	private final Vector3f velocity;
	/**
	 * The fixed physics update rate in seconds.
	 */
	private final float rate;
	/**
	 * The time elapsed since last physics iteration.
	 */
	private float time;
	/**
	 * Constructor of <code>PhysicsManager</code>.
	 */
	private PhysicsManager() {
		super(EManager.PhysicsManager);
		this.entities = new ArrayList<IDynamicEntity>();
		this.velocity = new Vector3f();
		this.rate = 0.01f;
	}
	
	/**
	 * Retrieve the singleton <code>PhysicsManager</code> instance.
	 * @return The <code>PhysicsManager</code> instance.
	 */
	public static PhysicsManager getInstance() {
		if(PhysicsManager.instance == null) {
			PhysicsManager.instance = new PhysicsManager();
		}
		return PhysicsManager.instance;
	}
	
	/**
	 * Update the <code>PhysicsManager</code>.
	 * @param interpolation The rendering frame rate interpolation value.
	 */
	public void update(float interpolation) {
		// Return if there is no entities need to be updated.
		if(this.entities.size() <= 0) return;
		// Perform as many iterations as needed.
		this.time += interpolation;
		while(this.time >= this.rate) {
			for(IDynamicEntity entity : this.entities) {
				this.moveEntity(entity);
			}
			this.time -= this.rate;
		}
		// Clear forces.
		for(IDynamicEntity entity : this.entities) {
			entity.resetForce();
		}
		// Clear update list.
		this.entities.clear();
	}
	
	/**
	 * Move the entity based on its force and mass.
	 * @param entity The <code>IDynamicEntity</code> to be moved.
	 */
	private void moveEntity(IDynamicEntity entity) {
		this.velocity.set(entity.getNetForce()).divideLocal(entity.getMass());
		Vector3f delta = this.velocity.multLocal(this.rate);
		try {
			View view = (View)ViewManager.getInstance().getView(entity);
			view.getLocalTranslation().addLocal(delta);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Mark the given dynamic entity for update during the next physics update cycle.
	 * @param entity The <code>IDynamicEntity</code> needs to be updated.
	 * @return True if the entity is marked. False if the entity is already marked before.
	 */
	public boolean markForUpdate(IDynamicEntity entity) {
		if(this.entities.contains(entity)) return false;
		this.entities.add(entity);
		return true;
	}

	/**
	 * Clean up the <code>PhysicsManager</code> by removing all marked entities.
	 */
	@Override
	public void cleanup() {
		this.entities.clear();
	}
}
