package com.sun.darkstar.example.snowman.common.entity.view.terrain;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.CollisionTreeManager;
import com.jme.math.FastMath;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.TexCoords;
import com.jme.scene.lod.AreaClodMesh;
import com.jme.util.export.InputCapsule;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.OutputCapsule;
import com.jme.util.geom.BufferUtils;

/**
 * <code>TerrainMesh</code> extends <code>AreaClodMesh</code> to define the
 * most basic terrain view structure. It represents a single mesh view of
 * the entire terrain.
 * <p>
 * <code>TerrainMesh</code> provides level of detail and height manipulation
 * functionalities to the entire terrain during the world editing stages. It
 * is then attached to an <code>IStaticView</code> and locked during the world
 * export process.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-01-2008 14:24 EST
 * @version Modified date: 07-01-2008 16:59 EST
 */
public class TerrainMesh extends AreaClodMesh {
	/**
	 * Serial version.
	 */
	private static final long serialVersionUID = -8608506645496450894L;
	/**
	 * The size of this <code>TerrainMesh</code>.
	 */
	private int size;
	/**
	 * The offset of this <code>TerrainMesh</code> on the entire terrain.
	 */
	private Vector2f offset;
	/**
	 * The row index number in the parent <code>TerrainCluster</code>.
	 */
	private int row;
	/**
	 * The column index number in the parent <code>TerrainCluster</code>.
	 */
	private int col;
	/**
	 * The number <code>TerrainMesh</code> along x axis in the parent <code>TerrainCluster</code>.
	 */
	private int clusterWidth;
	/**
	 * The number <code>TerrainMesh</code> along z axis in the parent <code>TerrainCluster</code>.
	 */
	private int clusterDepth;
	/**
	 * The height values of this <code>TerrainMesh</code>.
	 */
	private float[][] heights;
	/**
	 * The flag indicates if level of detail data has been generated.
	 */
	private boolean lod;
	/**
	 * The temporary <code>Vector3f</code>.
	 */
	private final Vector3f tempVector;
	/**
	 * The temporary <code>Vector3f</code> of normal.
	 */
	private final Vector3f tempNormal;
	/**
	 * The temporary <code>Vector3f</code> of adjacent vertex.
	 */
	private final Vector3f tempAdjacent;
	/**
	 * The temporary <code>Vector3f</code> of opposite vertex.
	 */
	private final Vector3f tempOpposite;
	/**
	 * The temporary <code>ArrayList</code> of vertices for smoothing.
	 */
	private final ArrayList<Vector3f> tempValues;
	/**
	 * The temporary <code>ArrayList</code> of indices for smoothing.
	 */
	private final ArrayList<Integer> tempIndices;
	/**
	 * The temporary <code>ArrayList</code> of distances for smoothing.
	 */
	private final ArrayList<Float> tempDistances;

	/**
	 * Constructor of <code>TerrainMesh</code>.
	 */
	public TerrainMesh() {
		this.tempVector = new Vector3f();
		this.tempNormal = new Vector3f();
		this.tempAdjacent = new Vector3f();
		this.tempOpposite = new Vector3f();
		this.tempValues = new ArrayList<Vector3f>();
		this.tempIndices = new ArrayList<Integer>();
		this.tempDistances = new ArrayList<Float>();
	}

	/**
	 * Constructor of <code>TerrainMesh</code>.
	 * @param name The <code>String</code> name of this <code>TerrainMesh</code>.
	 * @param triangleCount The number of triangles this <code>TerrainMesh</code> should have.
	 * @param offset The offset of this <code>TerrainMesh</code> in the <code>TerrainCluster</code>.
	 */
	public TerrainMesh(String name, int triangleCount, Vector2f offset) {
		this(name, triangleCount, offset, 0, 0);
	}

	/**
	 * Constructor of <code>TerrainMesh</code>.
	 * @param name The <code>String</code> name of this <code>TerrainMesh</code>.
	 * @param triangleCount The number of triangles this <code>TerrainMesh</code> should have.
	 * @param offset The offset of this <code>TerrainMesh</code> in the <code>TerrainCluster</code>.
	 * @param row The row index of this <code>TerrainMesh</code> in the <code>TerrainCluster</code>.
	 * @param col The column index of this <code>TerrainMesh</code> in the <code>TerrainCluster</code>.
	 */
	public TerrainMesh(String name, int triangleCount, Vector2f offset, int row, int col) {
		this(name, triangleCount, offset, row, col, 1, 1);
	}
	
	/**
	 * Constructor of <code>TerrainMesh</code>.
	 * @param name The <code>String</code> name of this <code>TerrainMesh</code>.
	 * @param triangleCount The number of triangles this <code>TerrainMesh</code> should have.
	 * @param offset The offset of this <code>TerrainMesh</code> in the <code>TerrainCluster</code>.
	 * @param row The row index of this <code>TerrainMesh</code> in the <code>TerrainCluster</code>.
	 * @param col The column index of this <code>TerrainMesh</code> in the <code>TerrainCluster</code>.
	 * @param clusterWidth The width of the parent <code>TerrainCluster</code> in terms of number of meshes.
	 * @param clusterDepth The depth of the parent <code>TerrainCluster</code> in terms of number of meshes.
	 */
	public TerrainMesh(String name, int triangleCount, Vector2f offset, int row, int col, int clusterWidth, int clusterDepth) {
		super(name);
		this.size = (int)FastMath.ceil(FastMath.sqrt(triangleCount/2) + 1);
		if(offset == null) offset = new Vector2f(0, 0);
		this.offset = offset;
		this.row = row;
		this.col = col;
		this.clusterWidth = clusterWidth;
		this.clusterDepth = clusterDepth;
		this.heights = new float[this.size][this.size];
		this.tempVector = new Vector3f();
		this.tempNormal = new Vector3f();
		this.tempAdjacent = new Vector3f();
		this.tempOpposite = new Vector3f();
		this.tempValues = new ArrayList<Vector3f>();
		this.tempIndices = new ArrayList<Integer>();
		this.tempDistances = new ArrayList<Float>();
		this.buildMesh();
		this.setLocalTranslation(this.offset.x, 0, this.offset.y);
	}

	/**
	 * Build the actual mesh.
	 */
	private void buildMesh() {
		this.buildVertexBuffer();
		this.buildNormalBuffer();
		this.buildIndexBuffer();
		this.buildTextureBuffer();
		this.setModelBound(new BoundingBox());
		this.updateModelBound();
	}

	/**
	 * Build the vertex buffer.
	 */
	private void buildVertexBuffer() {
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(this.size*this.size*3);
		for (int z = 0; z < this.size; z++){
			for (int x = 0; x < this.size; x++){
				vertexBuffer.put((float)x);
				vertexBuffer.put(0);
				vertexBuffer.put((float)z);
			}
		}
		this.setVertexBuffer(vertexBuffer);
	}

	/**
	 * Build the normal buffer.
	 */
	private void buildNormalBuffer() {
		FloatBuffer normalBuffer = BufferUtils.createFloatBuffer(this.size*this.size*3);
		this.tempVector.set(0, 1, 0);
		for(int i = 0; i < this.getVertexCount(); i++) {
			BufferUtils.setInBuffer(this.tempVector, normalBuffer, i);
		}
		this.setNormalBuffer(normalBuffer);
	}

	/**
	 * Build the index buffer.
	 */
	private void buildIndexBuffer() {
		final int count = (this.size-1)*(this.size-1)*2;
		this.setTriangleQuantity(count);
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(count*3);
		for (int i = 0; i < (this.size * (this.size - 1)); i++) {
			// Skip the top row.
			if (i % ((this.size * (i / this.size + 1)) - 1) == 0 && i != 0) continue;
			// set the top left corner.
			indexBuffer.put(i);
			// set the bottom right corner.
			indexBuffer.put((1 + this.size) + i);
			// set the top right corner.
			indexBuffer.put(1 + i);
			// set the top left corner
			indexBuffer.put(i);
			// set the bottom left corner
			indexBuffer.put(this.size + i);
			// set the bottom right corner
			indexBuffer.put((1 + this.size) + i);
		}
		this.setIndexBuffer(indexBuffer);
	}

	/**
	 * Build the texture coordinates buffer.
	 */
	private void buildTextureBuffer() {
		FloatBuffer textureBuffer = BufferUtils.createVector2Buffer(this.getVertexCount());
		FloatBuffer vertexBuffer = this.getVertexBuffer();
		textureBuffer.clear();
		vertexBuffer.rewind();
		for (int i = 0; i < this.getVertexCount(); i++) {
			textureBuffer.put((vertexBuffer.get() + this.offset.x) / (this.clusterWidth*(this.size - 1)));
			vertexBuffer.get();
			textureBuffer.put((vertexBuffer.get() + this.offset.y) / (this.clusterDepth*(this.size - 1)));
		}
		this.setTextureCoords(new TexCoords(textureBuffer));
	}

	/**
	 * Raise or lower the height of this terrain mesh within the given radius.
	 * @param center The center position of modification in world coordinate system.
	 * @param radius The radius of modification.
	 * @param delta The delta of modification.
	 */
	public void modifyHeight(Vector3f center, float radius, float delta) {
		FloatBuffer vertexBuffer = this.getVertexBuffer();
		for(int i = 0; i < this.getVertexCount(); i++) {
			BufferUtils.populateFromBuffer(this.tempVector, vertexBuffer, i);
			this.localToWorld(this.tempVector, this.tempVector);
			float distance = this.getPlanarDistance(this.tempVector, center);
			if(distance < radius) {
				this.tempVector.y += delta * (1 - distance / radius);
			}
			this.worldToLocal(this.tempVector, this.tempVector);
			BufferUtils.setInBuffer(this.tempVector, vertexBuffer, i);
			this.heights[(int)this.tempVector.x][(int)this.tempVector.z] = this.tempVector.y;
		}
		this.updateNormalBuffer();
		CollisionTreeManager.getInstance().updateCollisionTree(this);
		this.updateGeometricState(0, false);
		this.updateModelBound();
	}

	/**
	 * Populate the vectors of affected vertices, the indices of the vertices and
	 * the distances between the vertices and the center based on given center and radius.
	 * @param center The center position of modification in world coordinate system.
	 * @param radius The radius of modification.
	 * @return The <code>ArrayList</code> of vertices <code>Vector3f</code> values.
	 */
	public ArrayList<Vector3f> populateVertices(Vector3f center, float radius) {
		this.tempValues.clear();
		this.tempIndices.clear();
		this.tempDistances.clear();
		FloatBuffer vertexBuffer = this.getVertexBuffer();
		for(int i = 0; i < this.getVertexCount(); i++) {
			BufferUtils.populateFromBuffer(this.tempVector, vertexBuffer, i);
			this.localToWorld(this.tempVector, this.tempVector);
			float distance = this.getPlanarDistance(this.tempVector, center);
			if(distance < radius) {
				this.tempValues.add(this.tempVector.clone());
				this.tempIndices.add(Integer.valueOf(i));
				this.tempDistances.add(Float.valueOf(distance));
			}
		}
		return this.tempValues;
	}

	/**
	 * Smooth the height of this terrain mesh towards the given average value.
	 * @param average The average value to smooth towards.
	 * @param radius The radius of modification.
	 * @param intensity The intensity of modification.
	 */
	public void smoothHeight(float average, float radius, float intensity) {
		FloatBuffer vertexBuffer = this.getVertexBuffer();
		Vector3f vector = null;
		for(int i = 0; i < this.tempValues.size(); i++) {
			vector = this.tempValues.get(i);
			float delta = (average - vector.y) * intensity * (1 - this.tempDistances.get(i).floatValue() / radius);
			vector.y += delta;
			this.worldToLocal(vector, vector);
			BufferUtils.setInBuffer(vector, vertexBuffer, this.tempIndices.get(i));
			this.heights[(int)vector.x][(int)vector.z] = vector.y;
		}
		this.updateNormalBuffer();
		CollisionTreeManager.getInstance().updateCollisionTree(this);
		this.updateGeometricState(0, false);
		this.updateModelBound();
	}
	
	/**
	 * Retrieve the planar distance between the given two vectors.
	 * @param vec1 The <code>Vector3f</code> to check.
	 * @param vec2 The <code>Vector3f</code> to check.
	 * @return The float planar distance between the given two vectors.
	 */
	private float getPlanarDistance(Vector3f vec1, Vector3f vec2) {
		float dx = vec1.x - vec2.x;
		float dy = vec1.z - vec2.z;
		return FastMath.sqrt(dx* dx + dy * dy);
	}

	/**
	 * Update the normal buffer.
	 */
	private void updateNormalBuffer() {
		FloatBuffer vertexBuffer = this.getVertexBuffer();
		FloatBuffer normalBuffer = this.getNormalBuffer();
		vertexBuffer.rewind();
		normalBuffer.rewind();
		int adjacent = 0, opposite = 0, normalIndex = 0;
		for(int y = 0; y < this.size; y++) {
			for(int x = 0; x < this.size; x++) {
				BufferUtils.populateFromBuffer(this.tempVector, vertexBuffer, normalIndex);
				if(y == this.size - 1) {
					// Up cross left.
					if(x == this.size - 1) {
						adjacent = normalIndex - this.size;
						opposite = normalIndex - 1;
						// Right cross up.
					} else {
						adjacent = normalIndex + 1;
						opposite = normalIndex - this.size;
					}
				} else {
					// Left cross down.
					if(x == this.size - 1) {
						adjacent = normalIndex - 1;
						opposite = normalIndex + this.size;
						// Down cross right.
					} else {
						adjacent = normalIndex + this.size;
						opposite = normalIndex + 1;
					}
				}
				BufferUtils.populateFromBuffer(this.tempAdjacent, vertexBuffer, adjacent);
				BufferUtils.populateFromBuffer(this.tempOpposite, vertexBuffer, opposite);
				this.tempNormal.set(this.tempAdjacent).subtractLocal(this.tempVector).crossLocal(this.tempOpposite.subtractLocal(this.tempVector)).normalizeLocal();
				BufferUtils.setInBuffer(this.tempNormal, normalBuffer, normalIndex);
				normalIndex++;
			}
		}
	}

	@Override
	public int chooseTargetRecord(Renderer r) {
		if(this.lod) return super.chooseTargetRecord(r);
		return 0;
	}

	/**
	 * Generate the level of detail data.
	 * @param trisPerPixel The number of triangles per pixels.
	 */
	public void generateLOD(float trisPerPixel) {
		this.setTrisPerPixel(trisPerPixel);
		this.create(null);
		this.lod = true;
	}

	/**
	 * Retrieve the size of this terrain mesh.
	 * @return The size of this <code>TerrainMesh</code>.
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Retrieve the row index number of this terrain mesh in the cluster.
	 * @return The row index number.
	 */
	public int getRowIndex() {
		return this.row;
	}

	/**
	 * Retrieve the column index number of this terrain mesh in the cluster.
	 * @return The column index number.
	 */
	public int getColumnIndex() {
		return this.col;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Class getClassTag() {
		return TerrainMesh.class;
	}

	@Override
	public void write(JMEExporter ex) throws IOException {
		super.write(ex);
		OutputCapsule oc = ex.getCapsule(this);
		oc.write(this.size, "Size", 0);
		oc.write(this.offset, "Offset", new Vector3f());
		oc.write(this.row, "RowIndex", 0);
		oc.write(this.col, "ColumnIndex", 0);
		oc.write(this.clusterWidth, "ClusterWidth", 1);
		oc.write(this.clusterDepth, "ClusterDepth", 1);
		oc.write(this.heights, "Heights", null);
		oc.write(this.lod, "LOD", false);
	}

	@Override
	public void read(JMEImporter im) throws IOException {
		super.read(im);
		InputCapsule ic = im.getCapsule(this);
		this.size = ic.readInt("Size", 0);
		this.offset = (Vector2f)ic.readSavable("Offset", new Vector3f());
		this.row = ic.readInt("RowIndex", 0);
		this.col = ic.readInt("ColumnIndex", 0);
		this.clusterWidth = ic.readInt("ClusterWidth", 1);
		this.clusterDepth = ic.readInt("ClusterDepth", 1);
		this.heights = ic.readFloatArray2D("Heights", null);
		this.lod = ic.readBoolean("LOD", false);
	}
}
