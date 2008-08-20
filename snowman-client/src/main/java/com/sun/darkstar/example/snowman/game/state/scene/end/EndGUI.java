package com.sun.darkstar.example.snowman.game.state.scene.end;

import org.fenggui.Label;
import org.fenggui.util.Alphabet;
import org.fenggui.util.Color;
import org.fenggui.util.fonttoolkit.FontFactory;

import com.sun.darkstar.example.snowman.game.gui.GUIPass;

/**
 * <code>EndGUI</code> extends <code>GUIPass</code> to define the user
 * interface for the end scene. It initializes and maintains all the
 * {@link FengGUI} widgets.
 * 
 * @author Owen Kellett
 */
public class EndGUI extends GUIPass {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The default status text.
	 */
	private final String message;
	/**
	 * The status note.
	 */
	private Label labelMessage;
	/**
	 * The winner label
	 */
	private Label labelWinner;
	
	/**
	 * Constructor of <code>EndGUI</code>.
	 */
	public EndGUI() {
		super();
		this.message = "The game has ended.";
	}
	
	@Override
	public void buildWidgets() {
		this.buildMessage();
		this.buildWinner();
	}
	
	/**
	 * Build the message note.
	 */
	private void buildMessage() {
		this.labelMessage = new Label(this.message);
		java.awt.Font awtFont = new java.awt.Font("Sans", java.awt.Font.BOLD, 16);
		this.labelMessage.getAppearance().setFont(FontFactory.renderStandardFont(awtFont, true, Alphabet.getDefaultAlphabet()));
		this.labelMessage.setSizeToMinSize();
		this.labelMessage.setHeight(60);
		this.labelMessage.setXY(this.display.getWidth()/2-this.labelMessage.getWidth()/2, this.display.getHeight()/3*2);
		this.labelMessage.getAppearance().setTextColor(Color.WHITE);
		this.display.addWidget(this.labelMessage);
	}
	
	/**
	 * Build the winner message
	 */
	private void buildWinner() {
		this.labelWinner = new Label("Red has won!");
                java.awt.Font awtFont = new java.awt.Font("Sans", java.awt.Font.BOLD, 16);
                this.labelWinner.getAppearance().setFont(FontFactory.renderStandardFont(awtFont, true, Alphabet.getDefaultAlphabet()));
		this.labelWinner.setSizeToMinSize();
		this.labelWinner.setHeight(60);
		this.labelWinner.setXY(this.display.getWidth()/2-this.labelWinner.getWidth()/2, this.display.getHeight()/3);
		this.labelWinner.getAppearance().setTextColor(Color.WHITE);
		this.display.addWidget(this.labelWinner);
	}
	
	/**
	 * Set the status text.
	 * @param text The <code>String</code> status text to be set.
	 */
	public void setWinner(String text) {
		this.labelWinner.setText(text);
	}
}

