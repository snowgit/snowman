package com.sun.darkstar.example.snowman.game.gui.scene;

import org.fenggui.Button;
import org.fenggui.Container;
import org.fenggui.Span;
import org.fenggui.TabContainer;
import org.fenggui.TextEditor;
import org.fenggui.background.PlainBackground;
import org.fenggui.border.PlainBorder;
import org.fenggui.event.FocusEvent;
import org.fenggui.layout.StaticLayout;
import org.fenggui.text.TextStyle;
import org.fenggui.util.Color;
import org.fenggui.util.fonttoolkit.FontFactory;

import com.sun.darkstar.example.snowman.game.gui.GUIPass;
import com.sun.darkstar.example.snowman.game.gui.text.TextView;
import com.sun.darkstar.example.snowman.game.gui.widget.ScrollContainer;
import com.sun.darkstar.example.snowman.game.input.gui.ChatButtonHandler;

/**
 * <code>ChatGUI</code> defines the concrete implementation of the GUI render
 * pass that displays the chat interface during the battle stage of the game.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 11-12-2008 12:18 EST
 * @version Modified date: 11-12-2008 14:39 EST
 */
public class ChatGUI extends GUIPass {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 8866591274194274971L;
	/**
	 * The possible chat channels.
	 */
	private final String[] channels;
	/**
	 * The chat container.
	 */
	private Container mainContainer;
	/**
	 * The input container.
	 */
	private TabContainer inputContainer;
	/**
	 * The chat button.
	 */
	private Button chatButton;
	/**
	 * The array contains four output scroll containers for all four channels.
	 */
	private ScrollContainer[] outputContainers;
	/**
	 * The array contains four output text views for four chat channels.
	 */
	private TextView[] outputChannels;
	/**
	 * The array contains five different font styles for five kinds of messages.
	 */
	private TextStyle[] outputStyles;
	/**
	 * The flag indicates if the chat is currently stay enabled.
	 */
	private boolean stayEnabled;

	/**
	 * Constructor of <code>BattleGUI</code>
	 */
	public ChatGUI() {
		this.channels = new String[] {"All"};
	}

	@Override
	protected void buildWidgets() {
		this.buildMainContainer();
		// Add parts to the main container.
		this.mainContainer.addWidget(this.buildChatButton());
		this.mainContainer.addWidget(this.buildOuputField());
		// Build the input field which is not added to the main container initially.
		this.buildInputField();
		// Add the main container to the display.
		this.display.addWidget(this.mainContainer);
	}

	@Override
	protected void doUpdate(float tpf) {
		super.doUpdate(tpf);
		// Remove the not selected output containers.
		for(int i = 0; i < this.channels.length && this.isChatInputEnabled(); i++) {
			if(this.getChatChannel().equalsIgnoreCase(this.channels[i]) &&
					!this.getOutputContainer(this.getChatChannel()).isInWidgetTree()) {
				for(int j = 0; j < this.outputContainers.length; j++) {
					if(j != i) this.mainContainer.removeWidget(this.outputContainers[j]);
				}
				this.mainContainer.addWidget(this.outputContainers[i]);
				this.outputContainers[i].layout();
			}
		}
	}

	/**
	 * Build the main container which contains the whole chat sub GUI system.
	 */
	private void buildMainContainer() {
		// Create the main container.
		this.mainContainer = new Container(new StaticLayout());
		this.mainContainer.setSize(460, 216);
		this.mainContainer.setXY(5, 5);
	}

	/**
	 * Build the chat button.
	 */
	private Button buildChatButton() {
		// Create the chat button.
		this.chatButton = new Button("Chat");
		this.chatButton.setSize(60, 20);
		this.chatButton.setXY(0, 0);
		this.chatButton.addButtonPressedListener(new ChatButtonHandler(this));
		// Return the chat button.
		return this.chatButton;
	}

	/**
	 * Build the output text views which display the chat messages.
	 * @return The default channel output container.
	 */
	private ScrollContainer buildOuputField() {
		// Create the output containers.
		this.outputContainers = new ScrollContainer[this.channels.length];
		for(int i = 0; i < this.outputContainers.length; i++) {
			this.outputContainers[i] = new ScrollContainer(true);
			this.outputContainers[i].setSize(this.mainContainer.getWidth(), this.mainContainer.getHeight() - 2*this.chatButton.getHeight());
			this.outputContainers[i].setXY(0, 2*this.chatButton.getHeight());
		}
		// Create the output text channels and add them to their containers.
		this.outputChannels = new TextView[this.channels.length];
		for(int j = 0; j < this.outputChannels.length; j++) {
			this.outputChannels[j] = new TextView();
			this.outputChannels[j].setSize(this.outputContainers[j].getWidth(), this.outputContainers[j].getHeight());
			this.outputChannels[j].setXY(0, 0);
			this.outputChannels[j].setFadingEnabled(true);
			this.outputContainers[j].setInnerWidget(this.outputChannels[j]);
		}
		// Create the text styles.
		this.outputStyles = new TextStyle[this.channels.length];
		this.outputStyles[0] = new TextStyle(FontFactory.renderStandardFont(new java.awt.Font("Verdana", java.awt.Font.PLAIN, 12)), Color.WHITE);
		// Return the default channel container.
		return this.outputContainers[0];
	}

	/**
	 * Build the input container.
	 */
	private void buildInputField() {
		// Create the input container.
		this.inputContainer = new TabContainer(true);
		this.inputContainer.setSize(this.mainContainer.getWidth()-this.chatButton.getWidth(), 40);
		this.inputContainer.setXY(this.chatButton.getWidth(), 0);
		this.inputContainer.getAppearance().add(new PlainBackground(new Color(0,0,0,160)));
		// Create the general text field for input container.
		Container inputContainer = new Container(new StaticLayout());
		inputContainer.setSize(this.inputContainer.getWidth(), this.inputContainer.getHeight()/2);
		inputContainer.setXY(0, 0);
		inputContainer.getAppearance().add(new PlainBorder(1, 1, 1, 1,new Color(255,255,255,255), true, Span.BORDER));
		TextEditor messageInputText = new TextEditor(false);
		messageInputText.setSize(inputContainer.getWidth(), inputContainer.getHeight());
		messageInputText.setXY(0, 0);
		messageInputText.setMaxCharacters(75);
		inputContainer.addWidget(messageInputText);
		// Add tabs.
		for(String channel : this.channels) {
			this.inputContainer.addTab(channel, null, inputContainer);
		}
	}

	/**
	 * Append the given chat message to the correct output fields.
	 * @param channel The output channel of this message.
	 * @param playerName The name of the player who sent this message.
	 * @param chatMessage The chat message needs to be appended.
	 */
	public void appendChatMessage(String channel, String playerName, String chatMessage) {
		StringBuilder builder = new StringBuilder();
		builder.append(playerName).append(": ").append(chatMessage).append("\n");
		// Append the message with correct "to" string in specified channel.
		this.getOutputChannel(channel).appendText(builder.toString(), this.getOutputStyle(channel));
		// If channel is not all channel, append the message in all channel as well.
		if(!channel.equalsIgnoreCase(this.channels[0])) {
			this.outputChannels[0].appendText(builder.toString(), this.getOutputStyle(channel));
			this.outputContainers[0].layout();
		}
		// Layout the output container.
		this.getOutputContainer(channel).layout();
	}

	/**
	 * Enable or disable the input field.
	 * @param enabled True if the input field should be enabled. False disabled.
	 */
	public void setInputEnabled(boolean enabled) {
		if(this.stayEnabled && !enabled) {
			this.setInputFocused(true);
			return;
		}
		if(enabled) {
			this.mainContainer.addWidget(this.inputContainer);
		} else {
			this.mainContainer.removeWidget(this.inputContainer);
		}
		this.setInputFocused(enabled);
	}

	/**
	 * Set if the chat input field should stay enabled.
	 * @param stayEnabled True if the chat input field should stay enbled. False otherwise.
	 */
	public void setStayEnabled(boolean stayEnabled) {
		this.stayEnabled = stayEnabled;
		for(int i = 0; i < this.outputContainers.length; i++) {
			this.outputContainers[i].setVerticalScrollBarEnabled(stayEnabled);
			this.outputChannels[i].setFadingEnabled(!stayEnabled);
		}
		if(stayEnabled) {
			if(!this.isChatInputEnabled()) {
				this.inputContainer.layout();
				this.mainContainer.addWidget(this.inputContainer);
			}
			for(int i = 0; i < this.outputContainers.length; i++) {
				this.outputContainers[i].getAppearance().add(new PlainBackground(new Color(0,0,0,160)));
			}
			// Gain input focus.
			if(!this.isInTypingState()) this.setInputFocused(true);
		} else {
			this.mainContainer.removeWidget(this.inputContainer);
			for(int i = 0; i < this.outputContainers.length; i++) {
				this.outputContainers[i].getAppearance().removeAll();
			}
			// Lose input focus.
			if(this.isInTypingState()) this.setInputFocused(false);
		}
		this.mainContainer.layout();
	}

	private void setInputFocused(boolean focused) {
		TextEditor input = ((TextEditor)((Container)this.inputContainer.getSelectedTabWidget()).getWidget(0));
		if(focused && this.inputContainer.isInWidgetTree()) {
			input.focusChanged(new FocusEvent(input, false));
			this.inputContainer.focusChanged(new FocusEvent(this.inputContainer, false));
		} else if(!focused && this.inputContainer.isInWidgetTree()) {
			input.focusChanged(new FocusEvent(input, true));
			this.inputContainer.focusChanged(new FocusEvent(this.inputContainer, true));
		}
	}

	/**
	 * Retrieve the chat output container with given channel name.
	 * @param channel The name of the chat channel.
	 * @return The chat output container with given channel name.
	 */
	private ScrollContainer getOutputContainer(String channel) {
		for(int i = 0; i < this.channels.length; i++) {
			if(channel.equalsIgnoreCase(this.channels[i])) {
				return this.outputContainers[i];
			}
		}
		return null;
	}

	/**
	 * Retrieve the chat output channel with given channel name.
	 * @param channel The name of the chat channel.
	 * @return The chat output channel with given channel name.
	 */
	private TextView getOutputChannel(String channel) {
		for(int i = 0; i < this.channels.length; i++) {
			if(channel.equalsIgnoreCase(this.channels[i])) {
				return this.outputChannels[i];
			}
		}
		return null;
	}

	/**
	 * Retrieve the chat output text style with given channel name.
	 * @param channel The name of the chat channel.
	 * @return The chat output text style with given channel name.
	 */
	private TextStyle getOutputStyle(String channel) {
		for(int i = 0; i < this.channels.length; i++) {
			if(channel.equalsIgnoreCase(this.channels[i])) {
				return this.outputStyles[i];
			}
		}
		return null;
	}

	/**
	 * Retrieve the channel name with given index.
	 * @param index The <code>Integer</code> index value.
	 * @return The <code>String</code> name value of the channel.
	 */
	public String getChannel(int index) {
		if(index >= this.channels.length) throw new IndexOutOfBoundsException("Invalid channel index: " + index);
		return this.channels[index];
	}

	/**
	 * Retrieve the current selected chat channel.
	 * @return The current selected chat channel.
	 */
	public String getChatChannel() {
		return this.inputContainer.getSelectedTabLabel().getText();
	}

	/**
	 * Retrieve the chat message in the chat input field.
	 * @return The chat message.
	 */
	public String getChatMessage() {
		return ((TextEditor)((Container)this.inputContainer.getSelectedTabWidget()).getWidget(0)).getText();
	}

	/**
	 * Delete the chat message in the chat input field.
	 */
	public void cleanupChatInput() {
		((TextEditor)((Container)this.inputContainer.getSelectedTabWidget()).getWidget(0)).setText("");
	}

	/**
	 * Check if the chat input field is enbaled.
	 * @return True if the chat input field is enabled. False otherwise.
	 */
	public boolean isChatInputEnabled() {
		return this.inputContainer.isInWidgetTree();
	}

	/**
	 * Check if the user is typing in the input field.
	 * @return True if the user is typing in the input field. False otherwise.
	 */
	public boolean isInTypingState() {
		return ((TextEditor)((Container)this.inputContainer.getSelectedTabWidget()).getWidget(0)).isInWritingState();
	}
}
