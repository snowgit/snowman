package com.sun.darkstar.example.snowman.game.world;

import java.io.IOException;
import java.util.ArrayList;

import com.jme.scene.Node;
import com.jme.util.export.InputCapsule;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.OutputCapsule;
import com.sun.darkstar.example.snowman.data.enumn.EWorld;
import com.sun.darkstar.example.snowman.game.entity.influence.util.InfluenceManager;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.interfaces.IInfluence;
import com.sun.darkstar.example.snowman.interfaces.IStaticEntity;
import com.sun.darkstar.example.snowman.interfaces.IStaticView;
import com.sun.darkstar.example.snowman.interfaces.IWorld;
import com.sun.darkstar.example.snowman.interfaces.editable.IEditable;
import com.sun.darkstar.example.snowman.interfaces.editable.IEditableView;

/**
 * <code>World</code> extends {@link jME} <code>Node</code> and implements
 * <code>IWorld</code> to define the actual data structure of a world in game.
 * <p>
 * <code>World</code> should only be created based on <code>EditableWorld</code>
 * during the world export stage by the world editor. It should be imported as a
 * <code>Savable</code> resource using <code>DataManager</code> at run time.
 * <p>
 * <code>World</code> can be directly attached to the scene graph after it is
 * initialized. World needs to be initialized to register all the entities,
 * views, and influences it maintains with the corresponding <code>Manager</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-17-2008 15:23 EST
 * @version Modified date: 07-01-2008 17:04 EST
 */
public class World extends Node implements IWorld {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 4623946654242838364L;
	/**
	 * The <code>EWorld</code> enumeration of this <code>World</code>.
	 */
	private EWorld enumn;
	/**
	 * The <code>ArrayList</code> of <code>IStaticView</code>.
	 */
	private ArrayList<IStaticView> views;
	
	/**
	 * Constructor of <code>World</code>.
	 */
	public World() {}
	
	/**
	 * Constructor of <code>World</code>.
	 * @param enumn The <code>EWorld</code> enumeration.
	 */
	public World(EWorld enumn) {
		super("World" + enumn.toString());
		this.enumn = enumn;
		this.views = new ArrayList<IStaticView>();
	}

	@Override
	public void register() {
		EntityManager entityManager = EntityManager.getInstance();
		ViewManager viewManager = ViewManager.getInstance();
		InfluenceManager influenceManager = InfluenceManager.getInstance();
		for(IStaticView view : this.views) {
			IStaticEntity entity = (IStaticEntity)view.getEntity();
			entityManager.registerEntity(entity);
			viewManager.registerView(view);
			for(IInfluence influence : entity.getInfluences()) {
				influenceManager.registerInfluence(influence);
			}
		}
	}

	@Override
	public void process(IEditable editable) {
		if(editable instanceof EditableWorld) {
			EditableWorld given = (EditableWorld)editable;
			for(IEditableView v : given.getViews()) {
				this.views.add((IStaticView)v.constructFinal());
			}
		}
	}

	@Override
	public EWorld getWorldEnumn() {
		return this.enumn;
	}
	
	@Override
	public void write(JMEExporter ex) throws IOException {
		super.write(ex);
		OutputCapsule oc = ex.getCapsule(this);
		oc.write(this.enumn.toString(), "Enumeration", null);
		oc.writeSavableArrayList(this.views, "Views", null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void read(JMEImporter im) throws IOException {
		super.read(im);
		InputCapsule ic = im.getCapsule(this);
		this.enumn = EWorld.valueOf(ic.readString("Enumeration", null));
		this.setName("World"+this.enumn.toString());
		this.views = ic.readSavableArrayList("Views", null);
	}
}
