package com.sun.darkstar.example.snowman.game.input;

import com.sun.darkstar.example.snowman.common.interfaces.IEntity;
import com.sun.darkstar.example.snowman.game.input.enumn.EInputType;
import com.sun.darkstar.example.snowman.interfaces.IController;


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
 * @version Modified date: 07-24-2008 11:27 EST
 */
public abstract class Controller implements IController {
	/**
	 * The <code>IEntity</code> this controller controls.
	 */
	protected final IEntity entity;
	/**
	 * The <code>EInputType</code> enumeration.
	 */
	protected final EInputType type;
	/**
	 * The flag indicates if this controller is active.
	 */
	private boolean active;
	
	/**
	 * Constructor of <code>Controller</code>.
	 * @param entity The <code>IEntity</code> this controller controls.
	 * @param type The <code>EInputType</code> enumeration.
	 */
	public Controller(IEntity entity, EInputType type) {
		if(entity == null) throw new IllegalArgumentException("Null entity.");
		this.entity = entity;
		this.type = type;
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
	public EInputType getInputType() {
		return this.type;
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
