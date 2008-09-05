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
import com.sun.darkstar.example.snowman.game.state.GameState;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.login.LoginGUI;

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
