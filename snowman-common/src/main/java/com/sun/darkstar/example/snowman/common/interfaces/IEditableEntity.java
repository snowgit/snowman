package com.sun.darkstar.example.snowman.common.interfaces;

import java.util.ArrayList;


/**
 * <code>IEditableEntity</code> defines the interface for all entities that
 * can be created and edited through the world editor during world editing
 * stages.
 * <p>
 * <code>IEditableEntity</code> defines the intermediate editing stage for all
 * <code>IStaticEntity</code>. An <code>IEditableEntity</code> is created through
 * the world editor and can only exist during the world editing stages. It is
 * eventually converted to an <code>IStaticEntity</code> during the world export
 * process.
 * <p>
 * <code>IEditableEntity</code> maintains a list of <code>IInfluence</code> that
 * are added to the final <code>IStaticEntity</code> during the export process of
 * the world.
 * <p>
 * <code>IEditableEntity</code> is finalized into an <code>IStaticEntity</code>
 * instance that is utilized by <code>IWorld</code> at game run time.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-30-2008 21:45 EST
 * @version Modified date: 07-01-2008 11:42 EST
 */
public interface IEditableEntity extends IEntity, IEditable {

	/**
	 * Add the given influence to this editable entity.
	 * @param influence The <code>IInfluence</code> to be added.
	 * @return True if the given influence is added. False if influence already exists.
	 */
	public boolean attachInfluence(IInfluence influence);

	/**
	 * Remove the influence with given ID.
	 * @param influence The <code>IInfluence</code> to be removed.
	 * @return True if the given influence is removed. False if it does not exist.
	 */
	public boolean detachInfluence(IInfluence influence);
	
	/**
	 * Retrieve a shallow copy of the list of influences.
	 * @return The <code>ArrayList</code> of <code>IInfluence</code>.
	 */
	public ArrayList<IInfluence> getInfluences();
}
