package com.sun.darkstar.example.snowman.game.state.scene.login;

import org.fenggui.Button;
import org.fenggui.Label;
import org.fenggui.TextEditor;
import org.fenggui.util.Alphabet;
import org.fenggui.util.Color;
import org.fenggui.util.fonttoolkit.FontFactory;

import com.sun.darkstar.example.snowman.game.gui.GUIPass;
import com.sun.darkstar.example.snowman.game.gui.enumn.EButton;

/**
 * <code>LoginGUI</code> extends <code>GUIPass</code> to define the user
 * interface for the login scene. It initializes and maintains all the
 * {@link FengGUI} widgets.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-09-2008 15:43 EST
 * @version Modified date: 07-09-2008 16:12 EST
 */
public class LoginGUI extends GUIPass {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = -1020458021673923988L;
	/**
	 * The <code>LoginButtonHandler</code> instance.
	 */
	private final LoginButtonHandler buttonHandler;
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
	
	/**
	 * Constructor of <code>LoginGUI</code>.
	 */
	public LoginGUI() {
		super();
		this.buttonHandler = new LoginButtonHandler(this);
	}
	
	@Override
	public void buildWidgets() {
		this.buildNote();
		this.buildTexts();
		this.buildButton();
	}
	
	/**
	 * Build the status note.
	 */
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
	
	/**
	 * Build the text fields.
	 */
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
	
	/**
	 * Build the button.
	 */
	private void buildButton() {
		Button btnPlay = new Button(EButton.Play.toString());
		btnPlay.setSize(75, 20);
		btnPlay.setXY(this.txtPassword.getX()+this.txtPassword.getWidth()-btnPlay.getWidth(), this.txtPassword.getY()-40);
		btnPlay.addButtonPressedListener(this.buttonHandler);
		this.display.addWidget(btnPlay);
	}
	
	/**
	 * Retrieve the user name text the user entered.
	 * @return The <code>String</code> user name entered.
	 */
	public String getUsername() {
		return this.txtUsername.getText();
	}
	
	/**
	 * Retrieve the password text the user entered.
	 * @return The <code>String</code> password entered.
	 */
	public String getPassword() {
		return this.txtPassword.getText();
	}
}
