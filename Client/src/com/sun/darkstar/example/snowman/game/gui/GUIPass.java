package com.sun.darkstar.example.snowman.game.gui;

import org.fenggui.Display;
import org.fenggui.render.lwjgl.LWJGLBinding;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.renderer.pass.Pass;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;

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
 * <code>GUIPass</code> is only responsible for rendering the GUI widgets.
 * Input conversion from @{@link jME} to {@link FengGUI} needs to be handled
 * separately.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 05-21-2008 17:14 EST
 * @version Modified date: 05-28-2008 13:54 EST
 */
public abstract class GUIPass extends Pass {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = -284580225155562862L;
	/**
	 * FengGUI <code>Display</code> instance.
	 */
	protected final Display display;
	/**
	 * Temporary <code>TextureState</code> to render FengGUI.
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
	 * Initialize the graphical user interface widgets.
	 */
	public abstract void initialize();

	@Override
	protected void doRender(Renderer r) {
		this.tempTextureState.apply();
		this.display.display();
	}
	
	/**
	 * Retrieve the GUI <code>Display</code> instance.
	 * @return The GUI <code>Display</code> instance.
	 */
	public Display getDisplay() {
		return this.display;
	}
}
