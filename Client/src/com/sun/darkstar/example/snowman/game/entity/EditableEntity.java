package com.sun.darkstar.example.snowman.game.entity;

import java.io.IOException;
import java.util.ArrayList;

import com.jme.util.export.InputCapsule;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.OutputCapsule;
import com.sun.darkstar.example.snowman.game.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.interfaces.IFinal;
import com.sun.darkstar.example.snowman.interfaces.IInfluence;
import com.sun.darkstar.example.snowman.interfaces.editable.IEditableEntity;

/**
 * <code>EditableEntity</code> extends <code>Entity</code> and implements
 * <code>IEditableEntity</code> to define the actual editable object
 * in the world during editing stages.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-01-2008 15:40 EST
 * @version Modified date: 07-01-2008 21:06 EST
 */
public class EditableEntity extends Entity implements IEditableEntity {
	/**
	 * The <code>ArrayList</code> of <code>IInfluence</code>.
	 */
	private ArrayList<IInfluence> influences;
	
	/**
	 * Constructor of <code>EditableEntity</code>.
	 */
	public EditableEntity() {
		super();
	}
	
	/**
	 * Constructor of <code>EditableEntity</code>.
	 * @param enumn The <code>EEntity</code> enumeration.
	 * @param id The integer ID number of this entity.
	 */
	public EditableEntity(EEntity enumn, int id) {
		super(enumn, id);
		this.influences = new ArrayList<IInfluence>();
	}

	@Override
	public IFinal constructFinal() {
		StaticEntity entity = new StaticEntity(this.getEnumn(), this.getID());
		entity.process(this);
		return entity;
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
	@SuppressWarnings("unchecked")
	public ArrayList<IInfluence> getInfluences() {
		return (ArrayList<IInfluence>)this.influences.clone();
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
