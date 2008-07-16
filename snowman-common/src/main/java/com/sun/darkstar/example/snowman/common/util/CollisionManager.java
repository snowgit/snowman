package com.sun.darkstar.example.snowman.common.util;

import java.util.ArrayList;

import com.jme.intersection.PickData;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.FastMath;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;

/**
 * <code>CollisionManager</code> is a <code>Manager</code> that is responsible
 * for processing all collision detection tasks.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-02-2008 24:26 EST
 * @version Modified date: 07-16-2008 11:40 EST
 */
public class CollisionManager {
	/**
	 * The <code>CollisionManager</code> instance.
	 */
	private static CollisionManager instance;

	/**
	 * Constructor of <code>CollisionManager</code>.
	 */
	private CollisionManager() {}

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
		TrianglePickResults results = new TrianglePickResults();
		results.setCheckDistance(true);
		parent.findPick(ray, results);
		for(int i = 0; i < results.getNumber(); i++) {
			Spatial mesh = results.getPickData(i).getTargetMesh();
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
		TrianglePickResults results = new TrianglePickResults();
		results.setCheckDistance(true);
		Vector3f[] vertices = new Vector3f[3];
		parent.findPick(ray, results);
		boolean hit = false;
		if(results.getNumber() > 0) {
			PickData data = results.getPickData(0);
			ArrayList<Integer> triangles = data.getTargetTris();
			if(!triangles.isEmpty()) {
				TriMesh mesh = (TriMesh)data.getTargetMesh();
				mesh.getTriangle(triangles.get(0).intValue(), vertices);
				for(int j = 0; j < vertices.length; j++) {
					vertices[j].multLocal(mesh.getWorldScale());
					mesh.getWorldRotation().mult(vertices[j], vertices[j]);
					vertices[j].addLocal(mesh.getWorldTranslation());
				}
				hit = ray.intersectWhere(vertices[0], vertices[1], vertices[2], store);
				if(hit && local) {
					parent.worldToLocal(store, store);					
					return store;
				} else if(hit && !local) return store;
			}
		}
		return null;
	}
	
	/**
	 * Validate the movement from the given position to the destination
	 * defined by the given displacement.
	 * @param position The <code>Vector3f</code> starting movement position in local
	 * coordinate system relative to the given <code>Spatial</code>.
	 * @param displacement The <code>Vector2f</code> planar displacement of movement
	 * in local coordinate system relative to the given <code>Spatial</code>.
	 * @param spatial The <code>Spatial</code> to check the movement on.
	 * @param angle The maximum allowed movement angle in degrees.
	 * @return True if this movement is valid. False otherwise.
	 */
	public boolean validate(Vector3f position, Vector2f displacement, Spatial spatial, float angle) {
		Vector3f destination = new Vector3f();
		Ray ray = new Ray();
		destination.set(position);
		destination.x += displacement.x;
		destination.y += 1000;
		destination.z += displacement.y;
		ray.setOrigin(destination);
		ray.setDirection(Vector3f.UNIT_Y.negate());
		Vector3f value = this.getIntersection(ray, spatial, destination, true);
		if(value == null) return false;
		if(FastMath.abs(destination.angleBetween(position)) > FastMath.abs(angle)) return false;
		else return true;
	}
}
