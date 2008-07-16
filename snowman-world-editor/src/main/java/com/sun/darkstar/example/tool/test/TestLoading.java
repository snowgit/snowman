package com.sun.darkstar.example.tool.test;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

import com.jme.app.SimpleGame;
import com.jme.scene.Node;
import com.jme.util.export.binary.BinaryImporter;

public class TestLoading extends SimpleGame {

	public static void main(String[] args) {
		TestLoading test = new TestLoading();
		test.setConfigShowMode(ConfigShowMode.AlwaysShow);
		test.start();
	}

	@Override
	protected void simpleInitGame() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(null);
		File file = chooser.getSelectedFile();
		if(file != null) {
			try {
				Node node = (Node)BinaryImporter.getInstance().load(file);
				this.rootNode.attachChild(node);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
