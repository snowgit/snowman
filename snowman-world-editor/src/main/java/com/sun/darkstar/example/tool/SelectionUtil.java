package com.sun.darkstar.example.tool;

import java.nio.FloatBuffer;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingVolume;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Line;
import com.jme.scene.Spatial;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.ZBufferState;
import com.jme.scene.state.BlendState.DestinationFunction;
import com.jme.scene.state.BlendState.SourceFunction;
import com.jme.scene.state.ZBufferState.TestFunction;
import com.jme.util.geom.BufferUtils;

/**
 * @author Joshua Slack
 */
public class SelectionUtil {

	private static Line outline = null;

	private final static float EDGE_LENGTH = .2f;

	public static void updateSelection(final Spatial selectedItem,
			final Renderer renderer) {
		if (outline == null) {
			makeOutline(renderer);
		}

		final BoundingVolume selBoundingVol = selectedItem.getWorldBound();
		if (selBoundingVol == null) return;
		if (selBoundingVol instanceof BoundingBox) {
			BoundingBox bb = (BoundingBox) selBoundingVol;
			outline.getLocalTranslation().set(bb.getCenter());
			outline.getLocalScale().set(bb.xExtent, bb.yExtent, bb.zExtent);
		} else {
			BoundingBox tempBB = new BoundingBox();
			tempBB.xExtent = 0;
			tempBB.yExtent = 0;
			tempBB.zExtent = 0;
			tempBB.setCenter(selBoundingVol.getCenter());
			tempBB.mergeLocal(selBoundingVol);

			outline.getLocalTranslation().set(tempBB.getCenter());
			outline.getLocalScale().set(tempBB.xExtent, tempBB.yExtent,
					tempBB.zExtent);
		}

		outline.updateWorldVectors();
	}

	public static void drawOutline(final Renderer r) {
		if (outline != null) {
			r.draw(outline);
		}
	}

	/**
   * 
   */
	private static void makeOutline(final Renderer renderer) {
		outline = new Line("outline");
		outline.setDefaultColor(ColorRGBA.blue.clone());
		outline.setAntialiased(true);
		outline.setLineWidth(2.5f);

		FloatBuffer outlineVerts = BufferUtils.createVector3Buffer(48);

		outlineVerts.put(1).put(1).put(1);
		outlineVerts.put(1 - EDGE_LENGTH).put(1).put(1);
		outlineVerts.put(1).put(1).put(1);
		outlineVerts.put(1).put(1 - EDGE_LENGTH).put(1);
		outlineVerts.put(1).put(1).put(1);
		outlineVerts.put(1).put(1).put(1 - EDGE_LENGTH);

		outlineVerts.put(1).put(1).put(-1);
		outlineVerts.put(1 - EDGE_LENGTH).put(1).put(-1);
		outlineVerts.put(1).put(1).put(-1);
		outlineVerts.put(1).put(1 - EDGE_LENGTH).put(-1);
		outlineVerts.put(1).put(1).put(-1);
		outlineVerts.put(1).put(1).put(EDGE_LENGTH - 1);

		outlineVerts.put(1).put(-1).put(1);
		outlineVerts.put(1 - EDGE_LENGTH).put(-1).put(1);
		outlineVerts.put(1).put(-1).put(1);
		outlineVerts.put(1).put(EDGE_LENGTH - 1).put(1);
		outlineVerts.put(1).put(-1).put(1);
		outlineVerts.put(1).put(-1).put(1 - EDGE_LENGTH);

		outlineVerts.put(1).put(-1).put(-1);
		outlineVerts.put(1 - EDGE_LENGTH).put(-1).put(-1);
		outlineVerts.put(1).put(-1).put(-1);
		outlineVerts.put(1).put(EDGE_LENGTH - 1).put(-1);
		outlineVerts.put(1).put(-1).put(-1);
		outlineVerts.put(1).put(-1).put(EDGE_LENGTH - 1);

		outlineVerts.put(-1).put(1).put(1);
		outlineVerts.put(EDGE_LENGTH - 1).put(1).put(1);
		outlineVerts.put(-1).put(1).put(1);
		outlineVerts.put(-1).put(1 - EDGE_LENGTH).put(1);
		outlineVerts.put(-1).put(1).put(1);
		outlineVerts.put(-1).put(1).put(1 - EDGE_LENGTH);

		outlineVerts.put(-1).put(1).put(-1);
		outlineVerts.put(EDGE_LENGTH - 1).put(1).put(-1);
		outlineVerts.put(-1).put(1).put(-1);
		outlineVerts.put(-1).put(1 - EDGE_LENGTH).put(-1);
		outlineVerts.put(-1).put(1).put(-1);
		outlineVerts.put(-1).put(1).put(EDGE_LENGTH - 1);

		outlineVerts.put(-1).put(-1).put(1);
		outlineVerts.put(EDGE_LENGTH - 1).put(-1).put(1);
		outlineVerts.put(-1).put(-1).put(1);
		outlineVerts.put(-1).put(EDGE_LENGTH - 1).put(1);
		outlineVerts.put(-1).put(-1).put(1);
		outlineVerts.put(-1).put(-1).put(1 - EDGE_LENGTH);

		outlineVerts.put(-1).put(-1).put(-1);
		outlineVerts.put(EDGE_LENGTH - 1).put(-1).put(-1);
		outlineVerts.put(-1).put(-1).put(-1);
		outlineVerts.put(-1).put(EDGE_LENGTH - 1).put(-1);
		outlineVerts.put(-1).put(-1).put(-1);
		outlineVerts.put(-1).put(-1).put(EDGE_LENGTH - 1);

		outline.setVertexBuffer(outlineVerts);
		outline.generateIndices();

		BlendState bState = renderer.createBlendState();
		bState.setEnabled(true);
		bState.setBlendEnabled(true);
		bState.setSourceFunction(SourceFunction.SourceAlpha);
		bState.setDestinationFunction(DestinationFunction.OneMinusSourceAlpha);
		outline.setRenderState(bState);

		ZBufferState zState = renderer.createZBufferState();
		zState.setWritable(false);
		zState.setEnabled(true);
		zState.setFunction(TestFunction.LessThanOrEqualTo);
		outline.setRenderState(zState);

		outline.updateRenderState();
	}
}
