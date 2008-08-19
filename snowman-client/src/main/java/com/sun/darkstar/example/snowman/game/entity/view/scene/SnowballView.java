package com.sun.darkstar.example.snowman.game.entity.view.scene;

import com.jme.scene.SharedMesh;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowballEntity;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;

/**
 * <code>SnowballView</code> extends <code>DynamicView</code> to define
 * the graphical presentation of a snow ball.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 16:48 EST
 * @version Modified date: 07-25-2008 16:50 EST
 */
public class SnowballView extends DynamicView {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = -370362057381803248L;

	/**
	 * Constructor of <code>SnowballView</code>.
	 * @param entity The <code>SnowballEntity</code>.
	 */
	public SnowballView(SnowballEntity entity) {
		super(entity);
		this.buildParticles();
	}
	
	@Override
	public void attachSpatial(Spatial mesh) {
		SharedMesh shared = new SharedMesh((TriMesh)mesh);
		this.attachChild(shared);
	}
	
	/**
	 * Build snow particles follow the snow ball.
	 */
	private void buildParticles() {
		// TODO
	}

	@Override
	public void update(float interpolation) {}
}
