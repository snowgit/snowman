package com.sun.darkstar.example.snowman.common.interfaces;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.util.export.Savable;

/**
 * <code>IView</code> defines the interface for all types of view that
 * represents entities in the system which follows the MVC (Model View Controller)
 * design pattern.
 * <p>
 * <code>IView</code> defines the graphic representation of an entity. It
 * maintains graphic information includes meshes, animations and etc.
 * associated with the entity. It is able to be directly attached to the
 * scene graph for rendering.
 * <p>
 * <code>IView</code> maintains a reference to the <code>IEntity</code> it
 * represents since every <code>IView</code> has to have a data store for the
 * actual object in game world. Two <code>View</code> are considered 'equal'
 * if and only if they represent the same <code>IEntity</code>.
 * <p>
 * <code>IView</code> extends <code>Savable</code> interface so it can be
 * directly saved into a {@link jME} binary format which can then be imported
 * later on at game run time.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-27-2008 14:53 EST
 * @version Modified date: 07-01-2008 18:21 EST
 */
public interface IView extends Savable {

	/**
	 * Attach the given mesh to this view.
	 * @param mesh The <code>Spatial</code> to be attached.
	 */
	public void attachMesh(Spatial mesh);
	
	/**
	 * Attach this view to the given <code>Node</code>.
	 * @param parent The <code>Node</code> scene graph parent to attach to.
	 */
	public void attachTo(Node parent);
	
	/**
	 * Detach this view from the parent scene graph.
	 * @return True if view is detached successfully. False if this view is not in scene graph.
	 */
	public boolean detachFromParent();

	/**
	 * Retrieve the entity this view represents.
	 * @return The <code>IEntity</code> instance.
	 */
	public IEntity getEntity();
}
