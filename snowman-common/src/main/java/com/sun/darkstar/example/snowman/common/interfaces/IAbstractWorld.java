package com.sun.darkstar.example.snowman.common.interfaces;

import com.jme.scene.Node;
import com.jme.util.export.Savable;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;

/**
 * <code>IAbstractWorld</code> defines the interface of a basic abstraction
 * of the game world. It maintains all the <code>IEntity</code>, their
 * corresponding <code>IView</code> and <code>IInfluence</code> of the entities
 * for registration purpose during import process. <code>IAbstractWorld</code>
 * is defined by its unique <code>EWorld</code> enumeration.
 * <p>
 * <code>IAbstractWorld</code> extends <code>Savable</code> which allows it
 * to be exported into a {@link jME} binary format that can be imported later
 * on for either editing purpose or at game run time.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-01-2008 24:04 EST
 * @version Modified date: 08-11-2008 16:26 EST
 */
public interface IAbstractWorld extends Savable {
	
	/**
	 * Retrieve the enumeration of this world.
	 * @return The <code>EWorld</code> enumeration.
	 */
	public EWorld getWorldEnumn();
	
	/**
	 * Retrieve the root node of all static entities.
	 * @return The static entity root <code>Node</code>.
	 */
	public Node getStaticRoot();

	/**
	 * Retrieve the root node of all dynamic entities.
	 * @return The dynamic entity root <code>Node</code>.
	 */
	public Node getDynamicRoot();
}
