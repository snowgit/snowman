package com.sun.darkstar.example.snowman.game.util;

import java.util.ArrayList;

import com.jme.intersection.PickData;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.Ray;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;

/**
 * <code>CollisionManager</code> is a <code>Manager</code> that is responsible
 * for processing all collision detection tasks.
 * <p>
 * <code>CollisionManager</code> requires synchronized access. All invocations
 * of <code>CollisonManager</code> need to be mono-threaded.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-02-2008 24:26 EST
 * @version Modified date: 07-02-2008 10:59 EST
 */
public class CollisionManager {
	/**
	 * The <code>CollisionManager</code> instance.
	 */
	private static CollisionManager instance;
	/**
	 * The <code>TrianglePickResults</code> for object check.
	 */
	private final TrianglePickResults objectResults;
	/**
	 * The <code>TrianglePickResults</code> for intersection check.
	 */
	private final TrianglePickResults intersectionResults;
	/**
	 * The array of <code>Vector3f</code> for intersection check.
	 */
	private final Vector3f[] tempVertices;


	/**
	 * Constructor of <code>CollisionManager</code>.
	 */
	private CollisionManager() {
		this.objectResults = new TrianglePickResults();
		this.objectResults.setCheckDistance(true);
		this.intersectionResults = new TrianglePickResults();
		this.intersectionResults.setCheckDistance(true);
		this.tempVertices = new Vector3f[3];
	}

	/**
	 * Retrieve the <code>CollisionManager</code> instance.
	 * @return The <code>CollisionManager</code> instance.
	 */
	public static CollisionManager getInstance() {
		if(CollisionManager.instance == null) {
			CollisionManager.instance = new CollisionManager();
		}
		return CollisionManager.instance;
	}

	/**
	 * Retrieve the intersecting object under the given node.
	 * @param ray The <code>Ray</code> to check with.
	 * @param parent The parent <code>Node</code> to check against.
	 * @param reference The <code>Class</code> reference of the expected object.
	 * @return The <code>Spatial</code> that extends the given reference <code>Class</code>.
	 */
	@SuppressWarnings("unchecked")
	public Spatial getIntersectObject(Ray ray, Node parent, Class reference) {
		this.objectResults.clear();
		parent.findPick(ray, this.objectResults);
		for(int i = 0; i < this.objectResults.getNumber(); i++) {
			Spatial mesh = this.objectResults.getPickData(i).getTargetMesh();
			if(mesh.getClass().equals(reference)) return mesh;
			else {
				while(mesh.getParent() != null) {
					mesh = mesh.getParent();
					if(mesh == parent) return null; // TODO Should throw an exception here saying reached parent.
					else if(mesh.getClass().equals(reference)) return mesh;
				}
				// TODO Should throw an exception here saying that cannot find the referencing class.
			}
		}
		return null;
	}

	/**
	 * Retrieve the intersection point with the given ray and spatial in either
	 * world coordinate system or local coordinate system of the given spatial
	 * based on the given flag value. The intersection result is stored in the
	 * given vector and returned. If the given store is null, a new vector instance
	 * is created and returned with the intersection result.
	 * @param ray The <code>Ray</code> to check with.
	 * @param parent The parent <code>Spatial</code> to check against.
	 * @param store The <code>Vector3f</code> to store the intersection result in.
	 * @param local True if the intersection should be converted to local coordinate system of the parent.
	 * @return If hit, the <code>Vector3f</code> intersection is returned. Otherwise <code>null</code> is returned.
	 */
	public Vector3f getIntersection(Ray ray, Spatial parent, Vector3f store, boolean local) {
		if(store == null) store = new Vector3f();
		this.intersectionResults.clear();
		parent.findPick(ray, this.intersectionResults);
		boolean hit = false;
		if(this.intersectionResults.getNumber() > 0) {
			PickData data = this.intersectionResults.getPickData(0);
			ArrayList<Integer> triangles = data.getTargetTris();
			if(!triangles.isEmpty()) {
				TriMesh mesh = (TriMesh)data.getTargetMesh();
				mesh.getTriangle(triangles.get(0).intValue(), this.tempVertices);
				for(int j = 0; j < this.tempVertices.length; j++) {
					this.tempVertices[j].multLocal(mesh.getWorldScale());
					mesh.getWorldRotation().mult(this.tempVertices[j], this.tempVertices[j]);
					this.tempVertices[j].addLocal(mesh.getWorldTranslation());
				}
				hit = ray.intersectWhere(this.tempVertices[0], this.tempVertices[1], this.tempVertices[2], store);
				if(hit && local) {
					parent.worldToLocal(store, store);					
					return store;
				} else if(hit && !local) return store;
			}
		}
		return null;
	}
}
