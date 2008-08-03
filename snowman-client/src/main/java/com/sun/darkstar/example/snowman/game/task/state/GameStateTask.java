package com.sun.darkstar.example.snowman.game.task.state;

import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.state.GameState;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.LoginState;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>GameStateTask</code> extends <code>RealTimeTask</code> to change
 * the current active <code>GameState</code>.
 * <p>
 * <code>GameStateTask</code> execution logic:
 * 1. Validate the given enumeration is not the current active state.
 * 3. Clear the <code>BasicPassManager</code>.
 * 4. Clear the GUI <code>Display</code>.
 * 5. Deactivate all input.
 * 6. Initialize the new active <code>GameState</code>.
 * <p>
 * <code>GameStateTask</code> does not have a more detailed 'equals'
 * comparison. All <code>GameStateTask</code> are considered 'equal',
 * therefore, a newer version can always replace the older one.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-14-2008 11:42 EST
 * @version Modified date: 07-23-2008 17:37 EST
 */
public class GameStateTask extends RealTimeTask {
	/**
	 * The <code>EGameState</code> enumeration of the state to be activated.
	 */
	private final EGameState enumn;

	/**
	 * Constructor of <code>GameStateTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param enumn The <code>EGameState</code> enumeration of the state to be activated.
	 */
	public GameStateTask(Game game, EGameState enumn) {
		super(ETask.GameState, game);
		this.enumn = enumn;
	}

	@Override
	public void execute() {
		GameState state = this.game.getGameState(this.enumn);
		if(state.isActive()) return;
		this.game.getPassManager().clearAll();
		LoginState login = (LoginState)this.game.getGameState(EGameState.LoginState);
		if(login.isActive()) login.getGUI().getDisplay().removeAllWidgets();
		InputManager.getInstance().setInputActive(false);
		state.initialize();
		state.setActive(false);
	}
}
