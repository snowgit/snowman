package com.sun.darkstar.example.snowman.game.gui;

import org.fenggui.Display;
import org.fenggui.render.lwjgl.LWJGLBinding;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.renderer.pass.Pass;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.sun.darkstar.example.snowman.game.input.enumn.EInputConverter;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;

/**
 * <code>GUIPass</code> renders {@link FengGUI} widgets in its own separate
 * <code>RenderPass</code> to enhance performance and scene graph management.
 * <p>
 * <code>GUIPass</code> has to be added to a <code>BasicPassManager</code>
 * after the root <code>RenderPass</code> and before any additional special
 * effect <code>RenderPass</code> such as <code>BloomRenderPass</code> or
 * <code>ShadowRenderPass</code>. 
 * <p>
 * Subclasses which extends <code>GUIPass</code> need to implement initialize
 * method to create all GUI widgets and add them to the <code>Display</code>.
 * <p>
 * <code>GUIPass</code> automatically invokes <code>IInputConverter</code> to
 * set the {@link FengGUI} <code>Display</code> instance for input conversion.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-21-2008 17:14 EST
 * @version Modified date: 07-09-2008 11:58 EST
 */
public abstract class GUIPass extends Pass {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = -284580225155562862L;
	/**
	 * The {@link FengGUI} <code>Display</code> instance.
	 */
	protected final Display display;
	/**
	 * The temporary <code>TextureState</code> to render FengGUI.
	 */
	private final TextureState tempTextureState;
	
	/**
	 * Constructor of <code>GUIPass</code>
	 */
	public GUIPass() {
		super();
		LWJGLBinding binding = new LWJGLBinding();
		binding.setUseClassLoader(true);
		this.display = new Display(binding);
		this.tempTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		Texture texture = TextureState.getDefaultTexture();
		texture.setScale(new Vector3f(1, 1, 1));
		this.tempTextureState.setTexture(texture);
	}
	
	/**
	 * Initialize the graphical user pass.
	 */
	public void initialize() {
		this.buildWidgets();
		InputManager.getInstance().getConverter(EInputConverter.KeyboardConverter).setDisplay(this.display);
		InputManager.getInstance().getConverter(EInputConverter.MouseConverter).setDisplay(this.display);
	}
	
	/**
	 * Build the graphical user interface widgets.
	 */
	protected abstract void buildWidgets();

	@Override
	protected void doRender(Renderer r) {
		this.tempTextureState.apply();
		this.display.display();
	}
	
	/**
	 * Retrieve the {@link FengGUI} <code>Display</code> instance.
	 * @return The {@link FengGUI} <code>Display</code> instance.
	 */
	public Display getDisplay() {
		return this.display;
	}
}
