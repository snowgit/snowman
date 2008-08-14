package com.sun.darkstar.example.snowman.game.entity.view.scene;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;

/**
 * <code>FlagView</code> extends <code>DynamicView</code> to define the
 * view of a flag entity.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 08-12-2008 11:10 EST
 * @version Modified date: 08-12-2008 11:11 EST
 */
public class FlagView extends DynamicView {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 3748496583234718536L;

	/**
	 * Constructor of <code>FlagView</code>.
	 * @param entity The flag dynamic entity.
	 */
	public FlagView(IDynamicEntity entity) {
		super(entity);
		// FIXME Delete this when model is ready.
		Box b = new Box(this.entity.getEnumn().toString(), new Vector3f(), 0.2f, 0.2f, 0.2f);
		b.setModelBound(new BoundingBox());
		b.updateModelBound();
		this.attachChild(b);
	}

	@Override
	public void update(float interpolation) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public IDynamicEntity getEntity() {
		return (IDynamicEntity) this.entity;
	}
}
