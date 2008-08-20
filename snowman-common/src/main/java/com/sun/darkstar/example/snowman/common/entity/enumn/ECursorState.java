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
    Invalid,
    /**
     * The trying to move state
     */
    TryingToMove,
    /**
     * The targeting other snowman state.
     */
    Targeting,
    /**
     * The trying to grab flag state.
     */
    TryingToGrab
}
