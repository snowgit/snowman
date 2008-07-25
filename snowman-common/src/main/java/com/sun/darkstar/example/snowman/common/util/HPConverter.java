package com.sun.darkstar.example.snowman.common.util;

/**
 * <code>HPConverter</code> implements <code>IHPConverter</code> to define
 * the HP converter utility class.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-21-2008 17:53 EST
 * @version Modified date: 07-25-2008 12:08 EST
 */
public final class HPConverter implements IHPConverter {
	/**
	 * The <code>HPConverter</code> instance.
	 */
	private static HPConverter instance;
	/**
	 * The maximum HP value.
	 */
	private final int maxHP;
	/**
	 * The maximum scale value.
	 */
	private final float maxScale;
	/**
	 * The default mass value.
	 */
	private final float defaultMass;
	/**
	 * The default range value.
	 */
	private final float defaultRange;
	
	/**
	 * Constructor of <code>HPConverter</code>.
	 */
	private HPConverter() {
		this.maxHP = 100;
		this.maxScale = 2;
		this.defaultMass = 10;
		this.defaultRange = 10;
	}
	
	/**
	 * Retrieve the <code>HPConverter</code> instance.
	 * @return The <code>HPConverter</code> instance.
	 */
	public static IHPConverter getInstance() {
		if(HPConverter.instance == null) {
			HPConverter.instance = new HPConverter();
		}
		return HPConverter.instance;
	}

	@Override
	public float convertScale(int hp) {
		return ((this.maxScale * this.maxHP) - hp) / this.maxHP;
	}

	@Override
	public float convertMass(int hp) {
		return this.convertScale(hp) * this.defaultMass;
	}

	@Override
	public float convertRange(int hp) {
		return this.convertScale(hp) * this.defaultRange;
	}

	@Override
	public int getMaxHP() {
		return this.maxHP;
	}

	@Override
	public float getDefaultMass() {
		return this.defaultMass;
	}

	@Override
	public float getDefaultRange() {
		return this.defaultRange;
	}
}
