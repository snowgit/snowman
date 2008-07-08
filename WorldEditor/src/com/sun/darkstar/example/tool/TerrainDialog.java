package com.sun.darkstar.example.tool;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class TerrainDialog extends JDialog {
	private boolean doit = false;
	JSpinner xSpinner;
	JSpinner ySpinner;
	JSpinner trisPerMesh;
	public TerrainDialog(Frame parent){
		super(parent,"Set Terrain Size",true);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4,2));
		xSpinner = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
		ySpinner = new JSpinner(new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1));
		trisPerMesh = new JSpinner(new SpinnerNumberModel(2000,100,Integer.MAX_VALUE,100));
		panel.add(new JLabel("Width"));
		panel.add(xSpinner);
		panel.add(new JLabel("Depth"));
		panel.add(ySpinner);
		panel.add(new JLabel("Triangles per mesh"));
		panel.add(trisPerMesh);
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				doit = true;
				TerrainDialog.this.setVisible(false);
				
			}});
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				doit = false;
				TerrainDialog.this.setVisible(false);				
			}});
		panel.add(okButton);
		panel.add(cancelButton);
		setLayout(new BorderLayout());
		add(panel,BorderLayout.CENTER);
		setLocation(parent.getWidth()/2,parent.getHeight()/2);
		pack();
		setVisible(true);
	}
	
	public boolean wasCanceled(){
		return !doit;
	}
	
	public Dimension getTerrainSize(){
		return new Dimension((Integer)xSpinner.getModel().getValue(),
				(Integer)ySpinner.getModel().getValue());
	}
	
	public int getTrisPerMesh(){
		return (Integer)trisPerMesh.getModel().getValue();
	}
	
}
