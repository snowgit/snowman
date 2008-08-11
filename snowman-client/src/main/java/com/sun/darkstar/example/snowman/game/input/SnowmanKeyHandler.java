package com.sun.darkstar.example.snowman.game.input;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;

/**
 * Quick hack for getting keyboard rotate / zoom in.
 * 
 * @author Joshua Slack
 * 
 */
public class SnowmanKeyHandler extends InputHandler {

	private final SnowmanCameraHandler camHandler;

	public SnowmanKeyHandler(SnowmanCameraHandler camHandler) {
		this.camHandler = camHandler;
	}

	@Override
	public void update(float time) {
		float scale = 2f;
		float zoomScale = 20f;

		if (KeyInput.get().isKeyDown(KeyInput.KEY_LEFT)) {
			camHandler.doRotate(time*scale, 0);
		}
		if (KeyInput.get().isKeyDown(KeyInput.KEY_RIGHT)) {
			camHandler.doRotate(time*-scale, 0);
		}
		if (KeyInput.get().isKeyDown(KeyInput.KEY_UP)) {
			camHandler.doRotate(0,time*scale);
		}
		if (KeyInput.get().isKeyDown(KeyInput.KEY_DOWN)) {
			camHandler.doRotate(0,time*-scale);
		}
		if (KeyInput.get().isKeyDown(KeyInput.KEY_LBRACKET)) {
			camHandler.doZoom(time*zoomScale);
		}
		if (KeyInput.get().isKeyDown(KeyInput.KEY_RBRACKET)) {
			camHandler.doZoom(time*-zoomScale);
		}
	}
}
