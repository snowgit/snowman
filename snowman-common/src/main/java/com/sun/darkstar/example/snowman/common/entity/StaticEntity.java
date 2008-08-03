package com.sun.darkstar.example.snowman.common.entity;

import java.io.IOException;
import java.util.ArrayList;

import com.jme.util.export.InputCapsule;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.OutputCapsule;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.common.interfaces.IEditable;
import com.sun.darkstar.example.snowman.common.interfaces.IEditableEntity;
import com.sun.darkstar.example.snowman.common.interfaces.IInfluence;
import com.sun.darkstar.example.snowman.common.interfaces.IStaticEntity;

/**
 * <code>StaticEntity</code> extends <code>Entity</code> and implements
 * <code>IStaticEntity</code> to represent an actual static entity in
 * the game world.
 * <p>
 * <code>StaticEntity</code> overrides binary import and export methods
 * to handle variables of <code>StaticEntity</code>. This allows binary
 * format importing and exporting of <code>StaticEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-06-2008 10:49 EST
 * @version Modified date: 07-01-2008 15:29 EST
 */
public class StaticEntity extends Entity implements IStaticEntity {
	/**
	 * The <code>ArrayList</code> of <code>IInfluence</code>.
	 */
	private ArrayList<IInfluence> influences;
	
	/**
	 * Constructor of <code>StaticEntity</code>.
	 */
	public StaticEntity() {
		super();
	}

	/**
	 * Constructor of <code>StaticEntity</code>.
	 * @param enumn The <code>EEntity</code> enumeration.
	 * @param id The integer ID number of this entity.
	 */
	public StaticEntity(EEntity enumn, int id) {
		super(enumn, id);
		this.influences = new ArrayList<IInfluence>();
	}

	@Override
	public void process(IEditable editable) {
		if(editable instanceof IEditableEntity) {
			IEditableEntity given = (IEditableEntity)editable;
			for(IInfluence influence : given.getInfluences()) {
				this.influences.add(influence);
			}
		}
	}

	@Override
	public boolean attachInfluence(IInfluence influence) {
		if(this.influences.contains(influence)) return false;
		this.influences.add(influence);
		return true;
	}

	@Override
	public boolean detachInfluence(IInfluence influence) {
		return this.influences.remove(influence);
	}

	@Override
	public void performInfluence(IDynamicEntity entity) {
		for(IInfluence influence : this.influences) {
			influence.perform(entity);
		}
	}

	@Override
	public ArrayList<IInfluence> getInfluences() {
		return this.influences;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class getClassTag() {
		return StaticEntity.class;
	}
	
	@Override
	public void write(JMEExporter ex) throws IOException {
		super.write(ex);
		OutputCapsule oc = ex.getCapsule(this);
		oc.writeSavableArrayList(this.influences, "Influences", null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void read(JMEImporter im) throws IOException {
		super.read(im);
		InputCapsule ic = im.getCapsule(this);
		this.influences = (ArrayList<IInfluence>)ic.readSavableArrayList("Influences", null);
	}
}
