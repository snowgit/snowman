package com.sun.darkstar.example.snowman.game.state;

import com.jmex.game.state.BasicGameState;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.interfaces.IGameState;

/**
 * <code>GameState</code> extends <code>BasicGameState</code> and implements
 * <code>IGameState</code> to define the basic abstraction of a particular
 * state of game.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-02-2008 12:29 EST
 * @version Modified date: 07-02-2008 12:29 EST
 */
public abstract class GameState extends BasicGameState implements IGameState {
	/**
	 * The <code>EGameState</code> enumeration.
	 */
	protected final EGameState enumn;
	/**
	 * The <code>Game</code> instance.
	 */
	protected final Game game;
	/**
	 * The flag indicates the activeness of this game state.
	 */
	protected boolean active;
	/**
	 * The flag indicates if this game state is initialized.
	 */
	protected boolean initialized;

	/**
	 * Constructor of <code>GameState</code>.
	 * @param enumn The <code>EGameState</code> enumeration.
	 */
	public GameState(EGameState enumn, Game game) {
		super(enumn.toString());
		this.enumn = enumn;
		this.game = game;
	}

	@Override
	public void initialize() {
		this.initializeState();
		this.initialized = true;
	}
	
	/**
	 * Initialize the actual details of the game state.
	 */
	protected abstract void initializeState();

	@Override
	public void update(float interpolation) {
		if(this.active) this.updateState(interpolation);
	}
	
	/**
	 * Update the actual details of the game state.
	 * @param interpolation The frame rate interpolation value.
	 */
	protected abstract void updateState(float interpolation);

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public EGameState getType() {
		return this.enumn;
	}

	@Override
	public boolean isInitialized() {
		return this.initialized;
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	@Override
	public void cleanup() {
		this.rootNode.detachAllChildren();
	}
}
