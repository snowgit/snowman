package com.sun.darkstar.example.snowman.data.util.export;

import java.io.IOException;
import java.net.URL;

import com.jme.input.KeyBindingManager;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.model.md5.ModelNode;
import com.model.md5.importer.MD5Importer;
import com.sun.darkstar.example.snowman.data.enumn.EDataType;

/**
 * <code>ModelNodeExporter</code> is responsible for exporting all
 * character animated <code>ModelNode</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-13-2008 11:35 EST
 * @version Modified date: 07-31-2008 11:59 EST
 */
public class ModelNodeExporter extends Exporter {
	/**
	 * The source model file name without extension.
	 */
	private final String fileName = "SnowManRed";
	/**
	 * The <code>ModelNode</code> to be exported.
	 */
	private ModelNode model;

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		new ModelNodeExporter().start();
	}

	/**
	 * Constructor of <code>ModelNodeExporter</code>.
	 */
	public ModelNodeExporter() {
		super("com/sun/darkstar/example/snowman/data/mesh/character/");
	}

	@Override
	protected void initialize() {
		final URL mesh = this.getClass().getClassLoader().getResource(this.sourceDir + this.fileName + ".md5mesh");
		final URL anim = this.getClass().getClassLoader().getResource(this.sourceDir + this.fileName + ".md5anim");
		try {
			MD5Importer.getInstance().load(mesh, this.fileName, anim, this.fileName, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.model = MD5Importer.getInstance().getModelNode();
		MaterialState ms = display.getRenderer().createMaterialState();
		ms.setAmbient(ColorRGBA.white);
		ms.setDiffuse(ColorRGBA.white);
		this.model.setRenderState(ms);
		setFullAmbient(this.model);
		this.model.updateRenderState();
		this.model.setLocalScale(0.01f);
		this.rootNode.attachChild(this.model);
		MD5Importer.getInstance().cleanup();
	}
	

	private void setFullAmbient(Spatial spat) {
		if (spat.getRenderState(RenderState.RS_MATERIAL) != null) {
			MaterialState ms = (MaterialState)spat.getRenderState(RenderState.RS_MATERIAL);
			ms.setAmbient(ColorRGBA.white.clone());
		}
		if (spat instanceof Node) {
			Node node = (Node)spat;
			for (int i = 0; i < node.getQuantity(); i++) {
				Spatial child = node.getChild(i);
				this.setFullAmbient(child);
			}
		}
	}
	
	@Override
	protected void simpleUpdate() {
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("out", false)) {
			this.export(this.fileName + EDataType.DynamicMesh.getExtension(), this.model);
		}
	}
}
