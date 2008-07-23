package com.sun.darkstar.example.snowman.common.util;

/**
 * <code>HPConverter</code> implements <code>IHPConverter</code> to define
 * the HP converter utility class.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-21-2008 17:53 EST
 * @version Modified date: 07-21-2008 17:59 EST
 */
public final class HPConverter implements IHPConverter {
	/**
	 * The <code>HPConverter</code> instance.
	 */
	private static HPConverter instance;
	
	/**
	 * Constructor of <code>HPConverter</code>.
	 */
	private HPConverter() {}
	
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

	public float convertRange(int hp) {
		// TODO Auto-generated method stub
		return 0;
	}

	public float convertScale(int hp) {
		// TODO Auto-generated method stub
		return 0;
	}

	public float convertSpeed(int hp) {
		// TODO Auto-generated method stub
		return 0;
	}
}
