package com.sun.darkstar.example.snowman.game.input.util;

import java.util.HashMap;

import com.sun.darkstar.example.snowman.game.input.enumn.EConverter;
import com.sun.darkstar.example.snowman.game.input.gui.KeyInputConverter;
import com.sun.darkstar.example.snowman.game.input.gui.MouseInputConverter;
import com.sun.darkstar.example.snowman.interfaces.IController;
import com.sun.darkstar.example.snowman.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.interfaces.IInputConverter;
import com.sun.darkstar.example.snowman.unit.Manager;
import com.sun.darkstar.example.snowman.unit.enumn.EManager;

/**
 * <code>InputManager</code> is a <code>Manager</code> that is responsible
 * for managing all <code>IInputConverter</code> and <code>IController</code>
 * instances. It is responsible for the creation, retrieval and destruction
 * of all the input handling instances.
 * <p>
 * <code>InputManager</code> is invoked by <code>Game</code> every frame
 * to update all the maintained <code>IController</code> instances.
 * <p>
 * <code>InputManager</code> maintains all <code>IInputConverter</code> as
 * singleton instances. There can only exist a single instance of one type
 * of <code>IInputConverter</code> at any given time. These converters are
 * maintained by their <code>EInputHandler</code> enumerations.
 * <p>
 * <code>InputManager</code> allows multiple instances of the same type of
 * <code>IController</code>. These controllers are maintained by their
 * corresponding <code>IEntity</code>.
 * <p>
 * <code>InputManager</code> provides convenient method for activating or
 * deactivating all input handlers via a single invocation.
 * 
 * @author Yi Wang (Neakor)
 * @author Tim Poliquin (Weenahmen)
 * @version Creation date: 07-15-2008 23:18 EST
 * @version Modified date: 07-17-2008 12:11 EST
 */
public final class InputManager extends Manager {
	/**
	 * The <code>InputManager</code> instance.
	 */
	private static InputManager instance;
	/**
	 * The <code>HashMap</code> of <code>EInputHandler</code> enumeration
	 * and <code>IInputConverter</code> pair singletons.
	 */
	private final HashMap<EConverter, IInputConverter> converters;
	/**
	 * The <code>HashMap</code> of <code>IDynamicEntity</code> key and
	 * <code>IController</code> pairs.
	 */
	private final HashMap<IDynamicEntity, IController> controllers;

	/**
	 * Constructor of <code>InputManager</code>.
	 */
	private InputManager() {
		super(EManager.InputManager);
		this.converters = new HashMap<EConverter, IInputConverter>();
		this.controllers = new HashMap<IDynamicEntity, IController>();
	}
	
	/**
	 * Retrieve the <code>InputManager</code> instance.
	 * @return The <code>InputManager</code> instance.
	 */
	public static InputManager getInstance() {
		if(InputManager.instance == null) {
			InputManager.instance = new InputManager();
		}
		return InputManager.instance;
	}
	
	/**
	 * Update the entity controllers.
	 * @param interpolation The frame rate interpolation value.
	 */
	public void update(float interpolation) {
		for(IController controller : this.controllers.values()) {
			if(controller.isActive()) controller.update(interpolation);
		}
	}
	
	/**
	 * Activate or deactivate all input handlers including all GUI converters
	 * and all entity controllers.
	 * @param active True if input should be activated. False otherwise.
	 */
	public void setInputActive(boolean active) {
		for(IInputConverter converter : this.converters.values()) {
			converter.setActive(active);
		}
		for(IController controller : this.controllers.values()) {
			controller.setActive(active);
		}
	}
	
	/**
	 * Retrieve the input converter with given enumeration.
	 * @param enumn The <code>EConverter</code> enumeration.
	 * @return The <code>IInputConverter</code> with given enumeration.
	 */
	public IInputConverter getConverter(EConverter enumn) {
		IInputConverter converter = this.converters.get(enumn);
		if(converter == null) converter = this.createConverter(enumn);
		return converter;
	}
	
	/**
	 * Retrieve the entity controller with given enumeration.
	 * @param enumn The <code>IDynamicEntity</code> that is being controlled.
	 * @return The <code>IController</code> that controls the given entity.
	 */
	public IController getController(IDynamicEntity entity) {
		IController controller = this.controllers.get(entity);
		if(controller == null) controller = this.createController(entity);
		return controller;
	}
	
	/**
	 * Create an input converter based on given enumeration.
	 * @param enumn The <code>EConverter</code> enumeration.
	 * @return The <code>IInputConverter</code> with given enumeration.
	 */
	private IInputConverter createConverter(EConverter enumn) {
		IInputConverter converter = null;
		switch(enumn) {
		case KeyboardConverter: converter = new KeyInputConverter(); break;
		case MouseConverter: converter = new MouseInputConverter(); break;
		default: throw new IllegalArgumentException("Invalid converter enumeration.");
		}
		this.converters.put(enumn, converter);
		return converter;
	}
	
	/**
	 * Create an entity controller based on given enumeration.
	 * @param entity The <code>IDynamicEntity</code> controller by the created controller.
	 * @return The <code>IController</code> with given enumeration.
	 */
	private IController createController(IDynamicEntity entity) {
		IController controller = null;
		switch(entity.getEnumn()) {
		case Snowman: break; // TODO
		default: throw new IllegalArgumentException("Invalid controller enumeration.");
		}
		this.controllers.put(entity, controller);
		return controller;
	}

	@Override
	public void cleanup() {
		this.converters.clear();
		this.controllers.clear();
	}
}
