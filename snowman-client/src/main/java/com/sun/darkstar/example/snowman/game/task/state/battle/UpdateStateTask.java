package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jme.system.DisplaySystem;
import com.sun.darkstar.example.snowman.common.entity.enumn.EState;
import com.sun.darkstar.example.snowman.common.entity.view.StaticView;
import com.sun.darkstar.example.snowman.common.util.CollisionManager;
import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.darkstar.example.snowman.common.world.World;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowmanEntity;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;
import com.sun.darkstar.example.snowman.game.entity.view.scene.CharacterView;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>UpdateStateTask</code> extends <code>RealTimeTask</code> to update
 * all the corresponding values associated with mouse position.
 * <p>
 * <code>UpdateStateTask</code> execution logic:
 * 1. Create a <code>Ray</code> goes into the screen based on mouse screen
 * coordinates.
 * 2. If the mouse is intersecting a <code>StaticView</code>:
 *    1. Change the <code>SnowmanEntity</code> state to idle.
 *    2. Change the mouse cursor to walking cursor.
 * 3. If the mouse is intersecting a <code>SnowmanView</code>:
 *    1. Check if the target is in range.
 *    2. Check if there is anything blocking the target.
 *    3. Change the <code>SnowmanEntity</code> state to targeting.
 *    4. Change the mouse cursor to targeting cursor.
 * 4. If the mouse is intersecting a <code>DynamicView</code>:
 *    1. Change the <code>SnowmanEntity</code> state to grabbing.
 *    2. Change the mouse cursor to grabbing cursor.
 * <p>
 * <code>UpdateStateTask</code> does not have detailed 'equal' comparison.
 * All instances of <code>UpdateStateTask</code> are considered 'equal'.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-18-2008 11:36 EST
 * @version Modified date: 07-25-2008 14:50 EST
 */
public class UpdateStateTask extends RealTimeTask {
	/**
	 * The <code>SnowmanEntity</code> instance.
	 */
	private final SnowmanEntity snowman;
	/**
	 * The new x screen coordinate of the mouse.
	 */
	private final int x;
	/**
	 * The new y screen coordinate of the mouse.
	 */
	private final int y;

	/**
	 * Constructor of <code>UpdateStateTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param snowman The <code>SnowmanEntity</code> to be updated.
	 * @param x The new x screen coordinate of the mouse.
	 * @param y The new y screen coordinate of the mouse.
	 */
	public UpdateStateTask(Game game, SnowmanEntity snowman, int x, int y) {
		super(ETask.UpdateState, game);
		this.snowman = snowman;
		this.x = x;
		this.y = y;
	}

	@Override
	public void execute() {
		DisplaySystem display = DisplaySystem.getDisplaySystem();
		CollisionManager collisionManager = SingletonRegistry.getCollisionManager();
		Vector3f worldCoords = new Vector3f();
		Vector3f camLocation = display.getRenderer().getCamera().getLocation().clone();
		display.getWorldCoordinates(new Vector2f(this.x, this.y), 1, worldCoords);
		Ray ray = new Ray();
		ray.setOrigin(camLocation);
		ray.setDirection(worldCoords.subtractLocal(camLocation).normalizeLocal());
		World world = this.game.getGameState(EGameState.BattleState).getWorld();
		Spatial result = collisionManager.getIntersectObject(ray, world, StaticView.class, false);
		if(result != null) {
			this.snowman.setState(EState.Idle);
			// TODO Change cursor to walking.
			return;
		}
		result = collisionManager.getIntersectObject(ray, world, CharacterView.class, false);
		if(result != null) {
			CharacterView view = (CharacterView)result;
			if(view.getEntity() == this.snowman) return;
			if(this.validateRange(result) && this.validateBlocking(result)) {
				this.snowman.setState(EState.Targeting);
				this.snowman.setTarget((CharacterEntity)view.getEntity());
				//System.out.println("Targeting");
				// TODO Change cursor to targeting.
			}
			return;
		}
		result = collisionManager.getIntersectObject(ray, world, DynamicView.class, false);
		if(result != null) {
			this.snowman.setState(EState.TryingToGrab);
			System.out.println("Grabbing");
			// TODO Change cursor to grabbing.
			return;
		}
	}
	
	/**
	 * Validate if the given target is within attack range.
	 * @param target The <code>Spatial</code> target to check.
	 * @return True if the given target is in range. False otherwise.
	 */
	private boolean validateRange(Spatial target) {
		float range = SingletonRegistry.getHPConverter().convertRange(this.snowman.getHP());
		try {
			float distance = this.getPlanarDistance((Spatial)ViewManager.getInstance().getView(this.snowman), target);
			if(range * range >= distance) return true;
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Retrieve the planar distance squared between the given spatials.
	 * @param spatial The starting <code>Spatial</code>.
	 * @param target The targeting <code>Spatial</code>.
	 * @return The planar distance squared between the given spatials.
	 */
	private float getPlanarDistance(Spatial spatial, Spatial target) {
		Vector3f start = spatial.getLocalTranslation();
		Vector3f end = target.getLocalTranslation();
		float dx = start.x - end.x;
		float dz = start.z - end.z;
		return (dx * dx) + (dz * dz);
	}
	
	/**
	 * Validate if the given target is blocked by a static entity.
	 * @param target The <code>Spatial</code> target to check.
	 * @return True if the target is valid. False otherwise.
	 */
	private boolean validateBlocking(Spatial target) {
		try {
			Spatial snowman = (Spatial) ViewManager.getInstance().getView(this.snowman);
			Vector3f start = snowman.getLocalTranslation();
			Vector3f end = target.getLocalTranslation();
			World world = this.game.getGameState(EGameState.BattleState).getWorld();
			return SingletonRegistry.getCollisionManager().validate(start.x, start.z, end.x, end.z, world);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}
