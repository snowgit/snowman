package com.sun.darkstar.example.snowman.game.task.state.login;

import java.util.Properties;

import com.jmex.game.state.GameStateManager;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.LoginState;
import com.sun.darkstar.example.snowman.game.state.scene.login.LoginGUI;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>AuthenticateTask</code> extends <code>RealTimeTask</code> to login the
 * user into the server application.
 * <p>
 * <code>AuthenticateTask</code> execution logic:
 * 1. Deactivate all input.
 * 2. Updates the status label of <code>LoginGUI</code>.
 * 3. Change 'Play' button text to 'Please wait...'.
 * 4. Invoke <code>Client</code> to login to the server.
 * 5. Invoke <code>ClientHandler</code> to create <code>PasswordAuthentication</code>.
 * <p>
 * <code>AuthenticateTask</code> does not have a more detailed 'equals'
 * comparison. All <code>AuthenticateTask</code> are considered 'equal',
 * therefore, a newer version can always replace the older one.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-09-2008 17:02 EST
 * @version Modified date: 07-16-2008 11:33 EST
 */
public class AuthenticateTask extends RealTimeTask {
	/**
	 * The status text to display.
	 */
	private final String status;
	/**
	 * The <code>String</code> user name to login with.
	 */
	private final String username;
	/**
	 * The <code>String</code> password to login with.
	 */
	private final String password;

	/**
	 * Constructor of <code>AuthenticateTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param username The <code>String</code> user name to login with.
	 * @param password The <code>String</code> password to login with.
	 */
	public AuthenticateTask(Game game, String username, String password) {
		super(ETask.Authenticate, game);
		this.status = "Waiting for server to match you into a game...";
		this.username = username;
		this.password = password;
	}

	@Override
	public void execute() {
		final LoginGUI gui = ((LoginState)GameStateManager.getInstance().getChild(EGameState.LoginState.toString())).getGUI();
		gui.setStatus(this.status);
		InputManager.getInstance().setInputActive(false);
		gui.setButtonText("Please wait...");
		this.game.getClient().getHandler().authenticate(this.username, this.password);
		Properties properties = new Properties();
		properties.setProperty("host", "localhost");
		properties.setProperty("port", "3000");
		this.game.getClient().login(properties);
	}
}
