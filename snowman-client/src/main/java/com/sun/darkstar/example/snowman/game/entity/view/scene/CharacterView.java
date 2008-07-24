package com.sun.darkstar.example.snowman.game.entity.view.scene;

import com.jme.scene.Spatial;
import com.model.md5.ModelNode;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;

/**
 * <code>CharacterView</code> extends <code>DynamicView</code> to represent
 * a dynamic animated view of <code>CharacterEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-21-2008 14:54 EST
 * @version Modified date: 07-24-2008 11:45 EST
 */
public class CharacterView extends DynamicView {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1082825580187469809L;
	/**
	 * The <code>ModelNode</code> of this snowman view.
	 */
	private ModelNode model;
	
	/**
	 * Constructor of <code>CharacterView</code>.
	 * @param snowman The <code>CharacterEntity</code> instance.
	 */
	public CharacterView(CharacterEntity snowman) {
		super(snowman);
	}
	
	@Override
	public void attachMesh(Spatial mesh) {
		if(!(mesh instanceof ModelNode)) throw new IllegalArgumentException("Mesh is not a dynamic ModelNode.");
		super.attachMesh(mesh);
		this.model = (ModelNode)mesh;
	}

	@Override
	public void update(float interpolation) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Retrieve the dynamic mesh.
	 * @return The <code>ModelNode</code> instance.
	 */
	public ModelNode getMesh() {
		return this.model;
	}
}
