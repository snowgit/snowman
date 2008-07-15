package com.sun.darkstar.example.snowman.data.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

import com.jme.image.Texture;
import com.jme.scene.SharedMesh;
import com.jme.util.TextureManager;
import com.jme.util.export.Savable;
import com.jme.util.export.binary.BinaryImporter;
import com.jme.util.resource.MultiFormatResourceLocator;
import com.jme.util.resource.ResourceLocatorTool;
import com.model.md5.JointAnimation;
import com.model.md5.ModelNode;
import com.sun.darkstar.example.snowman.data.enumn.EAnimation;
import com.sun.darkstar.example.snowman.data.enumn.ESystemData;
import com.sun.darkstar.example.snowman.data.enumn.ETexture;
import com.sun.darkstar.example.snowman.data.enumn.EWorld;
import com.sun.darkstar.example.snowman.game.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.interfaces.IWorld;
import com.sun.darkstar.example.snowman.unit.Manager;
import com.sun.darkstar.example.snowman.unit.enumn.EManager;

/**
 * <code>DataManager</code> is a <code>Manager</code> which is responsible
 * for managing all types of data resources used by the game.
 * <p>
 * <code>DataManager</code> internally manages several asset pools based on
 * the types of the assets. These asset pools allow caching and cloning of
 * assets that may be shared by multiple <code>Entity</code> in the game.
 * <p>
 * <code>DataManager</code> allows caching assets for a particular game state
 * during the initialization process of the game state. The cached assets are
 * stored in the various asset pools which can then be retrieved, cloned or
 * destroyed later on.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-09-2008 14:48 EST
 * @version Modified date: 07-09-2008 17:36 EST
 */
public class DataManager extends Manager {
	/**
	 * The <code>DataManager</code> instance.
	 */
	private static DataManager instance;
	/**
	 * The <code>JointAnimation</code> asset pool. 
	 */
	private final HashMap<EAnimation, JointAnimation> animationPool;
	/**
	 * The <code>SharedMesh</code> asset pool.
	 */
	private final HashMap<EEntity, SharedMesh> meshPool;
	/**
	 * The character <code>ModelNode</code> asset pool.
	 */
	private final HashMap<EEntity, ModelNode> characterPool;
	/**
	 * The <code>Texture</code> asset pool.
	 */
	private final HashMap<ETexture, Texture> texturePool;
	/**
	 * The world resource directory.
	 */
	private final String worldDir;
	/**
	 * The animation resource directory.
	 */
	private final String animationDir;
	/**
	 * The mesh resource directory.
	 */
	private final String meshDir;
	/**
	 * The character model resource directory.
	 */
	private final String characterDir;
	/**
	 * The system data resource directory.
	 */
	private final String systemDir;
	/**
	 * The <code>Texture</code> resource directory.
	 */
	private final String textureDir;
	/**
	 * The <code>ClassLoader</code> instance.
	 */
	private final ClassLoader loader;

	/**
	 * Constructor of <code>DataManager</code>.
	 */
	private DataManager() {
		super(EManager.DataManager);
		this.animationPool = new HashMap<EAnimation, JointAnimation>();
		this.meshPool = new HashMap<EEntity, SharedMesh>();
		this.characterPool = new HashMap<EEntity, ModelNode>();
		this.texturePool = new HashMap<ETexture, Texture>();
		this.worldDir = "com/sun/darkstar/example/snowman/data/world/";
		this.animationDir = "com/sun/darkstar/example/snowman/data/animation/";
		this.meshDir = "com/sun/darkstar/example/snowman/data/mesh/";
		this.characterDir = "com/sun/darkstar/example/snowman/data/mesh/character";
		this.systemDir = "com/sun/darkstar/example/snowman/data/system/";
		this.textureDir = "com/sun/darkstar/example/snowman/data/texture/";
		this.loader = this.getClass().getClassLoader();
		this.setupLocator();
	}
	
	/**
	 * Setup the <code>ResourceLocator</code> used to locate resource files
	 * for <code>MD5Importer</code>.
	 */
	private void setupLocator() {
		URL base = this.loader.getResource(this.textureDir);
		try {
			MultiFormatResourceLocator locator = new MultiFormatResourceLocator(base, ".tex");
			ResourceLocatorTool.addResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, locator);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retrieve the <code>DataManager</code> instance.
	 * @return The <code>DataManager</code> instance.
	 */
	public static DataManager getInstance() {
		if(DataManager.instance == null) {
			DataManager.instance = new DataManager();
		}
		return DataManager.instance;
	}
	
	/**
	 * Retrieve a world resource. The world resource is not cached or cloned.
	 * @param enumn The <code>EWorld</code> enumeration.
	 * @return The <code>IWorld</code> resource with given ID.
	 */
	public IWorld getWorld(EWorld enumn) {
		return (IWorld)this.getResource(this.worldDir + enumn.toString());
	}
	
	/**
	 * Retrieve a copy of the cached animation resource with given animation ID.
	 * @param enumn The <code>EAnimation</code> enumeration.
	 * @return The copy of the cached <code>JointAnimation</code> with given ID.
	 */
	public JointAnimation getAnimationData(EAnimation enumn) {
		JointAnimation animation = this.animationPool.get(enumn);
		if(animation == null) {
			animation = (JointAnimation)this.getResource(this.animationDir + enumn.toString());
			this.animationPool.put(enumn, animation);
		}
		return animation.clone();
	}
	
	/**
	 * Retrieve the cached static mesh resource with given entity ID.
	 * @param enumn The <code>EEntity</code> enumeration.
	 * @return The cached <code>SharedMesh</code> with given ID.
	 */
	public SharedMesh getMeshData(EEntity enumn) {
		SharedMesh mesh = this.meshPool.get(enumn);
		if(mesh == null) {
			mesh = (SharedMesh)this.getResource(this.meshDir + enumn.toString());
			this.meshPool.put(enumn, mesh);
		}
		return mesh;
	}
	
	/**
	 * Retrieve a copy of the cached character resource with given entity ID.
	 * @param enumn The <code>EEntity</code> enumeration.
	 * @return The copy of the cached character <code>ModelNode</code> with given ID.
	 */
	public ModelNode getCharacterData(EEntity enumn) {
		ModelNode character = this.characterPool.get(enumn);
		if(character == null) {
			character = (ModelNode)this.getResource(this.characterDir + enumn.toString());
			this.characterPool.put(enumn, character);
		}
		return character.clone();
	}
	
	/**
	 * Retrieve the URL points to the system data with given ID.
	 * @param enumn The <code>ESystemData</code> enumeration.
	 * @return The <code>URL</code> points to the system data with given ID.
	 */
	public URL getSystemData(ESystemData enumn) {
		return this.loader.getResource(this.systemDir + enumn.toString());
	}
	
	/**
	 * Retrieve the cached texture resource with given texture ID.
	 * @param enumn The <code>ETexture</code> enumeration.
	 * @return The cached <code>Texture</code> with given ID.
	 */
	public Texture getTextureData(ETexture enumn) {
		Texture texture = this.texturePool.get(enumn);
		if(texture == null) {
			URL url = this.loader.getResource(this.textureDir + enumn.toString());
			texture = TextureManager.loadTexture(url, Texture.MinificationFilter.Trilinear, Texture.MagnificationFilter.Bilinear, 16, true);
			// Maintain a reference copy for future changes and fast retrieving.
			this.texturePool.put(enumn, texture);
		}
		return texture;
	}
	
	/**
	 * Retrieve a binary resource which is pointed by the given path.
	 * @param path The path to the resource in <code>String</code> form.
	 * @return The loaded <code>Savable</code> resource.
	 */
	private Savable getResource(String path) {
		URL url = this.loader.getResource(path);
		try {
			return BinaryImporter.getInstance().load(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Clear all the cached resources.
	 */
	@Override
	public void cleanup() {
		this.animationPool.clear();
		this.meshPool.clear();
		this.characterPool.clear();
		this.texturePool.clear();
		TextureManager.clearCache();
	}
}
