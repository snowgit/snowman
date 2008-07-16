package com.sun.darkstar.example.snowman.common.util;

import java.io.IOException;
import java.net.URL;

import com.jme.scene.Spatial;
import com.jme.util.export.binary.BinaryImporter;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;

/**
 * <code>DataImporter</code> is a singleton utility class that is responsible
 * for importing binary data files.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-16-2008 16:44 EST
 * @version Modified date: 07-16-2008 16:57 EST
 */
public final class DataImporter {
	/**
	 * The <code>DataImporter</code> instance.
	 */
	private static DataImporter instance;
	/**
	 * The <code>String</code> directory.
	 */
	private final String dir;
	/**
	 * The world data extension.
	 */
	private final String extWorld;
	
	/**
	 * Constructor of <code>DataImporter</code>.
	 */
	private DataImporter() {
		this.dir = "com/sun/darkstar/example/snowman/common/data/world";
		this.extWorld = ".world";
	}
	
	/**
	 * Retrieve the <code>DataImporter</code> instance.
	 * @return The <code>DataImporter</code> instance.
	 */
	public static DataImporter getInstance() {
		if(DataImporter.instance == null) {
			DataImporter.instance = new DataImporter();
		}
		return DataImporter.instance;
	}
	
	/**
	 * Retrieve the world geometry data.
	 * @param enumn The <code>EWorld</code> enumeration.
	 * @return The loaded <code>Spatial</code> world data.
	 */
	public Spatial getWorld(EWorld enumn) {
		URL url = this.getClass().getClassLoader().getResource(this.dir + enumn.toString() + this.extWorld);
		try {
			return (Spatial)BinaryImporter.getInstance().load(url.openStream());
		} catch (IOException e) {
			throw new NullPointerException("Cannot find world data: " + enumn.toString());
		}
	}
}
