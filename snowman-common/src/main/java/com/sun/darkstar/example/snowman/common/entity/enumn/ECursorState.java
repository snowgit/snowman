package com.sun.darkstar.example.snowman.common.entity.enumn;


/**
 * <code>ECursorState</code> defines the enumerations of all the possible states
 * that the targeting cursor can be in.
 * 
 * @author Owen Kellett
 */
public enum ECursorState {
    /**
     * Invalid operation
     */
    Invalid("com/sun/darkstar/example/snowman/data/icons/IconWalking.png"),
    /**
     * The trying to move state
     */
    TryingToMove("com/sun/darkstar/example/snowman/data/icons/IconWalking.png"),
    /**
     * The targeting other snowman state.
     */
    Targeting("com/sun/darkstar/example/snowman/data/icons/IconTarget.png"),
    /**
     * The trying to grab flag state.
     */
    TryingToGrab("com/sun/darkstar/example/snowman/data/icons/IconGrab.png");

    final String iconLocation;
    
    private ECursorState(String location) {
		this.iconLocation = location;
	}
    
	public String getIconLocation() {
		return iconLocation;
	}
}
