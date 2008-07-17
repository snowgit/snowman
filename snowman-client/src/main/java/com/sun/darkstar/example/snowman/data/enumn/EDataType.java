package com.sun.darkstar.example.snowman.data.enumn;

/**
 * <code>EDataType</code> defines enumerations of all the types of data files
 * utilized by the data system. Each <code>EDataType</code> enumeration is
 * paired with a specific file extension and a directory of that data type.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-17-2008 11:14 EST
 * @version Modified date: 07-17-2008 11:30 EST
 */
public enum EDataType {
	/**
	 * The animation data type.
	 */
	Animation(".anim", "com/sun/darkstar/example/snowman/data/animation/"),
	/**
	 * The static mesh data type.
	 */
	StaticMesh(".mesh", "com/sun/darkstar/example/snowman/data/mesh/"),
	/**
	 * The character dynamic mesh data type.
	 */
	DynamicMesh(".char", "com/sun/darkstar/example/snowman/data/mesh/character/"),
	/**
	 * The system data type.
	 */
	SystemData(".data", "com/sun/darkstar/example/snowman/data/system/"),
	/**
	 * The texture data type.
	 */
	Texture(".tex", "com/sun/darkstar/example/snowman/data/texture/"),
	/**
	 * The world data type.
	 */
	World(".wld", "com/sun/darkstar/example/snowman/data/world/");
	
	/**
	 * The <code>String</code> extension.
	 */
	private final String ext;
	/**
	 * The <code>String</code> directory of the extension.
	 */
	private final String dir;
	
	/**
	 * Constructor of <code>EDataType</code>.
	 * @param ext The <code>String</code> extension.
	 * @param dir The <code>String</code> directory.
	 */
	private EDataType(String ext, String dir) {
		this.ext = ext;
		this.dir = dir;
	}
	
	/**
	 * Retrieve the actual extension of the data type.
	 * @return The <code>String</code> extension.
	 */
	public String getExtension() {
		return this.ext;
	}
	
	/**
	 * Retrieve the directory associated with this data type.
	 * @return The <code>String</code> directory of the data type.
	 */
	public String getDirectory() {
		return this.dir;
	}
	
	/**
	 * Convert the given file name into a complete path of that file.
	 * @param file The name of the file.
	 * @return The <code>String</code> path to the file with given file name.
	 */
	public String toPath(String file) {
		return this.dir + file + this.ext;
	}
}
