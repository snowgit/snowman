package com.sun.darkstar.example.snowman.data.util.export;

import java.io.IOException;
import java.net.URL;

import com.jme.input.KeyBindingManager;
import com.model.md5.JointAnimation;
import com.model.md5.ModelNode;
import com.model.md5.importer.MD5Importer;
import com.sun.darkstar.example.snowman.data.enumn.EDataType;

/**
 * <code>AnimationExporter</code> is responsible for exporting all
 * character <code>JointAnimation</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-31-2008 11:35 EST
 * @version Modified date: 07-31-2008 12:00 EST
 */
public class AnimationExporter extends Exporter {
	/**
	 * The source model file name without extension.
	 */
	private final String fileName = "SnowManStanding";
	/**
	 * The <code>JointAnimation</code> to be exported.
	 */
	private JointAnimation animation;
	
	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		new AnimationExporter().start();
	}

	/**
	 * Constructor of <code>AnimationExporter</code>.
	 */
	public AnimationExporter() {
		super("com/sun/darkstar/example/snowman/data/animation/");
	}

	@Override
	protected void initialize() {
		final String name = this.fileName.substring(0, this.fileName.lastIndexOf("."));
		final URL mesh = this.getClass().getClassLoader().getResource(this.sourceDir + this.fileName + ".md5mesh");
		final URL anim = this.getClass().getClassLoader().getResource(this.sourceDir + this.fileName + ".md5anim");
		try {
			MD5Importer.getInstance().load(mesh, name, anim, name, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ModelNode model = MD5Importer.getInstance().getModelNode();
		model.setLocalScale(0.01f);
		this.rootNode.attachChild(model);
		this.animation = MD5Importer.getInstance().getAnimation();
		MD5Importer.getInstance().cleanup();
	}

	@Override
	protected void simpleUpdate() {
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("out", false)) {
			this.export(this.fileName + EDataType.Animation.getExtension(), this.animation);
		}
	}
}
