package com.sun.darkstar.example.snowman.data.util.export;

import java.io.IOException;
import java.net.URL;

import com.jme.input.KeyBindingManager;
import com.model.md5.ModelNode;
import com.model.md5.importer.MD5Importer;

/**
 * <code>ModelNodeExporter</code> is responsible for exporting all
 * character animated <code>ModelNode</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-13-2008 11:35 EST
 * @version Modified date: 06-17-2008 17:31 EST
 */
public class ModelNodeExporter extends Exporter {
	/**
	 * The source model file name with extension.
	 */
	private final String inputFile = "UpperDayCloud.md5mesh";
	/**
	 * The output binary file with extension.
	 */
	private final String outputFile = "UpperDayCloud.mdl";
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
		final URL md5mesh = this.getClass().getClassLoader().getResource(this.sourceDir + this.inputFile);
		final String name = this.inputFile.substring(0, this.inputFile.lastIndexOf("."));
		try {
			MD5Importer.getInstance().loadMesh(md5mesh, name);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.model = MD5Importer.getInstance().getModelNode();
		MD5Importer.getInstance().cleanup();
	}
	
	@Override
	protected void simpleUpdate() {
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("out", false)) {
			this.export(this.outputFile, this.model);
		}
	}
}
