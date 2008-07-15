package com.sun.darkstar.example.snowman.exception;

/**
 * <code>MissingComponentException</code> is thrown in response to attempting to
 * initialize a <code>Component</code> without necessary <code>Component</code>
 * being connected.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-23-2008 15:52 EST
 * @version Modified date: 06-06-2008 17:21 EST
 */
public class MissingComponentException extends Exception {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 5091083733501702459L;
	
	/**
	 * Constructor of <code>MissingComponentException</code>.
	 */
	public MissingComponentException() {
		super();
	}

	/**
	 * Constructor of <code>MissingComponentException</code>.
	 * @param component The name of the <code>Component</code> that is missing.
	 */
	public MissingComponentException(String component) {
		super("Missing Component: " + component.substring(component.lastIndexOf(".")+1, component.length()));
	}
}
