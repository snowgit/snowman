package com.sun.darkstar.example.tool;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.concurrent.Callable;

import javax.swing.event.MouseInputAdapter;

import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.system.DisplaySystem;
import com.jme.util.GameTaskQueueManager;
import com.sun.darkstar.example.snowman.game.util.CollisionManager;
import com.sun.darkstar.example.tool.WorldEditor.ModeEnum;

public class WorldEditorMouseListener extends MouseInputAdapter {

	private final Vector2f screencoords;
	private final Vector3f worldcoords;
	private final Vector3f cameraLocation;
	private final Camera cam;
	private final Ray ray;
	private final Vector3f intersection;
	private final WorldEditor editor;
	private ModeEnum mode;

	public WorldEditorMouseListener(WorldEditor editor) {
		this.screencoords = new Vector2f();
		this.worldcoords = new Vector3f();
		this.cameraLocation = new Vector3f();
		this.cam = DisplaySystem.getDisplaySystem().getRenderer().getCamera();
		this.ray = new Ray();
		this.intersection = new Vector3f();
		this.editor = editor;
	}

	@SuppressWarnings("unchecked")
	public void mousePressed(MouseEvent e) {
		if(this.editor.isPressed()) return;
		else if(e.getButton() == 3) {
			GameTaskQueueManager.getManager().update(new Callable(){

				@Override
				public Object call() throws Exception {
					editor.setPressed(true);
					return null;
				}
			
			});
		}
	}

	@SuppressWarnings("unchecked")
	public void mouseReleased(MouseEvent e) {
		if(!this.editor.isPressed()) return;
		else if(e.getButton() == 3) {
			GameTaskQueueManager.getManager().update(new Callable(){

				@Override
				public Object call() throws Exception {
					editor.setPressed(false);
					return null;
				}
			
			});
		}
	}

	@SuppressWarnings("unchecked")
	public void mouseWheelMoved(MouseWheelEvent e){
		if(this.mode == ModeEnum.Raise || this.mode == ModeEnum.Lower || this.mode == ModeEnum.Smooth
				 || this.mode == ModeEnum.Paint || this.mode == ModeEnum.Erase) {
			final MouseWheelEvent event = e; 
			GameTaskQueueManager.getManager().update(new Callable(){

				@Override
				public Object call() throws Exception {
					float percentage = event.getWheelRotation()/100.0f;
					float radius = editor.getBrush().getRadius()*(1-percentage);
					if(radius > 5) radius = 5;
					else if(radius < 1) radius = 1;
					editor.getBrush().setRadius(radius);
					return null;
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	public void mouseMoved(MouseEvent e){
		if(this.mode == ModeEnum.Raise || this.mode == ModeEnum.Lower || this.mode == ModeEnum.Smooth
				 || this.mode == ModeEnum.Paint || this.mode == ModeEnum.Erase) {
			final MouseEvent event = e; 
			GameTaskQueueManager.getManager().update(new Callable(){

				@Override
				public Object call() throws Exception {
					screencoords.set(event.getX(), editor.getCanvas().getHeight()-event.getY());
					cam.getWorldCoordinates(screencoords, 1, worldcoords);
					cameraLocation.set(cam.getLocation());
					ray.getOrigin().set(cameraLocation);
					ray.getDirection().set(worldcoords.subtractLocal(cameraLocation).normalizeLocal());
					CollisionManager.getInstance().getIntersection(ray, editor.getWorld(), intersection, true);
					editor.getBrush().setLocalTranslation(intersection);
					return null;
				}

			});
		}
	}
	
	@SuppressWarnings("unchecked")
	public void mouseDragged(MouseEvent e){
		if(this.mode == ModeEnum.Raise || this.mode == ModeEnum.Lower || this.mode == ModeEnum.Smooth
				 || this.mode == ModeEnum.Paint || this.mode == ModeEnum.Erase) {
			final MouseEvent event = e; 
			GameTaskQueueManager.getManager().update(new Callable(){

				@Override
				public Object call() throws Exception {
					screencoords.set(event.getX(), editor.getCanvas().getHeight()-event.getY());
					cam.getWorldCoordinates(screencoords, 1, worldcoords);
					cameraLocation.set(cam.getLocation());
					ray.getOrigin().set(cameraLocation);
					ray.getDirection().set(worldcoords.subtractLocal(cameraLocation).normalizeLocal());
					CollisionManager.getInstance().getIntersection(ray, editor.getTerrain(), intersection, true);
					editor.getBrush().setLocalTranslation(intersection);
					return null;
				}

			});
		} else if(this.mode == ModeEnum.Move) {
			// TODO move objects.
		}
	}

	public void setMode(ModeEnum enumn) {
		this.mode = enumn;
	}
	
	public Vector3f getIntersection() {
		return this.intersection;
	}
}
