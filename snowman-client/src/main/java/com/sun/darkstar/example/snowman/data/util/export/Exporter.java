package com.sun.darkstar.example.snowman.data.util.export;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import com.jme.app.SimpleGame;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.util.export.Savable;
import com.jme.util.export.binary.BinaryExporter;
import com.jme.util.resource.ResourceLocatorTool;
import com.jme.util.resource.SimpleResourceLocator;

/**
 * <code>Exporter</code> defines the basic abstraction of all types of
 * exporters which outputs intermediate resource data into binary format.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-13-2008 10:39 EST
 * @version Modified date: 06-13-2008 10:47 EST
 */
public abstract class Exporter extends SimpleGame {
	/**
	 * The source data directory.
	 */
	protected final String sourceDir = "com/sun/darkstar/example/snowman/data/source/";
	/**
	 * The output data directory.
	 */
	private final String outputDir;
	
	/**
	 * Constructor of <code>Exporter</code>.
	 * @param output The output directory in <code>String</code> form.
	 */
	public Exporter(String output) {
		this.outputDir = output;
		this.samples = 4;
	}
	
	@Override
	protected void simpleInitGame() {
		this.setupHotkey();
		this.setupTextureKey();
		this.initialize();
	}
	
	/**
	 * Setup the export key input.
	 */
	private void setupHotkey() {
		KeyBindingManager.getKeyBindingManager().set("out", KeyInput.KEY_O);
	}

	/**
	 * Setup texture directory.
	 */
	private void setupTextureKey() {
		URL url = this.getClass().getClassLoader().getResource(this.sourceDir);
		try {
			ResourceLocatorTool.addResourceLocator(ResourceLocatorTool.TYPE_TEXTURE, new SimpleResourceLocator(url));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the data to be exported.
	 */
	protected abstract void initialize();

	/**
	 * Create a new binary file and export the resource into it.
	 * @param output The name with extension of the output file.
	 * @param object The <code>Savable</code> object to be exported.
	 */
	protected void export(String output, Savable object) {
		URL url = this.getClass().getClassLoader().getResource(this.outputDir);
		String raw = url.toString().replaceAll("%20", " ").replace("bin", "src");
		String path = raw.substring(raw.indexOf("/")+1, raw.length());
		File file = new File(path + output);
		try {
			file.createNewFile();
			BinaryExporter.getInstance().save(object, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
