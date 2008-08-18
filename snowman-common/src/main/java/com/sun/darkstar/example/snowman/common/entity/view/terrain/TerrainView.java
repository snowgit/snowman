package com.sun.darkstar.example.snowman.common.entity.view.terrain;

import java.io.IOException;

import com.jme.scene.PassNode;
import com.jme.scene.PassNodeState;
import com.jme.util.export.InputCapsule;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;

import com.sun.darkstar.example.snowman.common.entity.terrain.TerrainEntity;
import com.sun.darkstar.example.snowman.common.entity.view.EditableView;
import com.sun.darkstar.example.snowman.common.interfaces.IEditableEntity;

/**
 * <code>TerrainView</code> extends <code>EditableView</code> to define the
 * view of the terrain during world editing stages. It maintains the base
 * <code>TerrainCluster</code> instance.
 * <p>
 * <code>TerrainView</code> internally maintains the <code>TerrainCluster</code>
 * with a <code>PassNode</code> that allows multiple render pass states to
 * enable texture splatting on the terrain.
 * 
 * @author Yi Wang (Neakor)
 * @author Tim Poliquin (Weenahmen)
 * @version Creation date: 07-01-2008 15:36 EST
 * @version Modified date: 07-07-2008 13:56 EST
 */
public class TerrainView extends EditableView {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 4544483836999026877L;
	/**
	 * The <code>TerrainCluster</code> instance.
	 */
	private TerrainCluster terrain;
	/**
	 * The <code>PassNode</code> of the terrain.
	 */
	private PassNode passNode;
	
	/**
	 * Constructor of <code>TerrainView</code>.
	 */
	public TerrainView() {
		super();
	}

	/**
	 * Constructor of <code>TerrainView</code>.
	 * @param entity The <code>IEditableEntity</code> instance.
	 */
	public TerrainView(IEditableEntity entity) {
		super(entity);
		TerrainEntity terrain = (TerrainEntity)entity;
		this.terrain = new TerrainCluster("Terrain", terrain.getWidth(), terrain.getDepth(), terrain.getTrianglesPerMesh());
		this.passNode = new PassNode("TerrainPassNode");
		this.passNode.attachChild(this.terrain);
		this.attachChild(this.passNode);
	}
	
	/**
	 * attach the given pass state to the terrain.
	 * @param pass The <code>PassNodeState</code> to be attached.
	 */
	public void attachPass(PassNodeState pass) {
		this.passNode.addPass(pass);
	}
	
	/**
	 * Detach the given pass state from the terrain.
	 * @param pass The <code>PassNodeState</code> to be detached.
	 * @return True if <code>PassNodeState</code> is detached. False otherwise.
	 */
	public boolean detachPass(PassNodeState pass) {
		return this.passNode.removePass(pass);
	}
	
	/**
	 * Retrieve the <code>TerrainCluster</code> instance.
	 * @return The <code>TerrainCluster</code> instance.
	 */
	public TerrainCluster getTerrainCluster() {
		return this.terrain;
	}

	@Override
	public void read(JMEImporter im) throws IOException {
		super.read(im);
		this.passNode = (PassNode) getChild("TerrainPassNode");
		this.terrain = (TerrainCluster) getChild("Terrain");
	}
}
