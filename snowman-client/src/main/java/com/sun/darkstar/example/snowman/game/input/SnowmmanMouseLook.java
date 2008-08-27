package com.sun.darkstar.example.snowman.game.input;

import com.jme.input.KeyInput;
import com.jme.input.Mouse;
import com.jme.input.MouseInput;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.MouseInputAction;

/**
 * Handles mouse interaction for the snowman camera system.
 * 
 * @author Joshua Slack
 */
public class SnowmmanMouseLook extends MouseInputAction {

	private float zoomPerClick = 2f;
	private boolean looking;
	private int cursorX, cursorY;
	private final SnowmanCameraHandler camHandler;
	
	public SnowmmanMouseLook(final Mouse aMouse, final SnowmanCameraHandler camHandler) {
		this.mouse = aMouse;
		this.camHandler = camHandler;
	}

	public void performAction(final InputActionEvent evt) {
		float scale = 0.008f;

		int wheelDelta = MouseInput.get().getWheelDelta();
		if (wheelDelta != 0) {
			camHandler.doZoom((wheelDelta > 0 ? zoomPerClick : -zoomPerClick));
		}

		// right
		boolean btn2 = MouseInput.get().isButtonDown(1);

		float mouseX = mouse.getLocalTranslation().x;
		float mouseY = mouse.getLocalTranslation().y;
        
		// Hold and reset cursor position
        if (!looking && btn2) {
            cursorX = MouseInput.get().getXAbsolute();
            cursorY = MouseInput.get().getYAbsolute();
        	MouseInput.get().setCursorVisible(false);
            looking = true;
        } else if (looking && !btn2) {
        	MouseInput.get().setCursorVisible(true);
        	MouseInput.get().setCursorPosition(cursorX, cursorY);
            looking = false;
        }

		if (btn2) {
			if (KeyInput.get().isControlDown()) {
				// Zoom
				if (mouseY != 0) {
					camHandler.doZoom(.25f * (mouseY > 0 ? zoomPerClick : -zoomPerClick));
				}
			} else {
				// rotate camera horizontally/vertically using scale and x/y mouse
				// shift
				if (mouseX != 0 || mouseY != 0) {
					camHandler.doRotate(scale * -mouseX, scale * mouseY);
				}
			}
		}
	}

	/**
	 * @return the _zoomUnitsPerClick
	 */
	public float getZoomUnitsPerClick() {
		return zoomPerClick;
	}

	/**
	 * @param unitsPerClick
	 *            the _zoomUnitsPerClick to set
	 */
	public void setZoomUnitsPerClick(final float unitsPerClick) {
		zoomPerClick = unitsPerClick;
	}
}
