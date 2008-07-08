package com.sun.darkstar.example.snowman.game.gui.input;

import org.fenggui.Display;
import org.fenggui.event.mouse.MouseButton;

import com.jme.input.MouseInputListener;

/**
 * <code>MouseInputConverter</code> is a utility class which converts
 * {@link jME} mouse inputs into {@link FengGUI} events for the GUI
 * systems to process.
 * <p>
 * <code>MouseInputConverter</code> needs to be assigned to specific
 * <code>Display</code> at construction time.
 * <p>
 * One instance of <code>MouseInputConverter</code> can only be assigned
 * to a single GUI <code>Display</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-28-2008 12:20 EST
 * @version Modified date: 05-28-2008 13:55 EST
 */
public class MouseInputConverter implements MouseInputListener {
	/**
	 * The <code>Display</code> instance associated with this converter.
	 */
	private final Display display;
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
	 * @param display The <code>Display</code> instance associated with this converter.
	 */
	public MouseInputConverter(Display display) {
		this.display = display;
	}
	
	/**
     * Called in whenever a mouse button is pressed or released.
     * @param button index of the mouse button that was pressed/released
     * @param pressed true if button was pressed, false if released
     * @param x x position of the mouse while button was pressed/released
     * @param y y position of the mouse while button was pressed/released
     */
	public void onButton(int button, boolean pressed, int x, int y) {
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
     * Called in whenever the mouse is moved.
     * @param xDelta delta of the x coordinate since the last mouse movement event
     * @param yDelta delta of the y coordinate since the last mouse movement event
     * @param newX x position of the mouse after the mouse was moved
     * @param newY y position of the mouse after the mouse was moved
     */
	public void onMove(int xDelta, int yDelta, int newX, int newY) {
		// If the button is pressed, fire MouseDraggedEvent.
		if(this.pressed) {
			this.display.fireMouseDraggedEvent(newX, newY, mapGUIMouseEvent(this.button));
		// Otherwise fire MouseMovedEvent.
		} else {
			this.display.fireMouseMovedEvent(newX, newY);
		}
	}

    /**
     * Called in whenever the mouse wheel is rotated.
     * @param wheelDelta steps the wheel was rotated
     * @param x x position of the mouse while wheel was rotated
     * @param y y position of the mouse while wheel was rotated
     */
	public void onWheel(int wheelDelta, int x, int y) {
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
		case 0:
			return MouseButton.LEFT;
		case 1:
			return MouseButton.RIGHT;
		case 2:
			return MouseButton.MIDDLE;
		default:
			return MouseButton.LEFT;
		}
	}
}
