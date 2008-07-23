package com.sun.darkstar.example.snowman.common.interfaces;


/**
 * <code>IStaticView</code> defines the interface for views that represent
 * <code>IStaticEntity</code> in the game world.
 * <p>
 * <code>IStaticView</code> is constructed from <code>IEditableView</code>
 * during the world export process. It extends <code>IFinal</code> to allow
 * construction based on <code>IEditable</code>.
 * <p>
 * <code>IStaticView</code> automatically locks the geometry data including
 * indices, vertices and normals, the bounding data, and the transformation
 * data. This ensures that this <code>IStaticView</code> is persistent at
 * all times.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-16-2008 16:10 EST
 * @version Modified date: 07-01-2008 14:03 EST
 */
public interface IStaticView extends IView, IFinal {

	/**
	 * Lock geometry, bounding and transformation data.
	 */
	public void lock();
}
