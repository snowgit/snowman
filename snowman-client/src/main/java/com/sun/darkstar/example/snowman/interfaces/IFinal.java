package com.sun.darkstar.example.snowman.interfaces;

import com.jme.util.export.Savable;
import com.sun.darkstar.example.snowman.interfaces.editable.IEditable;

/**
 * <code>IFinal</code> defines the interface of objects that cannot be edited
 * once is constructed. It is directly used at game run time by the corresponding
 * game systems.
 * <p>
 * <code>IFinal</code> is constructed out of an <code>IEditable</code> with
 * corresponding type during the export process at world editing stages. It
 * provides the functionality to process the information maintained by the
 * <code>IEditable</code> with corresponding type.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-01-2008 11:34 EST
 * @version Modified date: 07-01-2008 11:39 EST
 */
public interface IFinal extends Savable {

	/**
	 * Process the given editable instance to obtain the associated information.
	 * @param editable The <code>IEditable</code> with corresponding type.
	 */
	public void process(IEditable editable);
}
