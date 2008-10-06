package com.sun.darkstar.example.snowman.common.interfaces;

import com.jme.util.export.Savable;
import com.sun.darkstar.example.snowman.common.entity.influence.enumn.EInfluence;

/**
 * <code>IInfluence</code> defines the interface for all types of influences
 * that affect <code>IDynamicEntity</code>.
 * <p>
 * <code>IInfluence</code> has a unique <code>EInfluenceID</code> that defines
 * the type of this <code>IInfluence</code>. Two <code>IInfluence</code> are
 * considered 'equal' if and only if they have the same <code>EInfluenceID</code>.
 * <p>
 * <code>IInfluence</code> can be performed on all entities that implements
 * <code>IDynamicEntity</code>.
 * <p>
 * <code>IInfluence</code> extends <code>Savable</code> interface so it can be
 * directly saved into a jME binary format which can then be imported
 * later on at game run time.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-04-2008 15:17 EST
 * @version Modified date: 06-18-2008 11:25 EST
 */
public interface IInfluence extends Savable {

	/**
	 * Perform this influence on the given entity.
	 * @param entity The <code>IDynamicEntity</code> to be affected.
	 */
	public void perform(IDynamicEntity entity);
	
	/**
	 * Retrieve the ID of this influence.
	 * @return The <code>EInfluence</code> ID enumeration.
	 */
	public EInfluence getEnumn();
}
