package com.sun.darkstar.example.snowman.game.input.gui;

import org.fenggui.event.mouse.MouseButton;

import com.jme.input.MouseInputListener;
import com.sun.darkstar.example.snowman.game.input.InputConverter;
import com.sun.darkstar.example.snowman.game.input.enumn.EInputConverter;

/**
 * <code>MouseInputConverter</code> extends <code>InputConverter</code> and
 * implements <code>MouseInputListener</code> to define the utility class which
 * converts jME mouse inputs into FengGUI events for the GUI
 * systems to process.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-28-2008 12:20 EST
 * @version Modified date: 07-21-2008 12:11 EST
 */
public class MouseInputConverter extends InputConverter implements MouseInputListener {
	/**
	 * The last pressed mouse button.
	 */
	private int button;
	/**
	 * The boolean flag indicates if the last button was pressed.
	 */
	private boolean pressed;

	/**
	 * Constructor of <code>MouseInputConverter</code>.
	 */
	public MouseInputConverter() {
		super(EInputConverter.MouseConverter);
	}

	/**
	 * Invoked whenever a mouse button is pressed or released.
	 * @param button index of the mouse button that was pressed/released
	 * @param pressed true if button was pressed, false if released
	 * @param x x position of the mouse while button was pressed/released
	 * @param y y position of the mouse while button was pressed/released
	 */
	public void onButton(int button, boolean pressed, int x, int y) {
		if(!this.active || this.display == null) return;
		// Store the button and the pressed flag.
		this.button = button;
		this.pressed = pressed;
		// Map the last pressed LWJGL mouse button to GUI mouse button event.
		MouseButton mouseButton = this.mapGUIMouseEvent(button);
		// If the mouse button was pressed, fire MousePressedEvent.
		if(pressed) {
			this.display.fireMousePressedEvent(x, y, mouseButton, 1);
			// Otherwise fire MouseReleasedEvent.
		} else {
			this.display.fireMouseReleasedEvent(x, y, mouseButton, 1);
		}
	}

	/**
	 * Invoked whenever the mouse is moved.
	 * @param xDelta delta of the x coordinate since the last mouse movement event
	 * @param yDelta delta of the y coordinate since the last mouse movement event
	 * @param newX x position of the mouse after the mouse was moved
	 * @param newY y position of the mouse after the mouse was moved
	 */
	public void onMove(int xDelta, int yDelta, int newX, int newY) {
		if(!this.active || this.display == null) return;
		// If the button is pressed, fire MouseDraggedEvent.
		if(this.pressed) {
			this.display.fireMouseDraggedEvent(newX, newY, mapGUIMouseEvent(this.button));
			// Otherwise fire MouseMovedEvent.
		} else {
			this.display.fireMouseMovedEvent(newX, newY);
		}
	}

	/**
	 * Invoked whenever the mouse wheel is rotated.
	 * @param wheelDelta steps the wheel was rotated
	 * @param x x position of the mouse while wheel was rotated
	 * @param y y position of the mouse while wheel was rotated
	 */
	public void onWheel(int wheelDelta, int x, int y) {
		if(!this.active || this.display == null) return;
		// If the wheelDelta is positive, the mouse wheel is rolling up.
		if(wheelDelta > 0) {
			this.display.fireMouseWheel(x, y, true, wheelDelta);
			// Otherwise the mouse wheel is rolling down.
		} else {
			this.display.fireMouseWheel(x, y, false, wheelDelta);
		}
	}

	/**
	 * Map the last pressed LWJGL mouse button to GUI mouse button event.
	 * @param button The last pressed mouse button.
	 * @return The GUI mouse button event enumeration of the last pressed mouse button.
	 */
	private MouseButton mapGUIMouseEvent(int button) {
		switch(button) {
		case 0:	return MouseButton.LEFT;
		case 1:	return MouseButton.RIGHT;
		case 2:	return MouseButton.MIDDLE;
		default: return MouseButton.LEFT;
		}
	}
}
