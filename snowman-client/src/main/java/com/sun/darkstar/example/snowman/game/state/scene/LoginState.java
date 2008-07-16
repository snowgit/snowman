package com.sun.darkstar.example.snowman.game.state.scene;

import com.jme.input.MouseInput;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;
import com.sun.darkstar.example.snowman.data.util.DataManager;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.state.GameState;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.login.LoginGUI;
import com.sun.darkstar.example.snowman.game.world.World;

/**
 * <code>LoginState</code> extends <code>GameState</code> to define the login
 * scene of the <code>Game</code>. It provides the necessary GUI components
 * for the user to connect to the server.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-03-2008 13:39 EST
 * @version Modified date: 07-14-2008 12:12 EST
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
		this.buildGUIPass();
		this.buildWorld();
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
	
	/**
	 * Build the world.
	 */
	private void buildWorld() {
		//this.rootNode.attachChild((World)DataManager.getInstance().getWorld(EWorld.Login));
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
