package com.sun.darkstar.example.snowman.interfaces;

import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;

/**
 * <code>IGameState</code> defines the interface for all types of game states
 * that represent a particular stage of the game. It maintains and manages the
 * associated scene graph for the game stage.
 * <p>
 * <code>IGameState</code> is constructed by <code>Game</code> and initialized
 * by <code>ITask</code>. It is updated within the main update render loop every
 * frame. However, it does not provide any direct rendering functionalities.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-29-2008 15:35 EST
 * @version Modified date: 06-29-2008 17:15 EST
 */
public interface IGameState {

	/**
	 * Initialize the game state and its corresponding scene graph.
	 */
	public void initialize();
	
	/**
	 * Retrieve the type of this game state.
	 * @return The <code>EGameState</code> enumeration.
	 */
	public EGameState getType();
	
	/**
	 * Check if this game state has been initialized.
	 * @return True if this game state has been initialized. False otherwise.
	 */
	public boolean isInitialized();
}
