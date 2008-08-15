package com.sun.darkstar.example.snowman.game.entity.influence.util;

import java.util.HashMap;

import com.sun.darkstar.example.snowman.common.entity.influence.enumn.EInfluence;
import com.sun.darkstar.example.snowman.common.interfaces.IInfluence;
import com.sun.darkstar.example.snowman.game.entity.influence.BurnedInfluence;
import com.sun.darkstar.example.snowman.game.entity.influence.SlipperyInfluence;
import com.sun.darkstar.example.snowman.unit.Manager;
import com.sun.darkstar.example.snowman.unit.enumn.EManager;

/**
 * <code>InfluenceManager</code> is a <code>Manager</code> that is responsible
 * for managing all <code>IInfluence</code> of static entities.
 * <p>
 * <code>InfluenceManager</code> manages the creation of an <code>IInfluence</code>.
 * It maintains an <code>IInfluence</code> pool which allows the system to reuse
 * created <code>IInfluence</code> for multiple static entities. This means that
 * <code>InfluenceManager</code> does not allow multiple <code>IInfluence</code>
 * with the same <code>EInfluence</code> enumeration exist.
 * <p>
 * <code>InfluenceManager</code> does not allow destruction of an influence, since
 * <code>IInfluence</code> objects are generally not resource intensive and the
 * static entities which share these <code>IInfluence</code> always exist.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-06-2008 11:35 EST
 * @version Modified date: 06-29-2008 17:53 EST
 */
public class InfluenceManager extends Manager {
	/**
	 * The <code>InfluenceManager</code> instance.
	 */
	private static InfluenceManager instance;
	/**
	 * The <code>HashMap</code> of <code>EInfluence</code> and <code>IInfluence</code> pair.
	 */
	private final HashMap<EInfluence, IInfluence> influences;
	
	/**
	 * Constructor of <code>InfluenceManager</code>.
	 */
	private InfluenceManager() {
		super(EManager.InfluenceManager);
		this.influences = new HashMap<EInfluence, IInfluence>();
	}
	
	/**
	 * Retrieve the <code>InfluenceManager</code> instance.
	 * @return The <code>InfluenceManager</code> instance.
	 */
	public static InfluenceManager getInstance() {
		if(InfluenceManager.instance == null) {
			InfluenceManager.instance = new InfluenceManager();
		}
		return InfluenceManager.instance;
	}
	
	/**
	 * Register the given influence with the <code>InfluenceManager</code>.
	 * @param influence The <code>IInfluence</code> to be registered.
	 * @return True if the given influence is successfully registered. False otherwise.
	 */
	public boolean registerInfluence(IInfluence influence) {
		final EInfluence enumn = influence.getEnumn();
		if(this.influences.containsKey(enumn)) {
			this.logger.fine("Influence has already been registered.");
			return false;
		}
		this.influences.put(enumn, influence);
		return true;
	}
	
	/**
	 * Retrieve the influence with given influence ID.
	 * @param enumn The <code>EInfluence</code> enumeration.
	 * @return The <code>IInfluence</code> with given ID.
	 */
	public IInfluence getInfluence(EInfluence enumn) {
		IInfluence influence = this.influences.get(enumn);
		if(influence == null) return this.createInfluence(enumn);
		return influence;
	}
	
	/**
	 * Create an influence based on given influence ID.
	 * @param enumn The <code>EInfluence</code> enumeration.
	 * @return The <code>IInfluence</code> with given ID.
	 */
	private IInfluence createInfluence(EInfluence enumn) {
		IInfluence influence = null;
		switch(enumn) {
		case Burned:
			influence = new BurnedInfluence();
			break;
		case Slippery:
			influence = new SlipperyInfluence();
			break;
		}
		this.influences.put(enumn, influence);
		this.logger.fine("Created " + enumn.toString() + " influence");
		return influence;
	}
	
	/**
	 * Clean up <code>InfluenceManager</code> by removing all influences.
	 */
	@Override
	public void cleanup() {
		this.influences.clear();
	}
}
