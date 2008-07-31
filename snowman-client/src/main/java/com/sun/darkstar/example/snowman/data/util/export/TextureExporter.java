package com.sun.darkstar.example.snowman.data.util.export;

import java.net.URL;

import com.jme.bounding.BoundingBox;
import com.jme.image.Image;
import com.jme.image.Texture;
import com.jme.input.KeyBindingManager;
import com.jme.scene.shape.Quad;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;
import com.sun.darkstar.example.snowman.data.enumn.EDataType;

/**
 * <code>TextureExporter</code> exports image files into <code>Texture</code>
 * binary files.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-13-2008 10:47 EST
 * @version Modified date: 07-31-2008 12:03 EST
 */
public class TextureExporter extends Exporter {
	/**
	 * The source image file name with extension.
	 */
	private final String inputFile = "nicegrass.jpg";
	/**
	 * The <code>AlphaState</code> for transparency.
	 */
	private BlendState alpha;
	/**
	 * The <code>Texture</code> instance.
	 */
	private Image image;

	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		new TextureExporter().start();
	}
	
	/**
	 * Constructor of <code>TextureExporter</code>.
	 */
	public TextureExporter() {
		super("com/sun/darkstar/example/snowman/data/texture/");
	}

	@Override
	protected void initialize() {
		this.setupBlend();
		this.buildQuad();
	}

	/**
	 * Setup the <code>BlendState</code> for transparency.
	 */
	private void setupBlend() {
		this.alpha = this.display.getRenderer().createBlendState();
		this.alpha.setBlendEnabled(true);
		this.alpha.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
		this.alpha.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
		this.alpha.setTestEnabled(true);
		this.alpha.setTestFunction(BlendState.TestFunction.GreaterThan);
		this.alpha.setEnabled(true);
	}
	
	/**
	 * Build a <code>Quad</code> to preview the <code>Texture</code>.
	 */
	private void buildQuad() {
		Quad q = new Quad("Export", 20,	20);
		q.setModelBound(new BoundingBox());
		q.updateModelBound();
		q.setRenderState(this.alpha);
		URL url = this.getClass().getClassLoader().getResource(this.sourceDir + this.inputFile);
		Texture t = TextureManager.loadTexture(url, Texture.MinificationFilter.Trilinear, Texture.MagnificationFilter.Bilinear, 16, true);
		TextureState ts = this.display.getRenderer().createTextureState();
		ts.setTexture(t);
		q.setRenderState(ts);
		this.rootNode.attachChild(q);
		this.image = t.getImage();
	}

	@Override
	protected void simpleUpdate() {
		if(KeyBindingManager.getKeyBindingManager().isValidCommand("out", false)) {
			this.export(this.inputFile.substring(0, this.inputFile.lastIndexOf(".")) + EDataType.Texture.getExtension(), this.image);
		}
	}
}
