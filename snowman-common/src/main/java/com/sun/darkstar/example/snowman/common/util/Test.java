package com.sun.darkstar.example.snowman.common.util;

import com.jme.app.SimpleGame;
import com.sun.darkstar.example.snowman.common.util.enumn.EWorld;

public class Test extends SimpleGame {

	@Override
	protected void simpleInitGame() {
		this.rootNode.attachChild(SingletonRegistry.getDataImporter().getWorld(EWorld.Battle));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test test = new Test();
		test.setConfigShowMode(ConfigShowMode.AlwaysShow);
		test.start();
	}

}
