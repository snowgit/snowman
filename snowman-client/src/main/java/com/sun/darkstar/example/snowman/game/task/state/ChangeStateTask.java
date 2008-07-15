package com.sun.darkstar.example.snowman.game.task.state;

import com.jmex.game.state.GameStateManager;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.gui.input.KeyInputConverter;
import com.sun.darkstar.example.snowman.game.gui.input.MouseInputConverter;
import com.sun.darkstar.example.snowman.game.state.GameState;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>ChangeStateTask</code> extends <code>RealTimeTask</code> to change
 * the current active <code>GameState</code>.
 * <p>
 * <code>ChangeStateTask</code> execution logic:
 * 1. Validate the given enumeration is not the current active state.
 * 2. Disable the current active <code>GameState</code>.
 * 3. Clear the <code>BasicPassManager</code>.
 * 4. Disable GUI input converters.
 * 5. Initialize the new active <code>GameState</code>.
 * 6. Activate the new <code>GameState</code>.
 * 7. Set the current active <code>GameState</code> to be the new one.
 * 8. Enable GUI input converters.
 * <p>
 * <code>ChangeStateTask</code> does not have a more detailed 'equals'
 * comparison. All <code>ChangeStateTask</code> are considered 'equal',
 * therefore, a newer version can always replace the older one.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-14-2008 11:42 EST
 * @version Modified date: 07-14-2008 12:16 EST
 */
public class ChangeStateTask extends RealTimeTask {
	/**
	 * The <code>EGameState</code> enumeration of the state to be activated.
	 */
	private final EGameState enumn;

	/**
	 * Constructor of <code>ChangeStateTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param enumn The <code>EGameState</code> enumeration of the state to be activated.
	 */
	public ChangeStateTask(Game game, EGameState enumn) {
		super(ETask.ChangeState, game);
		this.enumn = enumn;
	}

	@Override
	public void execute() {
		if(this.game.getActiveState().getType() == this.enumn) return;
		this.game.getActiveState().setActive(false);
		this.game.getPassManager().clearAll();
		KeyInputConverter.getInstance().setEnabled(false);
		MouseInputConverter.getInstance().setEnabled(false);
		GameState state = ((GameState)GameStateManager.getInstance().getChild(this.enumn.toString()));
		state.initialize();
		state.setActive(true);
		this.game.setActiveState(state);
		KeyInputConverter.getInstance().setEnabled(true);
		MouseInputConverter.getInstance().setEnabled(true);
	}
}
