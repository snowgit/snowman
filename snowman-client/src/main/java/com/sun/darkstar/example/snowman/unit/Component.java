package com.sun.darkstar.example.snowman.unit;

import com.sun.darkstar.example.snowman.exception.MissingComponentException;
import com.sun.darkstar.example.snowman.interfaces.IComponent;

/**
 * <code>Component</code> implements <code>IComponent</code> interface to provide
 * the most basic abstraction of a component unit.
 *
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-23-2008 18:52 EST
 * @version Modified date: 06-06-2008 17:23 EST
 */
public abstract class Component implements IComponent {
	/**
	 * The flag indicates the activeness of this <code>Component</code>.
	 */
	private boolean active;

	@Override
	public void activate() throws MissingComponentException {
		if(this.validate()) {
			this.active = true;
			this.initialize();
		}
	}

	@Override
	public void deactivate() {
		this.active = false;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}
}
