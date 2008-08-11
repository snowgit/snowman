package com.sun.darkstar.example.snowman.game.input;

import com.jme.input.InputHandler;
import com.jme.input.RelativeMouse;

/**
 * Sets up mouse delegate for the snowman camera system.
 * 
 * @author Joshua Slack
 */
public class SnowmanMouseHandler extends InputHandler {

	private final SnowmmanMouseLook mouseLook;

	public SnowmanMouseHandler(final SnowmanCameraHandler camHandler) {
		RelativeMouse rMouse = new RelativeMouse("Mouse Input");
		rMouse.registerWithInputHandler(this);

		mouseLook = new SnowmmanMouseLook(rMouse, camHandler);
		addAction(mouseLook);
	}
}
