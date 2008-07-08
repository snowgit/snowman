package com.sun.darkstar.example.snowman.interfaces.editable;

import com.sun.darkstar.example.snowman.interfaces.IView;

/**
 * <code>IEditableView</code> defines the interface for views that represent
 * <code>IEditableEntity</code> during the world editing stages.
 * <p>
 * <code>IEditableView</code> is the intermediate editing stage for all
 * <code>IStaticView</code>. An <code>IEditableView</code> is created through
 * the world editor and can only exist during the world editing stages. It is
 * eventually converted to an <code>IStaticView</code> during the world export
 * process.
 * <p>
 * <code>IEditableView</code> provides the functionalities that are used for
 * editing the view. These functionalities are utilized by the world editor.
 * <p>
 * <code>IEditableView</code> is finalized into an <code>IStaticView</code>
 * instance that is utilized by <code>IStaticEntity</code> at game run time.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-30-2008 21:53 EST
 * @version Modified date: 07-01-2008 11:47 EST
 */
public interface IEditableView extends IView, IEditable {

	/**
	 * Enable or disable the axis view on this editable view.
	 * @param enabled True if axis should be shown. False otherwise.
	 */
	public void setAxisEnabled(boolean enabled);
	
	/**
	 * Enable or disable the wire frame view on this editable view.
	 * @param enabled True if wire frame should be shown. False otherwise.
	 */
	public void setWireEnabled(boolean enabled);
}
