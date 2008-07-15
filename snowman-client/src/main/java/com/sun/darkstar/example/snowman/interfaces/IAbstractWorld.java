package com.sun.darkstar.example.snowman.interfaces;

import com.jme.util.export.Savable;
import com.sun.darkstar.example.snowman.data.enumn.EWorld;

/**
 * <code>IAbstractWorld</code> defines the interface of a basic abstraction
 * of the game world. It maintains all the <code>IEntity</code>, their
 * corresponding <code>IView</code> and <code>IInfluence</code> of the entities
 * for registration purpose during import process. <code>IAbstractWorld</code>
 * is defined by its unique <code>EWorld</code> enumeration.
 * <p>
 * <code>IAbstractWorld</code> provides the functionality to register the
 * entities, views and influences it maintains with the corresponding
 * <code>Manager</code> during the import process.
 * <p>
 * <code>IAbstractWorld</code> extends <code>Savable</code> which allows it
 * to be exported into a {@link jME} binary format that can be imported later
 * on for either editing purpose or at game run time.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-01-2008 24:04 EST
 * @version Modified date: 07-01-2008 14:45 EST
 */
public interface IAbstractWorld extends Savable {

	/**
	 * Register the world by registering entities, views and influences with
	 * the corresponding managers.
	 */
	public void register();
	
	/**
	 * Retrieve the enumeration of this world.
	 * @return The <code>EWorld</code> enumeration.
	 */
	public EWorld getWorldEnumn();
}
