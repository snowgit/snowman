package com.sun.darkstar.example.snowman.common.interfaces;

import com.jme.util.export.Savable;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity.EEntityType;

/**
 * <code>IEntity</code> defines the interface for all types of entity in the
 * system which follows the MVC (Model View Controller) design pattern.
 * <p>
 * <code>IEntity</code> defines the data store for an entity object in the
 * game world. Each <code>IEntity</code> has its own <code>EEntityType</code>,
 * <code>EEntity</code> which defines the entity and an integer ID number
 * that is assigned to it at construction time.
 * <p>
 * Two <code>IEntity</code> are considered as 'equal' if and only if they have
 * exactly the same integer ID number.
 * <p>
 * <code>IEntity</code> extends <code>Savable</code> interface so it can be
 * directly saved into a jME binary format which can then be imported
 * later on at game run time.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-27-2008 14:53 EST
 * @version Modified date: 07-01-2008 15:27 EST
 */
public interface IEntity extends Savable {

	/**
	 * Retrieve the ID number of this entity.
	 * @return The integer ID number.
	 */
	public int getID();
	
	/**
	 * Retrieve the type of this entity.
	 * @return The <code>EEntityType</code> enumeration.
	 */
	public EEntityType getType();

	/**
	 * Retrieve the enumeration of this entity.
	 * @return The <code>EEntity</code> enumeration.
	 */
	public EEntity getEnumn();

	/**
	 * Retrieve the string representation of this entity.
	 * @return The <code>String</code> representation.
	 */
	public String toString();
}
