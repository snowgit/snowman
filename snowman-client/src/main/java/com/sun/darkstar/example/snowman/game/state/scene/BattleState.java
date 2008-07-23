package com.sun.darkstar.example.snowman.game.state.scene;

import com.jme.bounding.BoundingBox;
import com.jme.input.ChaseCamera;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.system.DisplaySystem;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;
import com.sun.darkstar.example.snowman.common.world.World;
import com.sun.darkstar.example.snowman.data.util.DataManager;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;
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
 * @version Modified date: 07-17-2008 16:59 EST
 */
public class BattleState extends GameState {
	/**
	 * The chase camera objet.
	 */
	private ChaseCamera chaseCam;

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
		// TODO Auto-generated method stub
	}

	@Override
	protected void updateState(float interpolation) {
		this.chaseCam.update(interpolation);
	}
	
	/**
	 * Initialize a chase camera with the given dynamic view as target.
	 * @param view The target <code>DynamicView</code> instance.
	 */
	public void initializeChaseCam(DynamicView view) {
		view.updateGeometricState(0, false);
		this.chaseCam = new ChaseCamera(DisplaySystem.getDisplaySystem().getRenderer().getCamera(), view);
		this.chaseCam.setMaintainAzimuth(true);
		this.chaseCam.getMouseLook().setLookMouseButton(1);
		this.chaseCam.setStayBehindTarget(true);
		this.chaseCam.setSpringK(25);
		this.chaseCam.setDampingK(10);
		Vector3f targetOffset = new Vector3f(0,0,0);
		targetOffset.setY(((BoundingBox)view.getWorldBound()).yExtent*0.2f);
		this.chaseCam.setTargetOffset(targetOffset);
		this.chaseCam.setActionSpeed(0.2f);
		this.chaseCam.getMouseLook().setMaxAscent(50 * FastMath.DEG_TO_RAD);
		this.chaseCam.getMouseLook().setMinAscent(-30 * FastMath.DEG_TO_RAD);
		this.chaseCam.getMouseLook().setMaxRollOut(5);
		this.chaseCam.getMouseLook().setMinRollOut(2);
		this.chaseCam.getMouseLook().setMouseRollMultiplier(4);
		this.chaseCam.setIdealSphereCoords(new Vector3f(5, 0, 40*FastMath.DEG_TO_RAD));
		this.active = true;
	}
}
