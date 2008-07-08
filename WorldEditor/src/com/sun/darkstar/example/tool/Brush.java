package com.sun.darkstar.example.tool;

import java.nio.FloatBuffer;

import com.jme.bounding.BoundingBox;
import com.jme.math.FastMath;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Line;
import com.jme.scene.Spatial;
import com.jme.scene.TexCoords;
import com.jme.util.geom.BufferUtils;
import com.jmex.terrain.TerrainPage;
import com.sun.darkstar.example.snowman.game.entity.view.terrain.TerrainMesh;

public class Brush extends Line {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = 5651976616233771466L;
	/**
	 * The fixed additional height above the terrain.
	 */
	private final float increase;
	/**
	 * The radial sample size of the brush.
	 */
	private final int radialSample;
	/**
	 * The radius of the brush.
	 */
	private float radius;
	/**
	 * The <code>ColorRGBA</code> of this brush.
	 */
	private ColorRGBA color;
	/**
	 * The intensity of modification on the terrain.
	 */
	private float intensity;
	/**
	 * The temporary <code>Vector3f</code> for updating vertices.
	 */
	private final Vector3f tempVertex;

	/**
	 * Constructor of <code>Brush</code>.
	 * @param radialSample The radial sample size.
	 * @param radius The radius of the brush.
	 * @param color The <code>ColorRGBA</code> of the brush.
	 */
	public Brush (int radialSample, float radius, ColorRGBA color) {
		super("Brush");
		this.increase = 0.1f;
		this.radialSample = radialSample;
		this.radius = radius;
		this.color = color;
		this.intensity = 0.05f;
		this.tempVertex = new Vector3f();
		this.buildMesh();
		this.setModes();
	}

	/**
	 * Build the mesh of the brush.
	 */
	private void buildMesh() {
		// Build vertices.
		Vector3f[] vertices = new Vector3f[this.radialSample * 2];
		float segment = 1.0f / (float)this.radialSample;
		float angle = 0;
		float x = 0;
		float y = 0;
		float z = 0;
		for(int i = 0; i < vertices.length; i++) {
			if(i != this.radialSample) angle = FastMath.TWO_PI * segment * (float)i;
			else angle = 0;
			x = this.radius * FastMath.cos(angle);
			y = 0.0f;
			z = this.radius * FastMath.sin(angle);
			vertices[i] = new Vector3f(x, y, z);
			if(i > 0 && i < this.radialSample) {
				vertices[i+1] = new Vector3f(x, y, z);
				i++;
			}
		}
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices);
		// Build normals.
		Vector3f[] normals = new Vector3f[vertices.length];
		for(int i = 1; i < normals.length; i++) {
			normals[i] = new Vector3f(0, 0, 1);
		}
		FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(normals);
		// Build color.
		ColorRGBA[] colors = new ColorRGBA[vertices.length];
		for(int i = 0; i < colors.length; i++) {
			colors[i] = this.color;
		}
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors);
		// Build the texture coordinates.
		Vector2f[] coords = new Vector2f[vertices.length];
		for(int i = 0; i < coords.length; i++) {
			coords[i] = new Vector2f(1, 0);
		}
		FloatBuffer textureBuffer = BufferUtils.createFloatBuffer(coords);
		// Construct everything.
		this.reconstruct(vertexBuffer, normalBuffer, colorBuffer, new TexCoords(textureBuffer));
		this.updateRenderState();
		this.updateGeometricState(0, false);
	}
	
	/**
	 * Set up the basic modes for the brush.
	 */
	private void setModes() {
		this.setAntialiased(true);
		this.setIsCollidable(false);
		this.setModelBound(new BoundingBox());
		this.updateModelBound();
		this.setSolidColor(this.color);
		this.setLightCombineMode(Spatial.LightCombineMode.Off);
		this.setTextureCombineMode(Spatial.TextureCombineMode.Replace);
		this.setRenderQueueMode(Renderer.QUEUE_SKIP);
		this.setCullHint(CullHint.Never);
	}
	
	/**
	 * Update the brush with new center position.
	 * @param center The center <code>Vector3f</code> position.
	 * @param mesh The <code>TerrainMesh</code> reference.
	 */
	public void update(Vector3f center, TerrainMesh mesh) {
		this.getLocalTranslation().set(center);
		this.getVertexBuffer().rewind();
		float newY = 0;
		float oldY = 0;
		for(int i = 0; i < this.getVertexCount(); i++) {
			BufferUtils.populateFromBuffer(this.tempVertex, this.getVertexBuffer(), i);
			newY = 2;// TODO mesh.getHeight(this.tempVert.x, this.tempVert.z);
			if(Float.isNaN(newY) || newY > this.radius + center.y) {
				this.tempVertex.y = oldY - center.y + this.increase;
			} else {
				this.tempVertex.y = newY - center.y + this.increase;
				oldY = newY;
			}
			BufferUtils.setInBuffer(this.tempVertex, this.getVertexBuffer(), i);
		}
	}
	
	public void update(Vector3f center, TerrainPage terrain) {
		this.setLocalTranslation(center);
		Vector3f[] vertices = BufferUtils.getVector3Array(this.getVertexBuffer());
		this.getVertexBuffer().rewind();
		float newY = 0;
		float oldY = 0;
		for(int i = 0; i < vertices.length; i++) {
			newY = terrain.getHeight(center.x + vertices[i].x, center.z + vertices[i].z);
			if(Float.isNaN(newY) || newY > this.radius + center.y) {
				vertices[i].y = oldY - center.y + this.increase;
			} else {
				vertices[i].y = newY - center.y + this.increase;
				oldY = newY;
			}
			this.getVertexBuffer().put(vertices[i].x).put(vertices[i].y).put(vertices[i].z);
		}
	}
	
	/**
	 * Set the radius of the brush.
	 * @param color The new float radius value.
	 */
	public void setRadius(float radius) {
		this.radius = radius;
		this.buildMesh();
		this.updateModelBound();
	}
	
	/**
	 * Set the modification intensity of the brush.
	 * @param intensity The positive intensity value to be set.
	 */
	public void setIntensity(float intensity) {
		this.intensity = FastMath.abs(intensity);
	}
	
	/**
	 * Set the color of the brush.
	 * @param color The new <code>ColorRGBA</code> value.
	 */
	public void setColor(ColorRGBA color) {
		this.color = color;
		this.buildMesh();
	}
	
	/**
	 * Retrieve the radius of the brush.
	 * @return The float radius value.
	 */
	public float getRadius() {
		return this.radius;
	}
	
	/**
	 * Retrieve the modification intensity of the brush.
	 * @return The float intensity value.
	 */
	public float getIntensity() {
		return this.intensity;
	}
}
