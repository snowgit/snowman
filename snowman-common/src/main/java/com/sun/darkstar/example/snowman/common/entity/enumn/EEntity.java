package com.sun.darkstar.example.snowman.common.entity.enumn;

import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;

/**
 * <code>EEntity</code> defines the enumerations of all types of entities in
 * the game world. It also maintains the default mass value of each type of
 * entity.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-04-2008 14:52 EST
 * @version Modified date: 07-25-2008 16:39 EST
 */
public enum EEntity {
	/**
	 * The terrain entity.
	 */
	Terrain(EEntityType.Editable, Float.POSITIVE_INFINITY),
	/**
	 * The locally controlled snowman entity.
	 */
	SnowmanLocal(EEntityType.Dynamic, SingletonRegistry.getHPConverter().getDefaultMass()),
	/**
	 * The distributed snowman entity.
	 */
	SnowmanDistributed(EEntityType.Dynamic, SingletonRegistry.getHPConverter().getDefaultMass()),
	/**
	 * The flag entity.
	 */
	Flag(EEntityType.Dynamic, Float.POSITIVE_INFINITY),
	/**
	 * The snow ball entity.
	 */
	Snowball(EEntityType.Dynamic, 0.005f);
	
	/**	
	 * The <code>EEntityType</code> value.
	 */
	private final EEntityType type;
	/**
	 * The mass value.
	 */
	private final float mass;
	
	/**
	 * Constructor of <code>EEntity</code>
	 * @param type The <code>EEntityType</code> enumeration.
	 */
	private EEntity(EEntityType type, float mass) {
		this.type = type;
		this.mass = mass;
	}
	
	/**
	 * Retrieve the type enumeration of this entity type.
	 * @return The <code>EEntityType</code> enumeration.
	 */
	public EEntityType getType() {
		return this.type;
	}
	
	/**
	 * Retrieve the mass value of this entity type.
	 * @return The float mass value.
	 */
	public float getMass() {
		return this.mass;
	}
	
	/**
	 * <code>EEntityType</code> defines all the types of <code>IEntity</code>
	 * in the system.
	 * 
	 * @author Yi Wang (Neakor)
	 * @author Tim Poliquin (Weenahmen)
	 * @version Creation date: 05-27-2008 15:10 EST
	 * @version Modified date: 05-27-2008 16:09 EST
	 */
	public enum EEntityType {
		/**
		 * The static entity type.
		 */
		Static,
		/**
		 * The dynamic entity type.
		 */
		Dynamic,
		/**
		 * The editable entity type.
		 */
		Editable
	}
}
