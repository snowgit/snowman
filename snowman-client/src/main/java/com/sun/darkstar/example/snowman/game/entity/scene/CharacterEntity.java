package com.sun.darkstar.example.snowman.game.entity.scene;

import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.entity.enumn.EState;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.darkstar.example.snowman.game.entity.DynamicEntity;

/**
 * <code>CharacterEntity</code> extends <code>DynamicEntity</code> to define
 * the base snowman character in the game.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-24-2008 11:34 EST
 * @version Modified date: 08-06-2008 11:18 EST
 */
public class CharacterEntity extends DynamicEntity {
	/**
	 * The current HP of the snowman.
	 */
	protected int hp;
	/**
	 * The current <code>EState</code>.
	 */
	protected EState state;
	/**
	 * The current <code>Vector3f</code> destination.
	 */
	protected Vector3f destination;
	/**
	 * The target <code>IDynamicEntity</code> instance.
	 */
	protected IDynamicEntity target;
	/**
	 * The flag entity instance.
	 */
	protected DynamicEntity flag;

	/**
	 * Constructor of <code>CharacterEntity</code>.
	 * @param enumn The <code>EEntity</code> enumeration.
	 * @param id The ID number of this snowman.
	 */
	public CharacterEntity(EEntity enumn, int id) {
		super(enumn, id);
		this.hp = SingletonRegistry.getHPConverter().getMaxHP();
		this.state = EState.Idle;
	}
	
	/**
	 * Set the HP of this snowman.
	 * @param hp The new HP value to set.
	 */
	public void setHP(int hp) {
		this.hp = hp;
	}
	
	/**
	 * Add the given delta value to the character HP.
	 * @param delta The change in HP value.
	 */
	public void addHP(int delta) {
		this.hp -= delta;
	}
	
	/**
	 * Set the current state of the snowman.
	 * @param state The <code>EState</code> enumeration.
	 */
	public void setState(EState state) {
		this.state = state;
	}
	
	/**
	 * Set the destination of this snowman.
	 * @param destination The <code>Vector3f</code> destination to be set.
	 */
	public void setDestination(Vector3f destination) {
		this.destination = destination;
	}
	
	/**
	 * Set the current target of this character.
	 * @param target The target <code>IDynamicEntity</code> instance.
	 */
	public void setTarget(IDynamicEntity target) {
		this.target = target;
	}
	
	/**
	 * Set the flag entity the snowman is carrying.
	 * @param flag The flag <code>DynamicEntity</code> instance.
	 */
	public void setFlag(DynamicEntity flag) {
		this.flag = flag;
	}

	/**
	 * Retrieve the current HP value.
	 * @return The integer HP value.
	 */
	public int getHP() {
		return this.hp;
	}
	
	/**
	 * Retrieve the current state of the snowman.
	 * @return The <code>EState</code> enumeration.
	 */
	public EState getState() {
		return this.state;
	}
	
	/**
	 * Retrieve the destination of this snowman.
	 * @return The <code>Vector3f</code> destination.
	 */
	public Vector3f getDestination() {
		return this.destination;
	}
	
	/**
	 * Retrieve the target <code>IDynamicEntity</code> instance.
	 * @return The target <code>IDynamicEntity</code> instance.
	 */
	public IDynamicEntity getTarget() {
		return this.target;
	}
	
	/**
	 * Retrieve the flag entity that is carried by the character.
	 * @return The <code>IDynamicEntity</code> instance.
	 */
	public IDynamicEntity getFlag() {
		return this.flag;
	}
	
	/**
	 * Check if this character is still alive.
	 * @return True if this character is still alive. False otherwise.
	 */
	public boolean isAlive() {
		return (this.hp > 0);
	}
	
	/**
	 * Check if the character is carrying a flag.
	 * @return True if the character is carrying a flag. False otherwise.
	 */
	public boolean isCarrying() {
		return (this.flag != null);
	}
}
