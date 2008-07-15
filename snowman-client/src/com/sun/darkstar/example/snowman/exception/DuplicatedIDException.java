package com.sun.darkstar.example.snowman.exception;

/**
 * <code>DuplicatedIDException</code> is thrown in response to attempting to
 * create an <code>IEntity</code> with an existing occupied ID number.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-06-2008 13:20 EST
 * @version Modified date: 06-06-2008 17:31 EST
 */
public class DuplicatedIDException extends Exception {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 5878061806142912677L;
	
	/**
	 * Constructor of <code>DuplicatedIDException</code>.
	 */
	public DuplicatedIDException() {
		super();
	}
	
	/**
	 * Constructor of <code>DuplicatedIDException</code>.
	 * @param id The duplicated ID number.
	 */
	public DuplicatedIDException(int id) {
		super("Duplicated ID number: " + id);
	}
}
