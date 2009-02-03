package com.sun.darkstar.example.snowman.game.input.gui;

import com.jme.input.KeyInput;
import com.jme.input.KeyInputListener;
import com.sun.darkstar.example.snowman.client.Client;
import com.sun.darkstar.example.snowman.common.protocol.messages.ClientMessages;
import com.sun.darkstar.example.snowman.game.gui.scene.ChatGUI;
import com.sun.darkstar.example.snowman.game.task.util.TaskManager;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>ChatKeyHandler</code>
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 11-12-2008 15:28 EST
 * @version Modified date: 11-19-2008 10:49 EST
 */
public class ChatKeyHandler implements KeyInputListener {

    /**
     * The <code>ChatGUI</code> instance.
     */
    private final ChatGUI gui;
    /**
     * The <code>Client</code> instance.
     */
    private final Client client;

    /**
     * Constructor of <code>ChatKeyHandler</code>
     * @param gui The <code>ChatGUI</code> instance.
     * @param client The <code>Client</code> instance.
     */
    public ChatKeyHandler(ChatGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    @Override
    public void onKey(char character, int keyCode, boolean pressed) {
        if (keyCode == KeyInput.KEY_RETURN && pressed) {
            String message = this.gui.getChatMessage().trim();
            if (message.length() > 0) {
                TaskManager.getInstance().createTask(ETask.Chat, client.getHandler().getProcessor().getID(), message, true);
            }
            this.gui.cleanupChatInput();
            this.gui.setInputEnabled(!this.gui.isChatInputEnabled());
        }
    }
}
