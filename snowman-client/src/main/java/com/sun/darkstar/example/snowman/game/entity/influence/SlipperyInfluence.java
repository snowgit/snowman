package com.sun.darkstar.example.snowman.game.entity.influence;

import com.sun.darkstar.example.snowman.common.entity.influence.enumn.EInfluence;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;

/**
 * <code>SlipperyInfluence</code> slows down the target in movement speed.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-09-2008 12:10 EST
 * @version Modified date: 06-18-2008 12:45 EST
 */
public class SlipperyInfluence extends Influence {
	/**
	 * The reduction percentage in movement speed.
	 */
	private final float reduceSpeed;

	/**
	 * Constructor of <code>SlipperyInfluence</code>.
	 */
	public SlipperyInfluence() {
		super(EInfluence.Slippery);
		// TODO Set values.
		this.reduceSpeed = 0;
	}
	
	@Override
	public void perform(IDynamicEntity entity) {
		// TODO Auto-generated method stub

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class getClassTag() {
		return SlipperyInfluence.class;
	}
}
