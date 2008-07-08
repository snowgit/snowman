package com.sun.darkstar.example.snowman.interfaces.editable;

import java.util.ArrayList;

import com.sun.darkstar.example.snowman.game.entity.view.terrain.TerrainCluster;
import com.sun.darkstar.example.snowman.interfaces.IAbstractWorld;

/**
 * <code>IEditableWorld</code> extends <code>IAbstractWorld</code> and
 * <code>IEditable</code> to define the interface for an editable world
 * during world editing stages through world editor.
 * <p>
 * <code>IEditableWorld</code> maintains only <code>IEditableView</code>.
 * During the registration process, <code>IEditableEntity</code> and
 * <code>IInfluence</code> are accessed through the views.
 * <p>
 * During the final world export process, all <code>IEditableView</code> are
 * invoked by the <code>World</code> to first construct <code>IStaticEntity</code>
 * based on the <code>IEditableEntity</code> of the views, then construct
 * <code>IStaticView</code> based on both the <code>IStaticEntity</code> and
 * the <code>IEditableView</code> itself.
 * <p>
 * <code>IEditableWorld</code> is created at the world editing stage where all
 * the editable entities in the game world are constructed and placed.
 * <p>
 * <code>IEditableWorld</code> is finalized into an <code>IWorld</code> instance
 * that is utilized by the <code>Game</code> at game run time.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-01-2008 24:39 EST
 * @version Modified date: 07-02-2008 13:36 EST
 */
public interface IEditableWorld extends IAbstractWorld, IEditable {

	/**
	 * Attach the given edtiable view to this editable world.
	 * @param view The <code>IEditableView</code> to be attached.
	 */
	public void attachView(IEditableView view);
	
	/**
	 * Detach the given edtiable view from this editable world.
	 * @param view The <code>IEditableView</code> to be detached.
	 */
	public void detachView(IEditableView view);
	
	/**
	 * Retrieve the terrain cluster of this world..
	 * @return The <code>TerrainCluster</code> instance.
	 */
	public TerrainCluster getTerrainCluster();
	
	/**
	 * Retrieve a shallow copy of the list of editable views.
	 * @return The <code>ArrayList</code> of <code>IEditableView</code> instances.
	 */
	public ArrayList<IEditableView> getViews();
}
