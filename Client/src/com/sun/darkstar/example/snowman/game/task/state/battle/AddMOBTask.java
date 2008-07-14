package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.sun.darkstar.example.snowman.common.protocol.enumn.EMOBType;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>AddMOBTask</code> extends <code>RealTimeTask</code> to create and
 * add a MOB to the <code>BattleState</code>.
 * <p>
 * <code>AddMOBTask</code> execution logic:
 * 1. Validate if <code>World</code> is initialized. If not, requeue the task.
 * 2. Convert <code>EMOBType</code> to <code>EEntity</code> enumeration.
 * 3. Create corresponding <code>DynamicEntity</code>.
 * 4. Create corresponding <code>DynamicView</code>.
 * 5. Attach the <code>DynamicView</code> to <code>World</code>.
 * <p>
 * <code>AddMOBTask</code> comparison is based on the given ID number and the
 * <code>EMOBType</code>. Two <code>AddMOBTask</code> are considered 'equal'
 * if and only if they have the same ID number and the <code>EMOBType</code>
 * enumeration.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-14-2008 16:30 EST
 * @version Modified date: 07-14-2008 16:30 EST
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
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object object) {
		if(!super.equals(object)) return false;
		AddMOBTask given = (AddMOBTask)object;
		return ((this.id == given.id) && (this.enumn == given.enumn));
	}
}
