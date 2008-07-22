package com.sun.darkstar.example.snowman.game.input.enumn;

/**
 * <code>EInputConverter</code> defines the enumerations of all types of GUI
 * <code>IInputConverter</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-16-2008 10:52 EST
 * @version Modified date: 07-21-2008 12:03 EST
 */
public enum EInputConverter {
	/**
	 * The keyboard GUI input converter enumeration.
	 */
	KeyboardConverter(EInputType.Keyboard),
	/**
	 * The mouse GUI input converter enumeration.
	 */
	MouseConverter(EInputType.Mouse);
	
	/**
	 * The <code>EInputType</code> enumeration.
	 */
	private final EInputType type;
	
	/**
	 * Constructor of <code>EInputConverter</code>.
	 * @param type The <code>EInputType</code> enumeration.
	 */
	private EInputConverter(EInputType type) {
		this.type = type;
	}
	
	/**
	 * Retrieve the input type of this converter type.
	 * @return The <code>EInputType</code> enumeration.
	 */
	public EInputType getType() {
		return this.type;
	}
}
