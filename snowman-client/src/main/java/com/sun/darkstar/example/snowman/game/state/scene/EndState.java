package com.sun.darkstar.example.snowman.game.state.scene;

import com.jme.input.MouseInput;
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
	 * Constructor of <code>LoginState</code>.
	 * @param game The <code>Game</code> instance.
	 */
	public EndState(Game game) {
		super(EGameState.EndState, game);
	}

	@Override
	protected void initializeWorld() {
		//this.world = (World)DataManager.getInstance().getWorld(EWorld.Login);
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
		this.gui = new EndGUI();
		this.gui.initialize();
		this.game.getPassManager().add(this.gui);
	}
	
	@Override
	protected void updateState(float interpolation) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Retrieve the <code>EndGUI</code> instance.
	 * @return The <code>EndGUI</code> instance.
	 */
	public EndGUI getGUI() {
		return this.gui;
	}
}

