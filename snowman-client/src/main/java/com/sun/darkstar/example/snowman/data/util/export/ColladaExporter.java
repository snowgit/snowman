package com.sun.darkstar.example.snowman.data.util.export;

import java.io.IOException;
import java.net.URL;

import com.jme.input.KeyBindingManager;
import com.jme.scene.Node;
import com.jmex.model.collada.ColladaImporter;
import com.sun.darkstar.example.snowman.data.enumn.EDataType;

/**
 * <code>ModelNodeExporter</code> is responsible for exporting all
 * static <code>SharedMesh</code> models.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-09-2008 17:20 EST
 * @version Modified date: 07-31-2008 12:02 EST
 */
public class ColladaExporter extends Exporter {
	/**
	 * The source model file name without extension.
	 */
	private final String fileName = "SnowGlobe";
	/**
	 * The mesh object to be exported.
	 */
	private Node node;
	
	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		new ColladaExporter().start();
	}

	/**
	 * Constructor of <code>ColladaExporter</code>.
	 */
	public ColladaExporter() {
		super("com/sun/darkstar/example/snowman/data/mesh/");
	}
	
	@Override
	protected void initialize() {
		URL url = this.getClass().getClassLoader().getResource(this.sourceDir + this.fileName + ".dae");
		try {
			ColladaImporter.load(url.openStream(), "Model");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.node = ColladaImporter.getModel();
		this.node.setLocalScale(.1f);  // Our Collada files were done in 100:1 scale
		this.node.updateRenderState();
		this.rootNode.attachChild(this.node);
	}

	@Override
	protected void simpleUpdate() {
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("out", false)) {
			this.export(this.fileName + EDataType.StaticMesh.getExtension(), this.node);
		}
	}
}
