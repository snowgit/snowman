package com.sun.darkstar.example.tool;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ExportDialog extends JDialog{
	JCheckBox textureExport = new JCheckBox();
	JTextField fileNameField;
	Boolean canceled = false;
	private File file;
	private boolean hasFile;
	
	public ExportDialog(Frame parent){
		super(parent,"Export");
		setModal(true);
		setLayout(new BorderLayout());
		textureExport = new JCheckBox("Export Textures");
		JPanel jpanel = new JPanel();
		jpanel.setLayout(new GridLayout(2,1));
		jpanel.add(textureExport);
		JPanel jp2 = new JPanel();
		jp2.setLayout(new GridLayout(1,2));
		JButton exportButton = new JButton("Export");
		exportButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				canceled = false;
				setVisible(false);
			}});
		jp2.add(exportButton);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				canceled = true;
				setVisible(false);
			}});
		jp2.add(cancelButton);
		jpanel.add(jp2);
		add(jpanel,BorderLayout.SOUTH);
		jpanel = new JPanel();
		jpanel.setLayout(new BorderLayout());
		jpanel.add(new JLabel("File: "),BorderLayout.WEST);
		fileNameField = new JTextField();
		jpanel.add(fileNameField,BorderLayout.CENTER);
		JButton browseButton = new JButton("Browse");
		jpanel.add(browseButton,BorderLayout.EAST);
		add(jpanel,BorderLayout.NORTH);
		browseButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser("Pick a file");
				int retval = chooser.showSaveDialog(ExportDialog.this);
				if (retval == JFileChooser.APPROVE_OPTION){
					if(chooser.getSelectedFile() != null) {
						hasFile = true;
						setFileNameField(chooser.getSelectedFile().getName());
						file = chooser.getSelectedFile();
					} else hasFile = false;
				}
				
			}});
		pack();
		setSize(200,100);
		setLocation(new Point(parent.getWidth()/2,parent.getHeight()/2));
	}
	
	public boolean showDialog(){
		setVisible(true);
		return !canceled;
	}

	protected void setFileNameField(String name) {
		fileNameField.setText(name);
		fileNameField.repaint();
	}
	
	public String getFilenameField(){
		return fileNameField.getText();
	}
	
	public Boolean exportTextures(){
		return textureExport.isSelected();
	}

	public File getFile() {
		return this.file;
	}
	
	public boolean hasFile() {
		return this.hasFile;
	}
}
