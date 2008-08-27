package com.sun.darkstar.example.snowman.game.entity.view.scene;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.MaterialState.ColorMaterial;
import com.jme.system.DisplaySystem;
import com.jme.renderer.ColorRGBA;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowballEntity;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;

/**
 * <code>SnowballView</code> extends <code>DynamicView</code> to define
 * the graphical presentation of a snow ball.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-25-2008 16:48 EST
 * @version Modified date: 07-25-2008 16:50 EST
 */
public class SnowballView extends DynamicView {
    /**
     * Serial version.
     */
    private static final long serialVersionUID = -370362057381803248L;
    
    private Sphere ball;

    /**
     * Constructor of <code>SnowballView</code>.
     * @param entity The <code>SnowballEntity</code>.
     */
    public SnowballView(SnowballEntity entity) {
        super(entity);
    }
    
    public void show() {
        ball = new Sphere("Snowball", 32, 32, 0.05f);
        ball.setSolidColor(ColorRGBA.white);
        ball.setModelBound(new BoundingSphere());
        ball.updateModelBound();
        MaterialState ms = DisplaySystem.getDisplaySystem().getRenderer().createMaterialState();
        ms.setColorMaterial(ColorMaterial.AmbientAndDiffuse);
        ball.setRenderState(ms);
        this.attachChild(ball);
        this.updateRenderState();
    }

    @Override
    public void update(float interpolation) {
    }
    
    @Override
    public IDynamicEntity getEntity() {
        return (IDynamicEntity) this.entity;
    }
}
