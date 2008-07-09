package com.sun.darkstar.example.snowman.data.util.export;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.jme.input.KeyBindingManager;
import com.jme.scene.Node;
import com.jme.scene.SharedMesh;
import com.jme.scene.Spatial;
import com.jmex.model.collada.ColladaImporter;

/**
 * <code>ModelNodeExporter</code> is responsible for exporting all
 * static <code>SharedMesh</code> models.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-09-2008 17:20 EST
 * @version Modified date: 07-09-2008 17:41 EST
 */
public class ColladaExporter extends Exporter {
	/**
	 * The source model file name with extension.
	 */
	private final String inputFile = "Tree.dae";
	/**
	 * The output binary file with extension.
	 */
	private final String outputFile = "GoldCloud.model";
	/**
	 * The mesh object to be exported.
	 */
	private SharedMesh mesh;
	
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
		URL url = this.getClass().getClassLoader().getResource(this.sourceDir + this.inputFile);
		try {
			ColladaImporter.load(url.openStream(), "Model");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final ArrayList<SharedMesh> store = new ArrayList<SharedMesh>();
		this.populateMeshes(ColladaImporter.getModel(), store);
		if(store.size() > 1) System.out.println("There is more than one mesh.");
		// There should be only one mesh.
		this.mesh = store.get(0);
		this.rootNode.attachChild(this.mesh);
	}
	
	/**
	 * Populate the given list with shared meshes from the given spatial.
	 * @param spatial The <code>Spatial</code> to check <code>SharedMesh</code> from.
	 * @param store The <code>ArrayList</code> to store <code>SharedMesh</code>.
	 */
	private void populateMeshes(Spatial spatial, ArrayList<SharedMesh> store) {
		if(spatial == null) return;
		else if(spatial instanceof SharedMesh) {
			store.add((SharedMesh)spatial);
			return;
		} else if(spatial instanceof Node) {
			Node node = (Node)spatial;
			if(node.getChildren() == null) return;
			for(Spatial s : node.getChildren()) {
				this.populateMeshes(s, store);
			}
		}
	}
	
	@Override
	protected void simpleUpdate() {
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("out", false)) {
			this.export(this.outputFile, this.mesh);
		}
	}
}
