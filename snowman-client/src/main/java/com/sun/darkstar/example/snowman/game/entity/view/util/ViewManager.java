package com.sun.darkstar.example.snowman.game.entity.view.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.sun.darkstar.example.snowman.data.util.DataManager;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowmanEntity;
import com.sun.darkstar.example.snowman.game.entity.view.EditableView;
import com.sun.darkstar.example.snowman.game.entity.view.StaticView;
import com.sun.darkstar.example.snowman.game.entity.view.scene.SnowmanView;
import com.sun.darkstar.example.snowman.game.entity.view.terrain.TerrainView;
import com.sun.darkstar.example.snowman.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.interfaces.IDynamicView;
import com.sun.darkstar.example.snowman.interfaces.IEntity;
import com.sun.darkstar.example.snowman.interfaces.IStaticEntity;
import com.sun.darkstar.example.snowman.interfaces.IView;
import com.sun.darkstar.example.snowman.interfaces.editable.IEditableEntity;
import com.sun.darkstar.example.snowman.unit.Manager;
import com.sun.darkstar.example.snowman.unit.enumn.EManager;

/**
 * <code>ViewManager</code> is a <code>Manager</code> that is responsible for
 * managing all the <code>IView</code> that represent the entities. It should
 * be updated by the <code>Game</code> inside the main game loop.
 * <p>
 * <code>ViewManager</code> is responsible for all aspects of view management
 * including view creation, retrieving, destruction and update.
 * <p>
 * <code>ViewManager</code> is invoked when an <code>IEntity</code> is created.
 * It then creates a new <code>IView</code> and invokes <code>DataManager</code>
 * to retrieve the geometry data for the new <code>IView</code>. It is also
 * invoked when an <code>IEntity</code> is destroyed to remove the corresponding
 * <code>IView</code>.
 * <p>
 * <code>ViewManager</code> maintains all the <code>IView</code> by its base
 * <code>IEntity</code> as the key. This allows the system to mark a particular
 * <code>IView</code> as dirty when its corresponding <code>IEntity</code> has
 * been modified. Since two <code>IEntity</code> are considered 'equal' only if
 * they have the same integer ID number, this helps <code>ViewManager</code> to
 * avoid <code>IView</code> mapping conflicts.
 * <p>
 * When an <code>IDynamicEntity</code> has been modified, its corresponding
 * <code>IDynamicView</code> needs to be marked as dirty with <code>ViewManager</code>.
 * <code>ViewManager</code> then buffers all the dirty <code>IDynamicView</code>
 * for update during the next cycle.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 06-16-2008 17:02 EST
 * @version Modified date: 07-01-2008 15:35 EST
 */
public class ViewManager extends Manager {
	/**
	 * The <code>ViewManager</code> instance.
	 */
	private static ViewManager instance;
	/**
	 * The <code>IView</code> pool.
	 */
	private final HashMap<IEntity, IView> views;
	/**
	 * The dirty <code>IDynamicView</code> buffer.
	 */
	private final ArrayList<IDynamicView> dirty;

	/**
	 * Constructor of <code>ViewManager</code>.
	 */
	private ViewManager() {
		super(EManager.ViewManager);
		this.views = new HashMap<IEntity, IView>();
		this.dirty = new ArrayList<IDynamicView>();
	}

	/**
	 * Retrieve the <code>ViewManager</code> instance.
	 * @return The <code>ViewManager</code> instance.
	 */
	public static ViewManager getInstance() {
		if(ViewManager.instance == null) {
			ViewManager.instance = new ViewManager();
		}
		return ViewManager.instance;
	}

	/**
	 * Update the <code>ViewManager</code> to update all the dirty views.
	 * @param interpolation The frame rate interpolation value.
	 */
	public void update(float interpolation) {
		for(IDynamicView view : this.dirty) {
			view.update(interpolation);
		}
		this.dirty.clear();
	}

	/**
	 * Register the given view with <code>ViewManager</code>. This method
	 * should only be invoked by <code>IWorld</code> when it is first loaded.
	 * @param view The <code>IView</code> to be registered.
	 * @return True if the given view is successfully registered. False otherwise.
	 */
	public boolean registerView(IView view) {
		final IEntity entity = view.getEntity();
		if(this.views.containsKey(entity)) {
			this.logger.info("View has already been registered.");
			return false;
		}
		this.views.put(view.getEntity(), view);
		return true;
	}

	/**
	 * Create a view represents the given entity.
	 * @param entity The <code>IEntity</code> that the new view represents.
	 * @return The newly created <code>IView</code>.
	 */
	public IView createView(IEntity entity) {
		if(this.views.containsKey(entity)) {
			this.logger.info("Entity already has a view.");
			return this.views.get(entity);
		}
		IView view = null;
		switch(entity.getEnumn()) {
		case Terrain: view = new TerrainView((IEditableEntity)entity); break;
		case Snowman:
			view = new SnowmanView((SnowmanEntity)entity);
			view.attachMesh(DataManager.getInstance().getDynamicMesh(entity.getEnumn()));
			break;
		default:
			switch(entity.getType()) {
			case Static:
				view = new StaticView((IStaticEntity)entity);
				view.attachMesh(DataManager.getInstance().getStaticMesh(entity.getEnumn()));
				((StaticView)view).lock();
				break;
			case Dynamic: break; // TODO
			case Editable:
				view = new EditableView((IEditableEntity)entity);
				view.attachMesh(DataManager.getInstance().getStaticMesh(entity.getEnumn()));
				break;
			}
			break;
		}
		this.views.put(entity, view);
		this.logger.info("Created " + entity.getType().toString() + " based on " + entity.getEnumn().toString() + "Entity");
		return view;
	}

	/**
	 * Remove the view represents the given entity.
	 * @param entity The <code>IEntity</code> the <code>IView</code> represents.
	 */
	public boolean removeView(IEntity entity) {
		final IView view = this.views.remove(entity);
		if(view == null) {
			this.logger.info("There is no view represents entity: " + entity.toString());
			return false;
		}
		view.detachFromParent();
		return true;
	}

	/**
	 * Mark the dynamic view represents the given dynamic entity for update.
	 * @param entity The <code>IDynamicEntity</code> has been modified.
	 * @throws ObjectNotFoundException If the view represents the given entity does not exist.
	 */
	public void markForUpdate(IDynamicEntity entity) throws ObjectNotFoundException {
		final IView view = this.getView(entity);
		if(view instanceof IDynamicView) {
			this.dirty.add((IDynamicView)view);
		} else {
			this.logger.info("Cannot update static view.");
		}
	}

	/**
	 * Retrieve the view that represents the given entity.
	 * @param entity The <code>IEntity</code> that the view represents.
	 * @return The <code>IView</code> that represents the given entity.
	 * @throws ObjectNotFoundException If the view represents the given entity does not exist.
	 */
	public IView getView(IEntity entity) throws ObjectNotFoundException {
		final IView view = this.views.get(entity);
		if(view == null) throw new ObjectNotFoundException("View of entity: " + entity.toString());
		return view;
	}

	/**
	 * Clear the <code>ViewManager</code> by first detaching all stored <code>IView</code>
	 * from the scene graph then remove their stored references.
	 */
	public void clear() {
		for(IView view : this.views.values()) {
			view.detachFromParent();
		}
		this.views.clear();
		this.dirty.clear();
	}

	/**
	 * Clean up <code>ViewManager</code> by removing all views and dirty views.
	 */
	@Override
	public void cleanup() {
		this.views.clear();
		this.dirty.clear();
	}
}
