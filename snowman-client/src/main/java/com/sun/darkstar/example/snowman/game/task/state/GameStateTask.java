package com.sun.darkstar.example.snowman.game.task.state;

import com.jmex.game.state.GameStateManager;
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
 * 2. Disable the current active <code>GameState</code>.
 * 3. Clear the <code>BasicPassManager</code>.
 * 4. Clear the GUI <code>Display</code>.
 * 5. Deactivate all input.
 * 6. Initialize the new active <code>GameState</code>.
 * 7. Activate the new <code>GameState</code>.
 * 8. Set the current active <code>GameState</code> to be the new one.
 * 9. Activate all input.
 * <p>
 * <code>GameStateTask</code> does not have a more detailed 'equals'
 * comparison. All <code>GameStateTask</code> are considered 'equal',
 * therefore, a newer version can always replace the older one.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-14-2008 11:42 EST
 * @version Modified date: 07-16-2008 11:33 EST
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
		super(ETask.ChangeState, game);
		this.enumn = enumn;
	}

	@Override
	public void execute() {
		if(this.game.getActiveState().getType() == this.enumn) return;
		this.game.getActiveState().setActive(false);
		this.game.getPassManager().clearAll();
		if(this.game.getActiveState().getType() == EGameState.LoginState) {
			((LoginState)this.game.getActiveState()).getGUI().getDisplay().removeAllWidgets();
		}
		InputManager.getInstance().setInputActive(false);
		GameState state = ((GameState)GameStateManager.getInstance().getChild(this.enumn.toString()));
		state.initialize();
		state.setActive(false);
		this.game.setActiveState(state);
		InputManager.getInstance().setInputActive(true);
	}
}
