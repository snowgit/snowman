package com.sun.darkstar.example.snowman.game.state.scene;

import com.jme.bounding.BoundingBox;
import com.jme.math.Vector3f;
import com.jme.scene.shape.Box;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;
import com.sun.darkstar.example.snowman.data.util.DataManager;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.state.GameState;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.world.World;

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
 * @version Modified date: 07-11-2008 12:25 EST
 */
public class BattleState extends GameState {

	/**
	 * Constructor of <code>BattleState</code>.
	 * @param game The <code>Game</code> instance.
	 */
	public BattleState(Game game) {
		super(EGameState.BattleState, game);
	}

	@Override
	protected void initializeState() {
		// TODO Auto-generated method stub
		this.buildWorld();
//		Box b = new Box("", new Vector3f(0, -10, -50), 5, 5, 5);
//		b.setModelBound(new BoundingBox());
//		b.updateModelBound();
//		this.rootNode.attachChild(b);
	}
	
	/**
	 * Build the battle field world.
	 */
	private void buildWorld() {
		this.rootNode.attachChild((World)DataManager.getInstance().getWorld(EWorld.Battle));
	}

	@Override
	protected void updateState(float interpolation) {
		// TODO Auto-generated method stub

	}
}
