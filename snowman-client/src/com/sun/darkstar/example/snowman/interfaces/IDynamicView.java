package com.sun.darkstar.example.snowman.interfaces;

/**
 * <code>IDynamicView</code> defines the interface for views that represent
 * <code>IDynamicEntity</code> in the game world.
 * <p>
 * <code>IDynamicView</code> allows the view to be modified through the update
 * method. Once the state of the <code>IDynamicEntity</code> which this view
 * represents changes, this <code>IDynamicView</code> is considered dirty and
 * should be updated by the <code>ViewManager</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-16-2008 16:16 EST
 * @version Modified date: 06-16-2008 16:21 EST
 */
public interface IDynamicView extends IView {

	/**
	 * Update this dynamic view based on the new state of its base entity.
	 * @param interpolation The frame rate interpolation value.
	 */
	public void update(float interpolation);
}
