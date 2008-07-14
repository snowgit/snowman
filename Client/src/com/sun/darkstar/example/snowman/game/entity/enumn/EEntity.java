package com.sun.darkstar.example.snowman.game.entity.enumn;

import com.sun.darkstar.example.snowman.game.physics.enumn.EMass;

/**
 * <code>EEntity</code> defines the enumerations of all types of entities in
 * the game world. It also maintains the default mass value of each type of
 * entity.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-04-2008 14:52 EST
 * @version Modified date: 07-02-2008 24:19 EST
 */
public enum EEntity {
	/**
	 * The terrain entity.
	 */
	Terrain(EEntityType.Editable, EMass.Terrain),
	/**
	 * The snowman entity.
	 */
	Snowman(EEntityType.Dynamic, EMass.Snowman);
	
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
	 * @param mass The <code>EMass</code> enumeration.
	 */
	private EEntity(EEntityType type, EMass mass) {
		this.type = type;
		this.mass = mass.getValue();
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
	
	@Override
	public String toString() {
		return this.type.toString() + super.toString();
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
