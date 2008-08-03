package com.sun.darkstar.example.snowman.common.entity.view;

import com.jme.scene.SharedMesh;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.sun.darkstar.example.snowman.common.interfaces.IEditableEntity;
import com.sun.darkstar.example.snowman.common.interfaces.IEditableView;
import com.sun.darkstar.example.snowman.common.interfaces.IFinal;
import com.sun.darkstar.example.snowman.common.interfaces.IStaticEntity;

/**
 * <code>EditableView</code> extends <code>View</code> and implements the
 * <code>IEditableView</code> interface to define the view for all types
 * of <code>IEditableEntity</code> utilized by the world editor during the
 * world editing stages.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-01-2008 15:40 EST
 * @version Modified date: 07-25-2008 16:55 EST
 */
public class EditableView extends View implements IEditableView {
	/**
	 * Serial version
	 */
	private static final long serialVersionUID = -4899825988268393506L;

	/**
	 * Constructor of <code>EditableView</code>.
	 */
	public EditableView() {
		super();
	}
	
	/**
	 * Constructor of <code>EditableView</code>.
	 * @param entity The <code>IEditableEntity</code> instance.
	 */
	public EditableView(IEditableEntity entity) {
		super(entity);
		this.buildAxisView();
		this.buildWireView();
	}
	
	@Override
	public void attachMesh(Spatial mesh) {
		SharedMesh shared = new SharedMesh((TriMesh)mesh);
		this.attachChild(shared);
	}
	
	/**
	 * Build the axis view used during editing.
	 */
	private void buildAxisView() {
		// TODO
	}
	
	/**
	 * Build the wire frame view used during editing.
	 */
	private void buildWireView() {
		// TODO
	}
	
	@Override
	public IFinal constructFinal() {
		IStaticEntity entity = (IStaticEntity)((IEditableEntity)this.getEntity()).constructFinal();
		StaticView view = new StaticView(entity);
		view.process(this);
		view.lock();
		return view;
	}

	@Override
	public void setAxisEnabled(boolean enabled) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWireEnabled(boolean enabled) {
		// TODO Auto-generated method stub

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class getClassTag() {
		return EditableView.class;
	}
}
