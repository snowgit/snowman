package com.sun.darkstar.example.snowman.common.interfaces;

import com.jme.util.export.Savable;

/**
 * <code>IEditable</code> defines the interface of all objects that can be
 * edited by the world editor during world editing stages.
 * <p>
 * <code>IEditable</code> provides the functionality for constructing the
 * corresponding <code>IFinal</code> instance based on the current maintained
 * information. In most cases, <code>IEditable</code> is finalized into the
 * corresponding static instance.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-01-2008 11:28 EST
 * @version Modified date: 07-01-2008 11:34 EST
 */
public interface IEditable extends Savable {

	/**
	 * Construct the final instance with corresponding type based on the
	 * current maintained information.
	 * @return The <code>IFinal</code> instance with corresponding type.
	 */
	public IFinal constructFinal();
}
