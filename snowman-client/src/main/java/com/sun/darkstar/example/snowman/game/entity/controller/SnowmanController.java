package com.sun.darkstar.example.snowman.game.entity.controller;

import com.jme.input.MouseInputListener;
import com.sun.darkstar.example.snowman.common.entity.enumn.EState;
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
 * @version Modified date: 08-06-2008 11:23 EST
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
		if(!this.isActive()) return;
		if(this.getEntity().getState() == EState.Attacking) return;
                if(this.getEntity().getState() == EState.Hit) return;
		if(button == 0 && pressed) {
			TaskManager.getInstance().createTask(ETask.UpdateCursorState, this.entity, x, y).execute();
			switch(this.getEntity().getCursorState()) {
                            case Invalid:
                                //do nothing
                                break;
                            case TryingToMove:
                                TaskManager.getInstance().createTask(ETask.MoveCharacter, this.entity, x, y);
                                break;
                            case Targeting:
                                TaskManager.getInstance().createTask(ETask.Attack, this.entity.getID(), this.getEntity().getTarget().getID());
                                break;
                            case TryingToGrab:
                                TaskManager.getInstance().createTask(ETask.Attach, this.getEntity().getTarget().getID(), this.entity.getID(), true);
                                break;
			}
		}
	}

	@Override
	public void onMove(int delta, int delta2, int newX, int newY) {
		if(!this.isActive()) return;
		TaskManager.getInstance().createTask(ETask.UpdateCursorState, this.entity, newX, newY);
	}

	@Override
	public void onWheel(int wheelDelta, int x, int y) {}
	
	@Override
	public SnowmanEntity getEntity() {
		return (SnowmanEntity)this.entity;
	}
}
