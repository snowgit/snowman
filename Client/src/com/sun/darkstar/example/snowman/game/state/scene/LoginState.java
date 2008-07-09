package com.sun.darkstar.example.snowman.game.state.scene;

import org.fenggui.Button;
import org.fenggui.Label;
import org.fenggui.TextEditor;
import org.fenggui.util.Alphabet;
import org.fenggui.util.Color;
import org.fenggui.util.fonttoolkit.FontFactory;

import com.jme.input.MouseInput;
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
 * @version Modified date: 07-09-2008 24:06 EST
 */
public class LoginState extends GameState {

	/**
	 * Constructor of <code>LoginState</code>.
	 * @param game The <code>Game</code> instance.
	 */
	public LoginState(Game game) {
		super(EGameState.LoginState, game);
	}

	@Override
	protected void initializeState() {
		MouseInput.get().setCursorVisible(true);
		LoginGUI gui = new LoginGUI();
		gui.initialize();
		this.game.getPassManager().add(gui);
	}

	@Override
	protected void updateState(float interpolation) {
		// TODO Auto-generated method stub

	}
	
	private class LoginGUI extends GUIPass {
		/**
		 * The status note.
		 */
		private Label noteLabel;
		/**
		 * The IP address text editor.
		 */
		private TextEditor txtUsername;
		/**
		 * The name text editor.
		 */
		private TextEditor txtPassword;
		
		@Override
		public void initialize() {
			this.buildNote();
			this.buildTexts();
			this.buildButton();
		}
		
		private void buildNote() {
			this.noteLabel = new Label("Please enter username and password to play.");
			java.awt.Font awtFont = new java.awt.Font("Sans", java.awt.Font.BOLD, 16);
			this.noteLabel.getAppearance().setFont(FontFactory.renderStandardFont(awtFont, true, Alphabet.getDefaultAlphabet()));
			this.noteLabel.setSizeToMinSize();
			this.noteLabel.setHeight(60);
			this.noteLabel.setXY(this.display.getWidth()/2-this.noteLabel.getWidth()/2, this.display.getHeight()/2 + 30);
			this.noteLabel.getAppearance().setTextColor(Color.WHITE);
			this.display.addWidget(this.noteLabel);
		}
		
		private void buildTexts() {
			// IP text.
			Label ip = new Label("Username:         ");
			ip.setSizeToMinSize();
			ip.getAppearance().setTextColor(Color.WHITE);
			ip.setXY(this.display.getWidth()/2 - (75+ip.getWidth()/2), this.display.getHeight()/2 - 10);
			this.display.addWidget(ip);
			this.txtUsername = new TextEditor();
			this.txtUsername.setSize(150, 20);
			this.txtUsername.setXY(ip.getX()+ip.getWidth(), ip.getY());
			this.txtUsername.setMultiline(false);
			this.display.addWidget(this.txtUsername);
			// Name text.
			Label name = new Label("Password:       ");
			name.setSizeToMinSize();
			name.getAppearance().setTextColor(Color.WHITE);
			name.setXY(ip.getX(), ip.getY()-30);
			this.display.addWidget(name);
			this.txtPassword = new TextEditor();
			this.txtPassword.setSize(150, 20);
			this.txtPassword.setXY(this.txtUsername.getX(), name.getY());
			this.txtPassword.setMultiline(false);
			this.display.addWidget(this.txtPassword);
		}
		
		private void buildButton() {
			Button btnPlay = new Button("Play");
			btnPlay.setSize(75, 20);
			btnPlay.setXY(this.txtPassword.getX()+this.txtPassword.getWidth()-btnPlay.getWidth(), this.txtPassword.getY()-40);
			this.display.addWidget(btnPlay);
			//login.addButtonPressedListener(this.game.getLoginHandler());
//			Button local = new Button("Load local IP");
//			local.setSize(75, 20);
//			local.setXY(this.ip.getX()-75, login.getY());
//			local.addButtonPressedListener(this.game.getLoginHandler());
//			this.display.addWidget(local);
		}
	}
}
