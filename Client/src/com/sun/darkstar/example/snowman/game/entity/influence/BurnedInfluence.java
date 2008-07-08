package com.sun.darkstar.example.snowman.game.entity.influence;

import com.sun.darkstar.example.snowman.game.entity.influence.enumn.EInfluence;
import com.sun.darkstar.example.snowman.interfaces.IDynamicEntity;

/**
 * <code>BurnedInfluence</code> damages the target, reduces the size of the
 * target and increases the motion of the target including both animation
 * speed and movement speed.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-09-2008 12:03 EST
 * @version Modified date: 06-18-2008 12:45 EST
 */
public class BurnedInfluence extends Influence {
	/**
	 * The damage reduction in HP.
	 */
	private final int damage;
	/**
	 * The reduction percentage in scale.
	 */
	private final float reduceScale;
	/**
	 * The percentage increase in motion.
	 */
	private final float increaseMotion;
	
	/**
	 * Constructor of <code>BurnedInfluence</code>.
	 */
	public BurnedInfluence() {
		super(EInfluence.Burned);
		// TODO set values.
		this.damage = 0;
		this.reduceScale = 0;
		this.increaseMotion = 0;
	}

	@Override
	public void perform(IDynamicEntity entity) {
		// TODO Auto-generated method stub

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class getClassTag() {
		return BurnedInfluence.class;
	}
}
