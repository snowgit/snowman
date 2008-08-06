package com.sun.darkstar.example.snowman.exception;

/**
 * <code>ObjectNotFoundException</code> is thrown in response to attempting to
 * retrieve an object such as an <code>IEntity</code> or <code>IInfluence</code>
 * that is not created yet.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-06-2008 11:47 EST
 * @version Modified date: 06-06-2008 17:34 EST
 */
public class ObjectNotFoundException extends RuntimeException {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = -3361707271776252063L;
	
	/**
	 * Constructor of <code>ObjectNotFoundException</code>.
	 */
	public ObjectNotFoundException() {
		super();
	}
	
	/**
	 * Constructor of <code>ObjectNotFoundException</code>.
	 * @param objectID The ID of the object attempted to retrieve.
	 */
	public ObjectNotFoundException(String objectID) {
		super("Object " + objectID + "not found.");
	}
}
