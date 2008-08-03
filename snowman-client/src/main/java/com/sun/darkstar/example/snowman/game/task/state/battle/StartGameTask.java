package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.jmex.game.state.GameState;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.stats.StatsManager;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>StartGameTask</code> extends <code>RealTimeTask</code> to start the
 * actual snow ball battle.
 * <p>
 * <code>StartGameTask</code> execution logic:
 * 1. Activate <code>BattleState</code>.
 * 2. Deactivate <code>LoginState</code>.
 * 3. Activate all input.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-23-2008 17:55 EST
 * @version Modified date: 07-23-2008 17:56 EST
 */
public class StartGameTask extends RealTimeTask {

	public StartGameTask(Game game) {
		super(ETask.StartGame, game);
	}

	@Override
	public void execute() {
		GameState state = this.game.getGameState(EGameState.BattleState);
		state.setActive(true);
		state = this.game.getGameState(EGameState.LoginState);
		state.setActive(false);
		InputManager.getInstance().setInputActive(true);
		
		StatsManager.getInstance().resetStats();
	}
}
