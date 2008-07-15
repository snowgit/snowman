package com.sun.darkstar.example.snowman.game.gui.enumn;

import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;

/**
 * <code>EButton</code> defines the enumerations of all buttons that are used
 * in all game scenes.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-09-2008 15:49 EST
 * @version Modified date: 07-09-2008 15:52 EST
 */
public enum EButton {
	/**
	 * The play button in login scene.
	 */
	Play(EGameState.LoginState);
	
	/**
	 * The <code>EGameState</code> enumeration.
	 */
	private final EGameState state;
	
	/**
	 * Constructor of <code>EButton</code>.
	 * @param state The <code>EGameState</code> enumeration.
	 */
	private EButton(EGameState state) {
		this.state = state;
	}
	
	/**
	 * Retrieve the game state this button is used in.
	 * @return The <code>EGameState</code> enumeration.
	 */
	public EGameState getState() {
		return this.state;
	}
}
