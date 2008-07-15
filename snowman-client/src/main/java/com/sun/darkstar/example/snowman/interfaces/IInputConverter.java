package com.sun.darkstar.example.snowman.interfaces;

import org.fenggui.Display;

/**
 * <code>IInputConverter</code> defines the interface for converters that
 * converts {@link jME} input events to {@link FengGUI} events.
 * <p>
 * <code>IInputConverter</code> only converts the input events to the set
 * {@link FengGUI} <code>Display</code> instance. It maintains a reference
 * to the <code>Display</code> instance that should receive the converted
 * {@link FengGUI} events.
 * <p>
 * <code>IInputConverter</code> can be disabled manually to stop the conversion
 * from {@link jME} input events to {@link FengGUI} events. This method can
 * be used to disable all the current displaying GUI.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-09-2008 11:01 EST
 * @version Modified date: 07-09-2008 11:11 EST
 */
public interface IInputConverter {

	/**
	 * Set the display instance that should receive the converted events.
	 * @param display The {@link FengGUI} <code>Display</code> instance.
	 */
	public void setDisplay(Display display);
	
	/**
	 * Enable or disable the input conversion process.
	 * @param enabled True if conversion should be enabled. False otherwise.
	 */
	public void setEnabled(boolean enabled);
	
	/**
	 * Check if the input conversion process is enabled.
	 * @return True if conversion is enabled. False otherwise.
	 */
	public boolean isEnabled();
}
