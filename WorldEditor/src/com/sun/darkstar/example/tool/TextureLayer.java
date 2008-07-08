package com.sun.darkstar.example.tool;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;

import com.jme.image.Image;
import com.jme.image.Texture;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.PassNodeState;
import com.jme.scene.state.BlendState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jme.util.lwjgl.LWJGLTextureUpdater;

public class TextureLayer {
	private Texture colorMap;
	private Texture alphaMap;
	private String name;
	private float dx;
	private float dz;
	private PassNodeState pass;
	
	public TextureLayer(File color, File alpha, float xBound, float zBound){
		try {
			colorMap = TextureManager.loadTexture(color.toURI().toURL(),Texture.MinificationFilter.Trilinear,
					Texture.MagnificationFilter.Bilinear, 16, true);
			alphaMap = TextureManager.loadTexture(alpha.toURI().toURL(),Texture.MinificationFilter.Trilinear,
					Texture.MagnificationFilter.Bilinear, 16, true);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		name = color.getName();
		this.dx = xBound*2/(this.alphaMap.getImage().getWidth()-1);
		this.dz = zBound*2/(this.alphaMap.getImage().getHeight()-1);
	}
	
	public PassNodeState createPass(BlendState blend) {
		if(this.pass != null) return this.pass;
		this.pass = new PassNodeState();
		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		this.colorMap.setWrap(Texture.WrapMode.Repeat);
		this.colorMap.setApply(Texture.ApplyMode.Modulate);
		this.colorMap.setScale(new Vector3f(50, 50, 1));
		ts.setTexture(this.colorMap);
		this.pass.setPassState(ts);
		this.alphaMap.setWrap(Texture.WrapMode.Repeat);
		this.alphaMap.setApply(Texture.ApplyMode.Combine);
		this.alphaMap.setCombineFuncRGB(Texture.CombinerFunctionRGB.Replace);
		this.alphaMap.setCombineSrc0RGB(Texture.CombinerSource.Previous);
		this.alphaMap.setCombineOp0RGB(Texture.CombinerOperandRGB.SourceColor);
		this.alphaMap.setCombineFuncAlpha(Texture.CombinerFunctionAlpha.Replace);
		ts.setTexture(this.alphaMap, ts.getNumberOfSetTextures());
		this.pass.setPassState(blend);
		return this.pass;
	}
	
	public PassNodeState getPass() {
		return this.pass;
	}
	
	public void modifyAlpha(Vector3f intersection, float radius, float intensity) {
		Image image = this.alphaMap.getImage();
		ByteBuffer buffer = image.getData(0);
		buffer.rewind();
		for(int v = 0; v < image.getWidth(); v++) {
			for(int u = 0; u < image.getHeight(); u++) {
				float distance = this.getDistance(this.dx*u, intersection.x, this.dz*v, intersection.z);
				if(distance < radius) {
					float delta = intensity*(1-distance/radius);
					int pixel = v*image.getWidth() + u;
					int redIndex = pixel * 4;
					int greenIndex = pixel * 4 + 1;
					int blueIndex = pixel * 4 + 2;
					int alphaIndex = pixel * 4 + 3;
					buffer.put(redIndex, this.modifyByte(buffer.get(redIndex), delta));
					buffer.put(greenIndex, this.modifyByte(buffer.get(greenIndex), delta));
					buffer.put(blueIndex, this.modifyByte(buffer.get(blueIndex), delta));
					buffer.put(alphaIndex, this.modifyByte(buffer.get(alphaIndex), delta));
				}
			}
		}
		LWJGLTextureUpdater.updateTexture(this.alphaMap, buffer, image.getWidth(), image.getHeight(), image.getFormat());
	}

	private float getDistance(float x1, float x2, float y1, float y2) {
		float dx = x1 - x2;
		float dy = y1 - y2;
		return FastMath.sqrt((dx * dx) + (dy * dy));
	}

	/**
	 * Modify the given byte value with given delta float value.
	 * @param value The <code>byte</code> value to be modified.
	 * @param delta The <code>float</code> to be added to the byte.
	 * @return The modified <code>byte</code> result.
	 */
	private byte modifyByte(byte value, float delta) {
		float result = (float)(value & 0xFF) + delta;
		if(result < 0) result = 0;
		else if(result > 255) result = 255;
		return (byte)result;
	}
	
	public Texture getColor(){
		return colorMap;
	}
	
	public Texture getAlpha(){
		return alphaMap;
	}
	
	public String toString(){
		return name;
	}
}
