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
 * @version Modified date: 07-10-2008 18:24 EST
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
	 * The default status text.
	 */
	private final String defaultStatus;
	/**
	 * The status note.
	 */
	private Label labelStatus;
	/**
	 * The user name label.
	 */
	private Label labelUsername;
	/**
	 * The password label.
	 */
	private Label labelPassword;
	/**
	 * The IP address text editor.
	 */
	private TextEditor textUsername;
	/**
	 * The name text editor.
	 */
	private TextEditor textPassword;
	/**
	 * The play <code>Button</code>
	 */
	private Button buttonPlay;
	/**
	 * The failed label.
	 */
	private Label labelFailed;
	
	/**
	 * Constructor of <code>LoginGUI</code>.
	 */
	public LoginGUI() {
		super();
		this.buttonHandler = new LoginButtonHandler(this);
		this.defaultStatus = "Please enter username and password to play";
	}
	
	@Override
	public void buildWidgets() {
		this.buildStatus();
		this.buildTexts();
		this.buildButton();
		this.buildFailedLabel();
	}
	
	/**
	 * Build the status note.
	 */
	private void buildStatus() {
		this.labelStatus = new Label(this.defaultStatus);
		java.awt.Font awtFont = new java.awt.Font("Sans", java.awt.Font.BOLD, 16);
		this.labelStatus.getAppearance().setFont(FontFactory.renderStandardFont(awtFont, true, Alphabet.getDefaultAlphabet()));
		this.labelStatus.setSizeToMinSize();
		this.labelStatus.setHeight(60);
		this.labelStatus.setXY(this.display.getWidth()/2-this.labelStatus.getWidth()/2, this.display.getHeight()/4);
		this.labelStatus.getAppearance().setTextColor(Color.WHITE);
		this.display.addWidget(this.labelStatus);
	}
	
	/**
	 * Build the text fields.
	 */
	private void buildTexts() {
		// IP text.
		this.labelUsername = new Label("Username:         ");
		this.labelUsername.setSizeToMinSize();
		this.labelUsername.getAppearance().setTextColor(Color.WHITE);
		this.labelUsername.setXY(this.display.getWidth()/2 - (75+this.labelUsername.getWidth()/2), this.display.getHeight()/5);
		this.display.addWidget(this.labelUsername);
		this.textUsername = new TextEditor();
		this.textUsername.setSize(150, 20);
		this.textUsername.setXY(this.labelUsername.getX()+this.labelUsername.getWidth(), this.labelUsername.getY());
		this.textUsername.setMultiline(false);
		this.display.addWidget(this.textUsername);
		// Name text.
		this.labelPassword = new Label("Password:       ");
		this.labelPassword.setSizeToMinSize();
		this.labelPassword.getAppearance().setTextColor(Color.WHITE);
		this.labelPassword.setXY(this.labelUsername.getX(), this.labelUsername.getY()-30);
		this.display.addWidget(this.labelPassword);
		this.textPassword = new TextEditor();
		this.textPassword.setSize(150, 20);
		this.textPassword.setXY(this.textUsername.getX(), this.labelPassword.getY());
		this.textPassword.setMultiline(false);
		this.display.addWidget(this.textPassword);
	}
	
	/**
	 * Build the button.
	 */
	private void buildButton() {
		this.buttonPlay = new Button(EButton.Play.toString());
		this.buttonPlay.setSize(this.textPassword.getX()+this.textPassword.getWidth()-this.labelPassword.getX(), 20);
		this.buttonPlay.setXY(this.labelUsername.getX(), this.textPassword.getY()-40);
		this.buttonPlay.addButtonPressedListener(this.buttonHandler);
		this.display.addWidget(this.buttonPlay);
	}
	
	/**
	 * Build the failed label.
	 */
	private void buildFailedLabel() {
		this.labelFailed = new Label("Last authentication failed.");
		this.labelFailed.setSizeToMinSize();
		this.labelFailed.setXY(this.display.getWidth()/2-this.labelFailed.getWidth()/2, this.buttonPlay.getY()-40);
		this.labelFailed.getAppearance().setTextColor(Color.WHITE);
	}
	
	/**
	 * Set the status text.
	 * @param text The <code>String</code> status text to be set.
	 */
	public void setStatus(String text) {
		this.labelStatus.setText(text);
	}
	
	/**
	 * Set the text displayed on the button.
	 * @param text The <code>String</code> text to be set.
	 */
	public void setButtonText(String text) {
		this.buttonPlay.setText(text);
	}
	
	/**
	 * Set the text of the user name text field.
	 * @param username The <code>String</code> text.
	 */
	public void setUsername(String username) {
		this.textUsername.setText(username);
	}
	
	/**
	 * Set the text of the password text field.
	 * @param password The <code>String</code> text.
	 */
	public void setPassword(String password) {
		this.textPassword.setText(password);
	}
	
	/**
	 * Enabled the failed text label.
	 */
	public void enableFailedLabel() {
		if(this.labelFailed.isInWidgetTree()) return;
		this.display.addWidget(this.labelFailed);
	}
	
	/**
	 * Retrieve the default status text.
	 * @return The default <code>String</code> status text.
	 */
	public String getDefaultStatus() {
		return this.defaultStatus;
	}
	
	/**
	 * Retrieve the default button text.
	 * @return The default <code>String</code> button text.
	 */
	public String getDefaultButton() {
		return EButton.Play.toString();
	}
	
	/**
	 * Retrieve the user name text the user entered.
	 * @return The <code>String</code> user name entered.
	 */
	public String getUsername() {
		return this.textUsername.getText();
	}
	
	/**
	 * Retrieve the password text the user entered.
	 * @return The <code>String</code> password entered.
	 */
	public String getPassword() {
		return this.textPassword.getText();
	}
}
