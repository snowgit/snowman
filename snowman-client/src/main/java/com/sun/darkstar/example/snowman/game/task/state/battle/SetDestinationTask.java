package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jme.system.DisplaySystem;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.common.protocol.ClientProtocol;
import com.sun.darkstar.example.snowman.common.util.CollisionManager;
import com.sun.darkstar.example.snowman.common.util.SingletonRegistry;
import com.sun.darkstar.example.snowman.common.world.World;
import com.sun.darkstar.example.snowman.exception.ObjectNotFoundException;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.entity.scene.CharacterEntity;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>SetDestinationTask</code> extends <code>RealTimeTask</code> to
 * set the destination of the user controlled <code>CharacterEntity</code>.
 * <p>
 * <code>SetDestinationTask</code> execution logic:
 * 1. Find the click position based on screen coordinates.
 * 2. Send server a 'moveme' packet.
 * 3. Find and set the valid destination based on clicked position.
 * <p>
 * Two <code>SetDestinationTask</code> are considered 'equal' if and only
 * if both of them are setting on the same <code>CharacterEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-21-2008 15:58 EST
 * @version Modified date: 07-24-2008 16:33 EST
 */
public class SetDestinationTask extends RealTimeTask {
	/**
	 * The <code>CharacterEntity</code> to be set.
	 */
	private CharacterEntity character;
	/**
	 * The flag indicates if the entity being set is locally controlled.
	 */
	private boolean local;
	/**
	 * The x coordinate of the clicked screen position.
	 */
	private int x;
	/**
	 * The y coordinate of the clicked screen position.
	 */
	private int y;
	/**
	 * The x coordinate of the starting position.
	 */
	private float startX;
	/**
	 * The z coordinate of the starting position.
	 */
	private float startZ;
	/**
	 * The x coordinate of the destination position.
	 */
	private float endX;
	/**
	 * The z coordinate of the destination position.
	 */
	private float endZ;

	/**
	 * Constructor of <code>SetDestinationTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param character The <code>CharacterEntity</code> to be set.
	 * @param x The x coordinate of the clicked screen position.
	 * @param y The y coordinate of the clicked screen position.
	 */
	public SetDestinationTask(Game game, CharacterEntity character, int x, int y) {
		super(ETask.SetDestination, game);
		this.character = character;
		this.local = true;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor of <code>SetDestinationTask</code>.
	 * @param game The <code>Game</code> instance.
	 * @param id The ID number of the character entity.
	 * @param startX The x coordinate of the starting position.
	 * @param startZ The z coordinate of the starting position.
	 * @param endX The x coordinate of the destination position.
	 * @param endZ The z coordinate of the destination position.
	 */
	public SetDestinationTask(Game game, int id, float startX, float startZ, float endX, float endZ) {
		super(ETask.SetDestination, game);
		try {
			this.character = (CharacterEntity)EntityManager.getInstance().getEntity(id);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		this.local = false;
		this.startX = startX;
		this.startZ = startZ;
		this.endX = endX;
		this.endZ = endZ;
	}

	@Override
	public void execute() {
		if(this.local) this.setLocal();
		else this.setDistributed();
	}
	
	/**
	 * Set the destination of a locally controlled character.
	 */
	private void setLocal() {
		DisplaySystem display = DisplaySystem.getDisplaySystem();
		CollisionManager collisionManager = SingletonRegistry.getCollisionManager();
		Vector3f worldCoords = new Vector3f();
		display.getWorldCoordinates(new Vector2f(this.x, this.y), 1, worldCoords);
		Ray ray = new Ray();
		ray.setOrigin(display.getRenderer().getCamera().getLocation());
		ray.setDirection(worldCoords.subtractLocal(display.getRenderer().getCamera().getLocation()).normalizeLocal());
		World world = this.game.getGameState(EGameState.BattleState).getWorld();
		Vector3f click = collisionManager.getIntersection(ray, world, new Vector3f(), true);
		if(click == null) return;
		try {
			Spatial view = (Spatial)ViewManager.getInstance().getView(this.character);
			Vector3f local = view.getLocalTranslation();
			this.game.getClient().send(ClientProtocol.getInstance().createMoveMePkt(local.x, local.z, click.x, click.z));
			Vector3f destination = collisionManager.getDestination(local.x, local.z, click.x, click.z, world);
			this.character.setDestination(destination);
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Set the destination of a distributed character.
	 */
	private void setDistributed() {
		try {
			View view = (View) ViewManager.getInstance().getView(this.character);
			view.getLocalTranslation().x = this.startX;
			view.getLocalTranslation().z = this.startZ;
			this.character.setDestination(new Vector3f(this.endX, 0, this.endZ));
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean equals(Object object) {
		if(super.equals(object)) {
			if(object instanceof SetDestinationTask) {
				SetDestinationTask given = (SetDestinationTask)object;
				return given.character.equals(this.character);
			}
		}
		return false;
	}
}
