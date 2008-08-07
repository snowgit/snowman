package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.common.interfaces.IEntity;
import com.sun.darkstar.example.snowman.common.interfaces.IView;
import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.common.protocol.enumn.ETeamColor;
import com.sun.darkstar.example.snowman.exception.DuplicatedIDException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.BattleState;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.interfaces.IController;

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
 * @version Modified date: 07-23-2008 17:41 EST
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
	 * The <code>ETeamColor</code> enumeration.
	 */
	private final ETeamColor color;
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
	 * @param color The <code>ETeamColor</code> enumeration.
	 * @param x The x coordinate of the initial position.
	 * @param z The z coordinate of the initial position.
	 * @param local The flag indicates if this mob is controlled locally.
	 */
	public AddMOBTask(Game game, int id, EMOBType enumn, ETeamColor color, float x, float z, boolean local) {
		super(ETask.AddMOB, game);
		this.id = id;
		this.enumn = enumn;
		this.color = color;
		this.x = x;
		this.z = z;
		this.local = local;
	}

	@Override
	public void execute() {
		EEntity enumn = null;
		switch(this.enumn) {
		case SNOWMAN: 
			if(this.local) {
				switch(this.color) {
				case Red: enumn = EEntity.SnowmanLocalRed; break;
				case Blue: enumn = EEntity.SnowmanLocalBlue; break;
				}
			} else {
				switch(this.color) {
				case Red: enumn = EEntity.SnowmanDistributedRed; break;
				case Blue: enumn = EEntity.SnowmanDistributedBlue; break;
				}
			}
			break;
		case FLAG:
			switch(this.color) {
			case Red: enumn = EEntity.FlagRed; break;
			case Blue: enumn = EEntity.FlagBlue; break;
			}
			break;
		default: throw new IllegalArgumentException("Invalid entity type: " + this.enumn.toString());
		}
		try {
			BattleState state = ((BattleState)this.game.getGameState(EGameState.BattleState));
			IEntity entity = EntityManager.getInstance().createEntity(enumn, this.id);
			if(entity == null) {
				state.incrementCount();
				return; // TODO A flag might be created.
			}
			IView view = ViewManager.getInstance().createView(entity);
			((View)view).getLocalTranslation().x = this.x;
			((View)view).getLocalTranslation().z = this.z;
			IController controller = InputManager.getInstance().getController((IDynamicEntity)entity);
			controller.setActive(true);
			if(this.local) {
				InputManager.getInstance().registerController(controller);
				state.initializeChaseCam((DynamicView)view);
			}
			view.attachTo(this.game.getGameState(EGameState.BattleState).getWorld());
			state.getWorld().updateRenderState();
			state.incrementCount();
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
