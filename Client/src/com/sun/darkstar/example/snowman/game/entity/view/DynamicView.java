package com.sun.darkstar.example.snowman.game.entity.view;

import com.sun.darkstar.example.snowman.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.interfaces.IDynamicView;

/**
 * <code>DynamicView</code> extends <code>View</code> and implements
 * <code>IDynamicView</code> to represent a dynamic view for its base
 * <code>IDynamicEntity</code>.
 * <p>
 * <code>DynamicView</code> only defines an abstraction for its subclasses
 * that represent specific types of dynamic views. Subclasses need to
 * implement specific update logic.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-16-2008 16:27 EST
 * @version Modified date: 07-01-2008 17:12 EST
 */
public abstract class DynamicView extends View implements IDynamicView {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 5540885068355371725L;

	/**
	 * Constructor of <code>DynamicView</code>.
	 * @param entity The <code>IDynamicEntity</code> this view represents.
	 */
	public DynamicView(IDynamicEntity entity) {
		super(entity);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class getClassTag() {
		return DynamicView.class;
	}
}
