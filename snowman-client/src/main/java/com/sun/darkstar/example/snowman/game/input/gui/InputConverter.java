package com.sun.darkstar.example.snowman.game.input.gui;

import org.fenggui.Display;

import com.sun.darkstar.example.snowman.interfaces.IInputConverter;

/**
 * <code>InputConverter</code> implements <code>IInputConverter</code> to
 * define the basic abstraction of an converter that converts {@link jME}
 * input events into {@link FengGUI} events.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-11-2008 16:53 EST
 * @version Modified date: 07-11-2008 16:58 EST
 */
public abstract class InputConverter implements IInputConverter {
	/**
	 * The {@link FengGUI }<code>Display</code> instance.
	 */
	protected Display display;
	/**
	 * The flag indicates if this converter is active.
	 */
	protected boolean active;
	
	/**
	 * Constructor of <code>InputConverter</code>.
	 */
	protected InputConverter() {
		this.active = true;
	}

	@Override
	public void setDisplay(Display display) {
		if(display == null) throw new IllegalArgumentException("Given display instance is null.");
		this.display = display;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}
}
