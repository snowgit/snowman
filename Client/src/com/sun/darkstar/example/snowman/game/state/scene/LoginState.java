package com.sun.darkstar.example.snowman.game.state.scene;

import com.jme.input.MouseInput;
import com.jme.renderer.pass.RenderPass;
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
 * @version Modified date: 07-10-2008 14:32 EST
 */
public class LoginState extends GameState {
	/**
	 * The <code>LoginGUI</code> instance.
	 */
	private LoginGUI gui;

	/**
	 * Constructor of <code>LoginState</code>.
	 * @param game The <code>Game</code> instance.
	 */
	public LoginState(Game game) {
		super(EGameState.LoginState, game);
	}

	@Override
	protected void initializeState() {
		this.buildRootPass();
		this.buildGUIPass();
	}
	
	/**
	 * Build the root node render pass.
	 */
	private void buildRootPass() {
		RenderPass rootPass = new RenderPass();
		rootPass.add(this.rootNode);
		this.game.getPassManager().add(rootPass);
	}
	
	/**
	 * Build the GUI render pass.
	 */
	private void buildGUIPass() {
		MouseInput.get().setCursorVisible(true);
		this.gui = new LoginGUI();
		this.gui.initialize();
		this.game.getPassManager().add(this.gui);
	}

	@Override
	protected void updateState(float interpolation) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Retrieve the <code>LoginGUI</code> instance.
	 * @return The <code>LoginGUI</code> instance.
	 */
	public LoginGUI getGUI() {
		return this.gui;
	}
}
