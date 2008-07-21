package com.sun.darkstar.example.snowman.interfaces;

import com.sun.darkstar.example.snowman.game.input.enumn.EInputType;

/**
 * <code>IController</code> defines the interface for all types of entity
 * controller in the system which follows the MVC (Model View Controller)
 * design pattern.
 * <p>
 * <code>IController</code> defines the controlling unit of a dynamic
 * entity which can be controlled by user inputs. It is responsible for
 * mapping user inputs to the entity and view it controls.
 * <p>
 * <code>IController</code> generates corresponding <code>ITask</code> when
 * a user input is received to modify the entity and it controls.
 * <p>
 * <code>IController</code> is updated by the main game update/render loop
 * every frame to perform potential continuous actions. It maintains a
 * reference to the <code>IEntity</code> it controls.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-27-2008 14:53 EST
 * @version Modified date: 07-21-2008 12:01 EST
 */
public interface IController {

	/**
	 * Update this controller.
	 * @param interpolation The frame rate interpolation value in seconds.
	 */
	public void update(float interpolation);
	
	/**
	 * Set the activeness of this controller.
	 * @param active True if this controller should be activated. False otherwise.
	 */
	public void setActive(boolean active);

	/**
	 * Retrieve the input type of this controller.
	 * @return The <Code>EInputType</code> enumeration.
	 */
	public EInputType getInputType();
	
	/**
	 * Retrieve the entity this controller controls.
	 * @return The <code>IEntity</code> instance.
	 */
	public IEntity getEntity();
	
	/**
	 * Check if this controller is active.
	 * @return True if the controller is active. False otherwise.
	 */
	public boolean isActive();
}
