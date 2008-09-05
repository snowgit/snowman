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
import com.jme.util.Timer;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.data.util.DataManager;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.state.GameState;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.end.EndGUI;

/**
 * <code>EndState</code> extends <code>GameState</code> to define the end game
 * scene of the <code>Game</code>.
 * 
 * @author Owen Kellett
 */
public class EndState extends GameState {
	/**
	 * The <code>EndGUI</code> instance.
	 */
	private EndGUI gui;
	/**
	 * Seconds to wait before restarting game
	 */
	private final int seconds = 10;
	/**
	 * Timer to keep track of countdown
	 */
	private final Timer timer = Timer.getTimer();
	private int lastTime = 0;

	private Spatial model;
    private Quaternion rotQuat = new Quaternion();
    private float angle = 0;
    private Vector3f axis;

    /**
	 * Constructor of <code>LoginState</code>.
	 * 
	 * @param game
	 *            The <code>Game</code> instance.
	 */
	public EndState(Game game) {
		super(EGameState.EndState, game);
	}

	@Override
	protected void initializeWorld() {
		// Init camera
		DisplaySystem.getDisplaySystem().getRenderer().getCamera().setFrame(
				new Vector3f(), new Vector3f(-1, 0, 0), new Vector3f(0, 1, 0),
				new Vector3f(0, 0, -1));
		
		// add our snowglobe
		model = DataManager.getInstance().getStaticSpatial(EEntity.SnowGlobe);
		rootNode.getLocalRotation().fromAngleAxis(15 * FastMath.DEG_TO_RAD,
				new Vector3f(1, 0, 0));
		rootNode.attachChild(model);
		rootNode.setLocalTranslation(0, 0, -4);

		LightState ls = DisplaySystem.getDisplaySystem().getRenderer()
				.createLightState();
		DirectionalLight dl = new DirectionalLight();
		dl.setDirection(new Vector3f(-1, -1, 0).normalizeLocal());
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
		this.timer.reset();
		this.lastTime = 0;
	}

	/**
	 * Build the GUI render pass.
	 */
	private void buildGUIPass() {
		MouseInput.get().setCursorVisible(true);
		this.gui = new EndGUI(seconds);
		this.gui.initialize();
		this.game.getPassManager().add(this.gui);

		axis = new Vector3f(0.0f, 1.0f, 0.0f);
	}

	@Override
	protected void updateState(float interpolation) {
		int newTime = (int) timer.getTimeInSeconds();
		if (newTime > lastTime) {
			gui.setCountdown(seconds - newTime);
			lastTime = newTime;

			if (seconds - newTime == 0) {
				this.setActive(false);
				this.game.getClient().logout();
			}
		}
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
	 * Retrieve the <code>EndGUI</code> instance.
	 * 
	 * @return The <code>EndGUI</code> instance.
	 */
	public EndGUI getGUI() {
		return this.gui;
	}
}
