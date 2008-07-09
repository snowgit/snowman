package com.sun.darkstar.example.snowman.game.task;

import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>LoginTask</code> extends <code>RealTimeTask</code> to login the user
 * into the server application.
 * <p>
 * <code>LoginTask</code> execution logic:
 * 1. Updates the status label of <code>LoginGUI</code>.
 * 2. Construct 
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-09-2008 17:02 EST
 * @version Modified date: 07-09-2008 17:02 EST
 */
public class LoginTask extends RealTimeTask {
	

	public LoginTask(Game game) {
		super(ETask.LoginTask, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}
}
