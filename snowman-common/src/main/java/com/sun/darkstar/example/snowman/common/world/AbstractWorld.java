package com.sun.darkstar.example.snowman.common.world;

import java.io.IOException;

import com.jme.scene.Node;
import com.jme.util.export.InputCapsule;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.OutputCapsule;
import com.sun.darkstar.example.snowman.common.interfaces.IAbstractWorld;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;

/**
 * <code>AbstractWorld</code> implements <code>IAbstractWorld</code> to define
 * the most basic abstraction of a world node.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 08-11-2008 15:12 EST
 * @version Modified date: 08-11-2008 16:27 EST
 */
public abstract class AbstractWorld extends Node implements IAbstractWorld {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * The <code>EWorld</code> enumeration.
	 */
	protected EWorld enumn;
	/**
	 * The root node of static entities.
	 */
	protected Node staticRoot;
	/**
	 * The root node of the terrain.
	 */
	protected Node terrainRoot;
	/**
	 * The root node of dynamic entities.
	 */
	protected Node dynamicRoot;
	
	/**
	 * Constructor of <code>EditableWorld</code>.
	 */
	protected AbstractWorld() {}
	
	/**
	 * Constructor of <code>AbstractWorld</code>.
	 * @param enumn The <code>EWorld</code> enumeration.
	 */
	protected AbstractWorld(EWorld enumn) {
		super(enumn.toString());
		this.enumn = enumn;
		this.staticRoot = new Node("StaticRoot");
		this.terrainRoot = new Node("TerrainRoot");
		this.dynamicRoot = new Node("DynamicRoot");
		this.attachChild(this.staticRoot);
		this.attachChild(this.terrainRoot);
		this.attachChild(this.dynamicRoot);
	}

	@Override
	public Node getStaticRoot() {
		return this.staticRoot;
	}
	
	public Node getTerrainRoot() {
		return this.terrainRoot;
	}

	@Override
	public Node getDynamicRoot() {
		return this.dynamicRoot;
	}

	@Override
	public EWorld getWorldEnumn() {
		return this.enumn;
	}
	
	@Override
	public void write(JMEExporter ex) throws IOException {
		super.write(ex);
		OutputCapsule oc = ex.getCapsule(this);
		oc.write(this.enumn.toString(), "Enumeration", null);
		oc.write(this.staticRoot, "StaticRoot", null);
		oc.write(this.terrainRoot, "TerrainRoot", null);
		oc.write(this.dynamicRoot, "DynamicRoot", null);
	}

	@Override
	public void read(JMEImporter im) throws IOException {
		super.read(im);
		InputCapsule ic = im.getCapsule(this);
		this.enumn = EWorld.valueOf(ic.readString("Enumeration", null));
		this.setName("World"+this.enumn.toString());
		this.staticRoot = (Node) ic.readSavable("StaticRoot", null);
		this.terrainRoot = (Node) ic.readSavable("TerrainRoot", null);
		this.dynamicRoot = (Node) ic.readSavable("DynamicRoot", null);
	}
}
