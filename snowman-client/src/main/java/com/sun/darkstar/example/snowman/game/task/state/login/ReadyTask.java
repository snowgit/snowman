package com.sun.darkstar.example.snowman.game.task.state.login;

import com.sun.darkstar.example.snowman.common.protocol.ClientProtocol;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.BattleState;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.game.task.util.TaskManager;

/**
 * <code>ReadyTask</code> extends <code>RealTimeTask</code> to define the
 * task that sends out a ready packet back to the server.
 * <p>
 * <code>ReadyTask</code> execution logic:
 * 1. Check if all entities are added.
 * 	  1. If yes, then send back a ready packet to the server.
 *    2. Otherwise re-submit this task.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-23-2008 17:41 EST
 * @version Modified date: 07-23-2008 17:52 EST
 */
public class ReadyTask extends RealTimeTask {
	
	public ReadyTask(Game game) {
		super(ETask.Ready, game);
	}

	@Override
	public void execute() {
		BattleState state = (BattleState)this.game.getGameState(EGameState.BattleState);
		if(state.getCount() == state.getExpected()) {
			this.game.getClient().send(ClientProtocol.getInstance().createReadyPkt());
			return;
		} else {
			TaskManager.getInstance().submit(this);
		}
	}
}
