package com.sun.darkstar.example.snowman.game.state.scene;

import com.jme.bounding.BoundingBox;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.system.DisplaySystem;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;
import com.sun.darkstar.example.snowman.common.world.World;
import com.sun.darkstar.example.snowman.data.util.DataManager;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;
import com.sun.darkstar.example.snowman.game.input.SnowmanCameraHandler;
import com.sun.darkstar.example.snowman.game.state.GameState;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;

/**
 * <code>BattleState</code> extends <code>GameState</code> to define the world
 * game state where the player interacts with other players in a snow ball
 * fight.
 * <p>
 * <code>BattleState</code> initializes by first loading the battle scene graph
 * then construct all the <code>IController</code> that is responsible for handling
 * user input events. It is also responsible for propagating the game update
 * invocation down to the <code>IController</code> and <code>IEntity</code> it
 * manages.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-11-2008 12:25 EST
 * @version Modified date: 08-11-2008 16:54 EST
 */
public class BattleState extends GameState {
	/**
	 * The number of added entities.
	 */
	private int count;
	/**
	 * Our camera handler.
	 */
	private SnowmanCameraHandler cameraHandler;
	
	/**
	 * Constructor of <code>BattleState</code>.
	 * @param game The <code>Game</code> instance.
	 */
	public BattleState(Game game) {
		super(EGameState.BattleState, game);
	}

	@Override
	protected void initializeWorld() {
		this.world = (World)DataManager.getInstance().getWorld(EWorld.Battle);
	}

	@Override
	protected void initializeState() {
	}

	@Override
	protected void updateState(float interpolation) {
		this.cameraHandler.update(interpolation);
	}
	
	/**
	 * Initialize a chase camera with the given dynamic view as target.
	 * @param view The target <code>DynamicView</code> instance.
	 */
	public void initializeCameraHandler(DynamicView view) {
		view.updateGeometricState(0, false);
		this.cameraHandler = new SnowmanCameraHandler(DisplaySystem.getDisplaySystem().getRenderer().getCamera(), view, this.game);
		Vector3f targetOffset = new Vector3f(0,0,0);
		targetOffset.setY(((BoundingBox)view.getWorldBound()).yExtent*1.5f);
		this.cameraHandler.setTargetOffset(targetOffset);
		Vector3f dir = view.getLocalRotation().mult(new Vector3f(0,0,-1));
		Vector3f store = new Vector3f();
		this.cameraHandler.setAzimuth(FastMath.cartesianToSpherical(dir, store).y);
	}
	
	/**
	 * Increment the number of entities have been added.
	 */
	public void incrementCount() {
		this.count++;
	}
	
	/**
	 * Retrieve the number of entities have been added.
	 * @return The number of entities have been added.
	 */
	public int getCount() {
		return this.count;
	}
}
