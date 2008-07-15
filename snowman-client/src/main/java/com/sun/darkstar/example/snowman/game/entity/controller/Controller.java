package com.sun.darkstar.example.snowman.game.entity.controller;

import com.sun.darkstar.example.snowman.interfaces.IController;
import com.sun.darkstar.example.snowman.interfaces.IEntity;


/**
 * <code>Controller</code> implements <code>IController</code> to define the
 * basic abstraction of an entity controller. It is responsible for monitoring
 * and processing user inputs. It generates corresponding <code>ITask</code>
 * in response to input events.
 * <p>
 * <code>Controller</code> is also responsible for monitoring the state of the
 * <code>IEntity</code> it controls to generate corresponding <code>ITask</code>
 * during the update cycle. This allows the <code>Controller</code> to modify
 * the <code>IEntity</code> continuously through several update iterations.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-11-2008 15:35 EST
 * @version Modified date: 07-11-2008 17:02 EST
 */
public abstract class Controller implements IController {
	/**
	 * The <code>IEntity</code> this controller controls.
	 */
	private final IEntity entity;
	/**
	 * The flag indicates if this controller is active.
	 */
	private boolean active;
	
	/**
	 * Constructor of <code>Controller</code>.
	 * @param entity The <code>IEntity</code> this controller controls.
	 */
	public Controller(IEntity entity) {
		if(entity == null) throw new IllegalArgumentException("Null entity.");
		this.entity = entity;
	}
	
	@Override
	public void update(float interpolation) {
		if(this.active) this.updateLogic(interpolation);
	}
	
	/**
	 * Update the detailed logic of this controller.
	 * @param interpolation The frame rate interpolation value in seconds.
	 */
	protected abstract void updateLogic(float interpolation);

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public IEntity getEntity() {
		return this.entity;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}
}
