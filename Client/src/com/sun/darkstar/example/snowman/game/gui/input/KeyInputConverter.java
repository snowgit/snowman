package com.sun.darkstar.example.snowman.game.gui.input;

import org.fenggui.Display;
import org.fenggui.event.Key;
import org.lwjgl.input.Keyboard;

import com.jme.input.KeyInputListener;
import com.sun.darkstar.example.snowman.interfaces.IInputConverter;

/**
 * <code>KeyInputConverter</code> implements <code>IInputConverter</code> and
 * <code>KeyInputListener</code> to define the singleton utility class which
 * converts {@link jME} key inputs into {@link FengGUI} events for the GUI
 * systems to process.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-28-2008 12:14 EST
 * @version Modified date: 07-09-2008 11:46 EST
 */
public class KeyInputConverter implements IInputConverter, KeyInputListener {
	/**
	 * The <code>KeyInputConverter</code> instance.
	 */
	private static KeyInputConverter instance;
	/**
	 * The {@link FengGUI }<code>Display</code> instance.
	 */
	private Display display;
	/**
	 * The flag indicates if this converter is enabled.
	 */
	private boolean enabled;

	/**
	 * Constructor of <code>KeyInputConverter</code>.
	 */
	private KeyInputConverter() {
		this.enabled = true;
	}

	/**
	 * Retrieve the <code>KeyInputConverter</code> instance.
	 * @return The <code>KeyInputConverter</code> instance.
	 */
	public static KeyInputConverter getInstance() {
		if(KeyInputConverter.instance == null) {
			KeyInputConverter.instance = new KeyInputConverter();
		}
		return KeyInputConverter.instance;
	}

	/**
	 * Invoked whenever a key is pressed or released.
	 * @param character The character associated with pressed key, 0 if not applicable.
	 * @param keyCode The key code of the pressed/released key.
	 * @param pressed True if key was pressed, false if released.
	 */
	public void onKey(char character, int keyCode, boolean pressed) {
		if(!this.enabled || this.display == null) return;
		// Convert the last pressed LWJGL key to FengGUI key event.
		Key keyEvent = this.convertKeyEvent();
		// If the key was pressed, fire both KeyPressedEvent and KeyTypedEvent.
		if(pressed) {
			this.display.fireKeyPressedEvent(character, keyEvent);
			this.display.fireKeyTypedEvent(character);
			// Otherwise fire the KeyReleasedEvent.
		} else {
			this.display.fireKeyReleasedEvent(character, keyEvent);
		}
	}

	/**
	 * Convert the last pressed LWJGL key to FengGUI key event.
	 * @return The GUI <code>Key</code> event of the last pressed key.
	 */
	private Key convertKeyEvent() {
		Key GUIKey;
		switch(Keyboard.getEventKey()) {
		case Keyboard.KEY_BACK: GUIKey = Key.BACKSPACE;	break;
		case Keyboard.KEY_RETURN: GUIKey = Key.ENTER; break;
		case Keyboard.KEY_DELETE: GUIKey = Key.DELETE; break;
		case Keyboard.KEY_UP: GUIKey = Key.UP; break;
		case Keyboard.KEY_RIGHT: GUIKey = Key.RIGHT; break;
		case Keyboard.KEY_LEFT:	GUIKey = Key.LEFT; break;
		case Keyboard.KEY_DOWN: GUIKey = Key.DOWN; break;
		case Keyboard.KEY_SCROLL: GUIKey = Key.SHIFT; break;
		case Keyboard.KEY_LMENU: GUIKey = Key.ALT; break;
		case Keyboard.KEY_RMENU: GUIKey = Key.ALT; break;
		case Keyboard.KEY_LCONTROL: GUIKey = Key.CTRL; break;
		case Keyboard.KEY_RSHIFT: GUIKey = Key.SHIFT; break;     
		case Keyboard.KEY_LSHIFT: GUIKey = Key.SHIFT; break;              
		case Keyboard.KEY_RCONTROL: GUIKey = Key.CTRL; break;
		case Keyboard.KEY_INSERT: GUIKey = Key.INSERT; break;
		case Keyboard.KEY_TAB: GUIKey = Key.TAB; break;
		case Keyboard.KEY_F12: GUIKey = Key.F12; break;
		case Keyboard.KEY_F11: GUIKey = Key.F11; break;
		case Keyboard.KEY_F10: GUIKey = Key.F10; break;
		case Keyboard.KEY_F9: GUIKey = Key.F9; break;
		case Keyboard.KEY_F8: GUIKey = Key.F8; break;
		case Keyboard.KEY_F7: GUIKey = Key.F7; break;
		case Keyboard.KEY_F6: GUIKey = Key.F6; break;
		case Keyboard.KEY_F5: GUIKey = Key.F5; break;
		case Keyboard.KEY_F4: GUIKey = Key.F4; break;
		case Keyboard.KEY_F3: GUIKey = Key.F3; break;
		case Keyboard.KEY_F2: GUIKey = Key.F2; break;
		case Keyboard.KEY_F1: GUIKey = Key.F1; break;
		default:
			if("1234567890".indexOf(Keyboard.getEventCharacter()) != -1)  GUIKey = Key.DIGIT;
			else GUIKey = Key.LETTER;
		break;
		}
		return GUIKey;
	}

	@Override
	public void setDisplay(Display display) {
		this.display = display;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
