package com.sun.darkstar.example.snowman.common.interfaces;

import java.util.ArrayList;


/**
 * <code>IWorld</code> extends <code>IAbstractWorld</code> and
 * <code>IFinal</code> to define the interface of run time game world data
 * structure.
 * <p>
 * <code>IWorld</code> maintains only a list of <code>IStaticView</code>.
 * During the registration process, <code>IEditableEntity</code> and
 * <code>IInfluence</code> are accessed through the views.
 * <p>
 * <code>IWorld</code> is constructed through world editor during the world
 * export process. It provides the functionality to process the information of
 * an <code>IEditableWorld</code> to create corresponding static information
 * before export.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-17-2008 13:10 EST
 * @version Modified date: 07-23-2008 11:04 EST
 */
public interface IWorld extends IAbstractWorld, IFinal {

	/**
	 * Retrieve all the views maintained by the world.
	 * @return The <code>ArrayList</code> of <code>IStaticView</code>.
	 */
	public ArrayList<IStaticView> getViews();
}
