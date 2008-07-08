package com.sun.darkstar.example.snowman.game.state.scene;

import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.gui.GUIPass;
import com.sun.darkstar.example.snowman.game.state.GameState;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;

/**
 * <code>LoginState</code> extends <code>GameState</code> to define the login
 * scene of the <code>Game</code>. It provides the necessary GUI components
 * for the user to connect to the server.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-03-2008 13:39 EST
 * @version Modified date: 07-03-2008 13:39 EST
 */
public class LoginState extends GameState {

	/**
	 * Constructor of <code>LoginState</code>.
	 * @param game The <code>Game</code> instance.
	 */
	public LoginState(Game game) {
		super(EGameState.LoginState, game);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initializeState() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateState(float interpolation) {
		// TODO Auto-generated method stub

	}
	
	private class LoginGUI extends GUIPass {

		@Override
		public void initialize() {
			// TODO Auto-generated method stub
			
		}
		
	}
}
