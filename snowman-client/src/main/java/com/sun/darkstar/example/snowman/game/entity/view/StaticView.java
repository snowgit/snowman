package com.sun.darkstar.example.snowman.game.entity.view;

import com.jme.scene.Spatial;
import com.sun.darkstar.example.snowman.interfaces.IStaticEntity;
import com.sun.darkstar.example.snowman.interfaces.IStaticView;
import com.sun.darkstar.example.snowman.interfaces.editable.IEditable;

/**
 * <code>StaticView</code> extends <code>View</code> and implements
 * <code>IStaticView</code> to represent a static view for its base
 * <code>IStaticEntity</code>.
 * <p>
 * <code>StaticView</code> is constructed by <code>ViewManager</code>
 * when a <code>IStaticEntity</code> is created.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-16-2008 16:23 EST
 * @version Modified date: 07-07-2008 18:01 EST
 */
public class StaticView extends View implements IStaticView {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = -6155991300919576009L;

	/**
	 * Constructor of <code>StaticView</code>.
	 */
	public StaticView() {
		super();
	}
	
	/**
	 * Constructor of <code>StaticView</code>.
	 * @param entity The <code>IStaticEntity</code> this view represents. 
	 */
	public StaticView(IStaticEntity entity) {
		super(entity);
	}

	@Override
	public void process(IEditable editable) {
		if(editable instanceof EditableView) {
			EditableView given = (EditableView)editable;
			if(given.getChildren() == null) return;
			for(Spatial s : given.getChildren()) {
				this.attachChild(s);
			}
			this.setLocalTranslation(given.getLocalTranslation());
		}
	}

	@Override
	public void lock() {
		this.lockMeshes();
		this.lockBounds();
		this.lockTransforms();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class getClassTag() {
		return StaticView.class;
	}
}
