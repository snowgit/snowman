package com.sun.darkstar.example.snowman.game.input.gui;

import com.jme.input.KeyInput;
import com.jme.input.KeyInputListener;
import com.sun.darkstar.example.snowman.game.gui.scene.ChatGUI;

/**
 * <code>ChatKeyHandler</code>
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 11-12-2008 3:28:09 PM EST
 * @version Modified date: 11-12-2008 3:28:09 PM EST
 */
public class ChatKeyHandler implements KeyInputListener {
	/**
	 * The <code>ChatGUI</code> instance.
	 */
	private final ChatGUI gui;

	/**
	 * Constructor of <code>ChatKeyHandler</code>
	 * @param gui The <code>ChatGUI</code> instance.
	 */
	public ChatKeyHandler(ChatGUI gui) {
		this.gui = gui;
	}

	@Override
	public void onKey(char character, int keyCode, boolean pressed) {
		if(keyCode == KeyInput.KEY_RETURN && pressed) {
			String message = this.gui.getChatMessage().trim();
			if(message.length() > 0) {
				this.gui.appendChatMessage(this.gui.getChannel(0), "I", message);
			}
			this.gui.cleanupChatInput();
			this.gui.setInputEnabled(!this.gui.isChatInputEnabled());
		}
	}
}
