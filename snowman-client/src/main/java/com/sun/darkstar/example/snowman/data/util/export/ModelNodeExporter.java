package com.sun.darkstar.example.snowman.data.util.export;

import java.io.IOException;
import java.net.URL;

import com.jme.input.KeyBindingManager;
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
	private final String fileName = "SnowManBlue";
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
		this.model.setLocalScale(0.01f);
		this.rootNode.attachChild(this.model);
		MD5Importer.getInstance().cleanup();
	}
	
	@Override
	protected void simpleUpdate() {
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("out", false)) {
			this.export(this.fileName + EDataType.DynamicMesh.getExtension(), this.model);
		}
	}
}
