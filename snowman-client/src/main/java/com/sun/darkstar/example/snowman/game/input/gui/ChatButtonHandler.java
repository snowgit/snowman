package com.sun.darkstar.example.snowman.game.input.gui;

import org.fenggui.event.ButtonPressedEvent;
import org.fenggui.event.IButtonPressedListener;

import com.sun.darkstar.example.snowman.game.gui.scene.ChatGUI;

/**
 * <code>ChatButtonHandler</code> handles chat button pressed events.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 11-12-2008 13:58 EST
 * @version Modified date: 11-12-2008 14:41 EST
 */
public class ChatButtonHandler implements IButtonPressedListener {
	/**
	 * The <code>ChatGUI</code> instance.
	 */
	private final ChatGUI gui;
	/**
	 * The flag indicates if the button was pressed.
	 */
	private boolean pressed;
	
	/**
	 * Constructor of <code>ChatButtonHandler</code>
	 * @param gui The <code>ChatGUI</code> instance.
	 */
	public ChatButtonHandler(ChatGUI gui) {
		this.gui = gui;
		this.pressed = false;
	}

	@Override
	public void buttonPressed(ButtonPressedEvent e) {
		this.pressed = !this.pressed;
		this.gui.setStayEnabled(this.pressed);
	}
}
