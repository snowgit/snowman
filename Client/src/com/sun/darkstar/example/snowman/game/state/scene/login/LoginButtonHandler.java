package com.sun.darkstar.example.snowman.game.state.scene.login;

import org.fenggui.event.ButtonPressedEvent;
import org.fenggui.event.IButtonPressedListener;

import com.sun.darkstar.example.snowman.game.gui.enumn.EButton;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.game.task.util.TaskManager;

/**
 * <code>LoginButtonHandler</code> is responsible for monitoring and processing
 * button pressed events in login scene.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-09-2008 15:46 EST
 * @version Modified date: 07-10-2008 16:42 EST
 */
public class LoginButtonHandler implements IButtonPressedListener {
	/**
	 * The <code>LoginGUI</code> instance.
	 */
	private final LoginGUI gui;
	
	/**
	 * Constructor of <code>LoginButtonHandler</code>.
	 * @param gui The <code>LoginGUI</code> instance.
	 */
	public LoginButtonHandler(LoginGUI gui) {
		this.gui = gui;
	}

	@Override
	public void buttonPressed(ButtonPressedEvent e) {
		if(e.getTrigger().getText().equalsIgnoreCase(EButton.Play.toString())) {
			TaskManager.getInstance().createTask(ETask.Login, this.gui.getUsername(), this.gui.getPassword());
		}
	}
}
