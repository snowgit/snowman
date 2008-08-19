package com.sun.darkstar.example.snowman.game.entity.view.scene;

import com.jme.bounding.BoundingBox;
import com.jme.scene.shape.Cylinder;
import com.jme.scene.shape.Disk;
import com.jme.renderer.ColorRGBA;
import com.jme.math.Vector3f;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;
import com.sun.darkstar.example.snowman.common.entity.enumn.EEntity;

/**
 * <code>FlagGoalView</code> extends <code>DynamicView</code> to define the
 * view of a flag goal entity.
 * 
 * @author Owen Kellett
 */
public class FlagGoalView extends DynamicView {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of <code>FlagGoalView</code>.
	 * @param entity The flag goal dynamic entity.
	 */
	public FlagGoalView(IDynamicEntity entity, float radius, EEntity type) {
            super(entity);

            Cylinder goal = new Cylinder(this.entity.getEnumn().toString(), 30, 30, radius, 0.1f);
            Disk top = new Disk(this.entity.getEnumn().toString() + "Top", 30, 30, radius);
            goal.setLocalTranslation(0, 0.05f, 0);
            top.setLocalTranslation(0, 0.1f, 0);
            switch (type) {
                case FlagBlueGoal:
                    goal.setSolidColor(ColorRGBA.blue);
                    top.setSolidColor(ColorRGBA.blue);
                    break;
                case FlagRedGoal:
                    goal.setSolidColor(ColorRGBA.red);
                    top.setSolidColor(ColorRGBA.red);
                    break;
                default:
                    goal.setSolidColor(ColorRGBA.lightGray);
                    top.setSolidColor(ColorRGBA.lightGray);
            }
            goal.rotateUpTo(Vector3f.UNIT_Z);
            top.rotateUpTo(Vector3f.UNIT_Z);
            goal.setModelBound(new BoundingBox());
            goal.updateModelBound();
            this.attachChild(goal);
            this.attachChild(top);
    }

    @Override
    public void update(float interpolation) {

    }

    @Override
    public IDynamicEntity getEntity() {
        return (IDynamicEntity) this.entity;
    }
}

