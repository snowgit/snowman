package com.sun.darkstar.example.snowman.game.input.entity;

import com.jme.input.MouseInputListener;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowmanEntity;
import com.sun.darkstar.example.snowman.game.input.enumn.EInputType;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.game.task.util.TaskManager;

/**
 * <code>SnowmanController</code> extends <code>Controller</code> and
 * implements <code>MouseInputListener</code> to define the input handling
 * unit that is responsible for mapping user inputs to <code>ITask</code>
 * which performs modifications on the <code>SnowmanEntity</code>.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-18-2008 11:05 EST
 * @version Modified date: 07-25-2008 15:54 EST
 */
public class SnowmanController extends CharacterController implements MouseInputListener {

	/**
	 * Constructor of <code>SnowmanController</code>.
	 * @param entity The <code>SnowmanEntity</code> instance.
	 */
	public SnowmanController(SnowmanEntity entity) {
		super(entity, EInputType.Mouse);
	}

	@Override
	public void onButton(int button, boolean pressed, int x, int y) {
		if(button == 0 && pressed) {
			switch(((SnowmanEntity)this.entity).getState()) {
			case Idle: TaskManager.getInstance().createTask(ETask.SetDestination, this.entity, x, y); break;
			case Moving: TaskManager.getInstance().createTask(ETask.SetDestination, this.entity, x, y); break;
			case Targeting: TaskManager.getInstance().createTask(ETask.CreateSnowball, this.entity.getID(),
					((SnowmanEntity)this.entity).getTaregt().getID()); break;
			case Grabbing: break;
			}
		}
	}

	@Override
	public void onMove(int delta, int delta2, int newX, int newY) {
		TaskManager.getInstance().createTask(ETask.UpdateState, this.entity, newX, newY);
	}

	@Override
	public void onWheel(int wheelDelta, int x, int y) {}
}
