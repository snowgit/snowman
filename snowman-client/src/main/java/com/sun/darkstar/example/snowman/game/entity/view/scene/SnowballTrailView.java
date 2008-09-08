package com.sun.darkstar.example.snowman.game.entity.view.scene;

import com.jme.scene.shape.Sphere;
import com.jme.system.DisplaySystem;
import com.jmex.effects.TrailMesh;
import com.jme.image.Texture;
import com.jme.scene.state.TextureState;
import com.jme.scene.state.BlendState;
import com.jme.util.TextureManager;
import com.sun.darkstar.example.snowman.game.entity.scene.SnowballTrailEntity;
import com.sun.darkstar.example.snowman.game.entity.view.DynamicView;
import com.sun.darkstar.example.snowman.common.interfaces.IDynamicEntity;

/**
 * <code>SnowballTrailView</code> extends <code>DynamicView</code> to define
 * the graphical presentation of a snow ball trail.
 * 
 * @author Owen Kellett
 */
public class SnowballTrailView extends DynamicView {
    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;
    
    private TrailMesh trail;
    private Sphere ball;
    
    private boolean started = false;

    /**
     * Constructor of <code>SnowballTrailView</code>.
     * @param entity The <code>SnowballTrailEntity</code>.
     */
    public SnowballTrailView(SnowballTrailEntity entity) {
        super(entity);
    }
    
    public void show(Sphere ball) {
        this.ball = ball;
        
        trail = new TrailMesh("TrailMesh", 5);
        trail.setUpdateSpeed(60.0f);
        trail.setFacingMode(TrailMesh.FacingMode.Billboard);
        trail.setUpdateMode(TrailMesh.UpdateMode.Step);
        
        TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
        ts.setEnabled(true);
        Texture t = TextureManager.loadTexture(
                getClass().getClassLoader().getResource(
                "com/sun/darkstar/example/snowman/data/texture/environment/snowballTrail.png"),
                Texture.MinificationFilter.Trilinear,
                Texture.MagnificationFilter.Bilinear);
        ts.setTexture(t);
        trail.setRenderState(ts);
        
        BlendState bs = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
        bs.setBlendEnabled(true);
        bs.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
        bs.setDestinationFunction(BlendState.DestinationFunction.One);
        bs.setTestEnabled(true);
        trail.setRenderState(bs);
        
        this.attachChild(trail);
        this.updateRenderState();
    }

    @Override
    public void update(float interpolation) {
        if(!started) {
            trail.resetPosition(ball.getWorldTranslation());
            started = true;
        }
        
        trail.setTrailFront(ball.getWorldTranslation(),
                    ball.getRadius()*1.5f, interpolation);
        trail.update(DisplaySystem.getDisplaySystem().getRenderer().getCamera().getLocation());
    }
    
    @Override
    public IDynamicEntity getEntity() {
        return (IDynamicEntity) this.entity;
    }
}
