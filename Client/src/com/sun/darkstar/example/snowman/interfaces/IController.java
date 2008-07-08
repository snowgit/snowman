package com.sun.darkstar.example.snowman.interfaces;

/**
 * <code>IController</code> defines the interface for all types of entity controller
 * in the system which follows the MVC (Model View Controller) design pattern.
 * <p>
 * <code>IController</code> defines the controlling unit of a dynamic entity which
 * can be controlled by user inputs. It is responsible for mapping user inputs to
 * the entity and view it controls.
 * <p>
 * <code>IController</code> generates corresponding <code>ITask</code> when a user
 * input is received, in order to modify the entity and view it controls.
 * <p>
 * <code>IController</code> is updated by the main game update/render loop every
 * frame to perform potential continuous actions.
 * <p>
 * <code>IController</code> maintains a reference to the <code>IEntity</code> and
 * a reference to the <code>IView</code> it controls.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-27-2008 14:53 EST
 * @version Modified date: 06-04-2008 14:39 EST
 */
public interface IController {

	/**
	 * Update this controller.
	 * @param interpolation The frame rate interpolation value in seconds.
	 */
	public void update(float interpolation);

	/**
	 * Retrieve the entity this controller controls.
	 * @return The <code>IEntity</code> instance.
	 */
	public IEntity getEntity();

	/**
	 * Retrieve the view this controller controls.
	 * @return The <code>IView</code> instance.
	 */
	public IView getView();
}
