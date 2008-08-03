package com.sun.darkstar.example.snowman.game.entity.util;

import java.util.HashMap;

import com.sun.darkstar.example.snowman.common.entity.EditableEntity;
import com.sun.darkstar.example.snowman.common.entity.StaticEntity;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.entity.terrain.TerrainEntity;
import com.sun.darkstar.example.snowman.common.interfaces.IEntity;
import com.sun.darkstar.example.snowman.exception.DuplicatedIDException;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowballEntity;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowmanEntity;
import com.sun.darkstar.example.snowman.unit.Manager;
import com.sun.darkstar.example.snowman.unit.enumn.EManager;

/**
 * <code>EntityManager</code> is a <code>Manager</code> that is responsible for
 * managing all the <code>IEntity</code> in the game world.
 * <p>
 * <code>EntityManager</code> is responsible for all aspects of entity management
 * including entity creation, retrieving and destruction.
 * <p>
 * <code>EntityManager</code> maintains all the entities by their ID number. This
 * allows multiple entities with the same type defined by <code>EEntity</code>
 * exist. Positive ID numbers are assigned by the server and negative ID numbers
 * are assigned by the client. Entities with negative ID numbers are usually static
 * environment entities.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-05-2008 11:15 EST
 * @version Modified date: 07-01-2008 14:09 EST
 */
public class EntityManager extends Manager {
	/**
	 * The <code>EntityManager</code> instance.
	 */
	private static EntityManager instance;
	/**
	 * The <code>HashMap</code> of ID number and <code>IEntity</code> pair.
	 */
	private final HashMap<Integer, IEntity> entities;
	/**
	 * The client side current entity ID number.
	 */
	private int idcount;
	
	/**
	 * Constructor of <code>EntityManager</code>.
	 */
	private EntityManager() {
		super(EManager.EntityManager);
		this.entities = new HashMap<Integer, IEntity>();
		this.idcount = 0;
	}
	
	/**
	 * Retrieve the <code>EntityManager</code> instance.
	 * @return The <code>EntityManager</code> instance.
	 */
	public static EntityManager getInstance() {
		if(EntityManager.instance == null) {
			EntityManager.instance = new EntityManager();
		}
		return EntityManager.instance;
	}
	
	/**
	 * Register the given entity with <code>EntityManager</code>. This method
	 * should only be invoked by <code>IWorld</code> when it is first loaded.
	 * @param entity The <code>IEntity</code> to be registered.
	 * @return True if the given entity is successfully registered. False otherwise.
	 */
	public boolean registerEntity(IEntity entity) {
		final int id = entity.getID();
		if(this.entities.containsKey(id)) {
			this.logger.info("ID: " + id + " already in use");
			return false;
		}
		if(this.idcount > id) this.idcount = id;
		this.entities.put(Integer.valueOf(id), entity);
		return true;
	}
	
	/**
	 * Create an entity with given type and ID type.
	 * @param enumn The <code>EEntity</code> enumeration of the entity.
	 * @return The newly created <code>IEntity</code>.
	 */
	public IEntity createEntity(EEntity enumn) {
		this.idcount--;
		// This exception should never be thrown.
		try {return this.createEntity(enumn, this.idcount);} catch (DuplicatedIDException e) {return null;}
	}
	
	/**
	 * Create an entity with given type and ID type.
	 * @param enumn The <code>EEntity</code> enumeration of the entity.
	 * @param id The integer ID number of the entity.
	 * @return The newly created <code>IEntity</code>.
	 * @throws DuplicatedIDException If the given ID number is already in use.
	 */
	public IEntity createEntity(EEntity enumn, int id) throws DuplicatedIDException {
		if(this.entities.containsKey(Integer.valueOf(id))) throw new DuplicatedIDException(id);
		if(enumn == EEntity.Terrain) id = 0;
		IEntity entity = null;
		switch(enumn) {
		case Terrain: entity = new TerrainEntity(id); break;
		case SnowmanLocal: entity = new SnowmanEntity(id); break;
		case SnowmanDistributed: entity = new CharacterEntity(enumn, id); break;
		case Snowball: entity = new SnowballEntity(id); break;
		default:
			switch(enumn.getType()) {
			case Static: entity = new StaticEntity(enumn, id); break;
			case Editable: entity = new EditableEntity(enumn, id); break;
			}
			break;
		}
		this.entities.put(Integer.valueOf(id), entity);
		this.logger.info("Created entity " + enumn.toString() + "with ID number: " + id);
		return entity;
	}
	
	/**
	 * Remove the entity with given ID number.
	 * @param id The ID number of the entity to be destroyed.
	 * @return True if the entity is removed. False if it does not exist.
	 */
	public boolean removeEntity(int id) {
		final IEntity entity = this.entities.remove(Integer.valueOf(id));
		if(entity == null) {
			this.logger.info("Entity with ID number: " + id + " does not exist.");
			return false;
		}
		this.logger.info("Destroyed entity with ID number: " + id);
		return true;
	}
	
	/**
	 * Retrieve the entity with given ID number.
	 * @param id The ID number of the entity.
	 * @return The <code>IEntity</code> with given ID number.
	 * @throws ObjectNotFoundException If the entity with given ID number does not exist.
	 */
	public IEntity getEntity(int id) throws ObjectNotFoundException {
		final IEntity entity = this.entities.get(Integer.valueOf(id));
		if(entity == null) throw new ObjectNotFoundException("Entity " + String.valueOf(id));
		return entity;
	}
	
	/**
	 * Clears the <code>EntityManager</code> by first clear the entity
	 * pool the reset the ID number count to 0. This method should be
	 * invoked whenever a new <code>World</code> is loaded.
	 */
	@Override
	public void cleanup() {
		this.entities.clear();
		this.idcount = 0;
	}
}
