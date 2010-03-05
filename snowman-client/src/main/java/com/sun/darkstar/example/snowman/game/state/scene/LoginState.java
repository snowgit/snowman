/*
 *
 * Copyright (c) 2007-2010, Oracle and/or its affiliates.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in
 *       the documentation and/or other materials provided with the
 *       distribution.
 *     * Neither the name of Sun Microsystems, Inc. nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package com.sun.darkstar.example.snowman.game.state.scene;

import com.jme.input.MouseInput;
import com.jme.light.DirectionalLight;
import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Spatial;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.data.util.DataManager;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.gui.scene.LoginGUI;
import com.sun.darkstar.example.snowman.game.state.GameState;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;

/**
 * <code>LoginState</code> extends <code>GameState</code> to define the login
 * scene of the <code>Game</code>. It provides the necessary GUI components
 * for the user to connect to the server.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-03-2008 13:39 EST
 * @version Modified date: 07-17-2008 12:05 EST
 */
public class LoginState extends GameState {
	/**
	 * The <code>LoginGUI</code> instance.
	 */
	private LoginGUI gui;
	private Spatial model;
    private Quaternion rotQuat = new Quaternion();
    private float angle = 0;
    private Vector3f axis;

	/**
	 * Constructor of <code>LoginState</code>.
	 * @param game The <code>Game</code> instance.
	 */
	public LoginState(Game game) {
		super(EGameState.LoginState, game);
	}

	@Override
	protected void initializeWorld() {
		// Init camera
		DisplaySystem.getDisplaySystem().getRenderer().getCamera().setFrame(
				new Vector3f(), new Vector3f(-1, 0, 0), new Vector3f(0, 1, 0),
				new Vector3f(0, 0, -1));
		
		// add our snowglobe
		model = DataManager.getInstance().getStaticSpatial(EEntity.SnowGlobe);
		rootNode.getLocalRotation().fromAngleAxis(15*FastMath.DEG_TO_RAD, new Vector3f(1,0,0));
		rootNode.attachChild(model);
		rootNode.setLocalTranslation(0, 0, -4);
		
		LightState ls = DisplaySystem.getDisplaySystem().getRenderer().createLightState();
		DirectionalLight dl = new DirectionalLight();
		dl.setDirection(new Vector3f(-1,-1,0).normalizeLocal());
		dl.setDiffuse(new ColorRGBA(.75f, .75f, .75f, 1));
		dl.setAmbient(new ColorRGBA(.25f, .25f, .25f, 1));
		dl.setEnabled(true);
		ls.attach(dl);
		rootNode.setRenderState(ls);
		rootNode.updateRenderState();
	}

	@Override
	protected void initializeState() {
		this.buildGUIPass();
	}
	
	/**
	 * Build the GUI render pass.
	 */
	private void buildGUIPass() {
		MouseInput.get().setCursorVisible(true);
		this.gui = new LoginGUI();
		this.gui.initialize();
		this.game.getPassManager().add(this.gui);

		axis = new Vector3f(0.0f, 1.0f, 0.0f);
	}
	
	@Override
	protected void updateState(float interpolation) {
        if (interpolation < 1) {
            angle = angle + (interpolation * 7);
            if (angle > 360) {
                angle = 0;
            }
        }

        rotQuat.fromAngleNormalAxis(angle * FastMath.DEG_TO_RAD, axis);
        model.setLocalRotation(rotQuat);
	}
	
	/**
	 * Retrieve the <code>LoginGUI</code> instance.
	 * @return The <code>LoginGUI</code> instance.
	 */
	public LoginGUI getGUI() {
		return this.gui;
	}
}
