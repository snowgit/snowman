package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.exception.DuplicatedIDException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;
import com.sun.darkstar.example.snowman.game.entity.view.View;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.state.scene.BattleState;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.interfaces.IController;
import com.sun.darkstar.example.snowman.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.interfaces.IEntity;
import com.sun.darkstar.example.snowman.interfaces.IView;

/**
 * <code>AddMOBTask</code> extends <code>RealTimeTask</code> to create and
 * add a MOB to the <code>BattleState</code>.
 * <p>
 * <code>AddMOBTask</code> execution logic:
 * 1. Convert <code>EMOBType</code> to <code>EEntity</code> enumeration.
 * 2. Create corresponding <code>DynamicEntity</code>.
 * 3. Create corresponding <code>DynamicView</code>.
 * 4. Set the x and z coordinates of the view.
 * 5. If it is controlled locally, create corresponding <code>IController</code>.
 * 6. If it is controlled locally, setup chase camera with battle state.
 * 7. Attach the <code>DynamicView</code> to <code>World</code>.
 * <p>
 * <code>AddMOBTask</code> comparison is based on the given ID number and the
 * <code>EMOBType</code>. Two <code>AddMOBTask</code> are considered 'equal'
 * if and only if they have the same ID number and the <code>EMOBType</code>
 * enumeration.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-14-2008 16:30 EST
 * @version Modified date: 07-17-2008 17:00 EST
 */
public class AddMOBTask extends RealTimeTask {
	/**
	 * The ID number of the entity to be added.
	 */
	private final int id;
	/**
	 * The <code>EMOBType</code> enumeration.
	 */
	private final EMOBType enumn;
	/**
	 * The flag indicates if this mob is controlled locally.
	 */
	private final boolean local;
	/**
	 * The x coordinate of the initial position.
	 */
	private final float x;
	/**
	 * The z coordinate of the initial position.
	 */
	private final float z;
	
	/**
	 * Constructor of <code>AddMOBTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param id The ID number of the entity to be added.
	 * @param enumn The <code>EMOBType</code> enumeration.
	 * @param local The flag indicates if this mob is controlled locally.
	 */
	public AddMOBTask(Game game, int id, EMOBType enumn, boolean local) {
		super(ETask.AddMOB, game);
		this.id = id;
		this.enumn = enumn;
		this.local = local;
		// TODO Set the default spawn position.
		this.x = 0;
		this.z = 0;
	}
	
	/**
	 * Constructor of <code>AddMOBTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param id The ID number of the entity to be added.
	 * @param enumn The <code>EMOBType</code> enumeration.
	 * @param x The x coordinate of the initial position.
	 * @param z The z coordinate of the initial position.
	 */
	public AddMOBTask(Game game, int id, EMOBType enumn, float x, float z) {
		super(ETask.AddMOB, game);
		this.id = id;
		this.enumn = enumn;
		this.local = false;
		this.x = x;
		this.z = z;
	}

	@Override
	public void execute() {
		EEntity enumn = null;
		switch(this.enumn) {
		case SNOWMAN: enumn = EEntity.Snowman; break;
		case FLAG: enumn = EEntity.Flag; break;
		default: throw new IllegalArgumentException("Invalid entity type: " + this.enumn.toString());
		}
		try {
			IEntity entity = EntityManager.getInstance().createEntity(enumn, this.id);
			IView view = ViewManager.getInstance().createView(entity);
			((View)view).getLocalTranslation().x = this.x;
			((View)view).getLocalTranslation().z = this.z;
			if(this.local) {
				IController controller = InputManager.getInstance().getController((IDynamicEntity)entity);
				controller.setActive(true);
				InputManager.getInstance().registerController(controller);
				((BattleState)this.game.getActiveState()).initializeChaseCam((DynamicView)view);
			} else {
				// TODO Add some other type of controller that needs to be updated every frame to add
				// forces toward the destination.
			}
			view.attachTo(this.game.getActiveState().getWorld());
			this.game.getActiveState().getWorld().updateRenderState();
		} catch (DuplicatedIDException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object object) {
		if(!super.equals(object)) return false;
		AddMOBTask given = (AddMOBTask)object;
		return ((this.id == given.id) && (this.enumn == given.enumn));
	}
}
