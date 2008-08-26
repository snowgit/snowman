package com.sun.darkstar.example.tool;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.scene.Spatial;
import com.jmex.editors.swing.widget.VectorPanel;

public class SpatialEditPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private final Spatial _spat;

	public SpatialEditPanel(final Spatial spat) {
		_spat = spat;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// setup edit panels for scale, rotation and translation.
		final VectorPanel translationPanel = new VectorPanel(
				Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, 0.01f);
		translationPanel.setValue(_spat.getLocalTranslation().clone());
		translationPanel
				.setBorder(BorderFactory.createTitledBorder("Position"));
		translationPanel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				_spat.setLocalTranslation(translationPanel.getValue().clone());
			}
		});
		this.add(translationPanel);

		final JSlider rotationSlider = new JSlider(-180, 180, (int) (_spat
				.getLocalRotation().toAngles(null)[1] * FastMath.RAD_TO_DEG));
		rotationSlider.setBorder(BorderFactory.createTitledBorder("Rotation"));
		rotationSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				_spat.setLocalRotation(new Quaternion(new float[] { 0,
						rotationSlider.getValue() * FastMath.DEG_TO_RAD, 0 }));
			}
		});
		this.add(rotationSlider);

		float scale = _spat.getLocalScale().y;
		final JSlider scaleSlider = new JSlider(1, 100, (int) (scale * 10));
		final TitledBorder scaleBorder = BorderFactory
				.createTitledBorder("Scale: " + scale);
		scaleSlider.setBorder(scaleBorder);
		scaleSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				float newScale = scaleSlider.getValue() / 10f;
				_spat.getLocalTranslation().y /= _spat.getLocalScale().y;
				_spat.getLocalTranslation().y *= newScale;
				translationPanel.setValue(_spat.getLocalTranslation());
				_spat.setLocalScale(newScale);
				scaleBorder.setTitle("Scale: " + newScale);
			}
		});
		this.add(scaleSlider);
	}
}
