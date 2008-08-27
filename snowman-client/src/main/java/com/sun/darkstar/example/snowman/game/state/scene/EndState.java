package com.sun.darkstar.example.snowman.game.state.scene;

import com.jme.input.MouseInput;
import com.jme.util.Timer;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.state.GameState;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.end.EndGUI;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.game.task.util.TaskManager;

/**
 * <code>EndState</code> extends <code>GameState</code> to define the end game
 * scene of the <code>Game</code>.
 * 
 * @author Owen Kellett
 */
public class EndState extends GameState {
	/**
	 * The <code>EndGUI</code> instance.
	 */
	private EndGUI gui;
        /**
         * Seconds to wait before restarting game
         */
        private final int seconds = 10;
        /**
         * Timer to keep track of countdown
         */
        private final Timer timer = Timer.getTimer();
        private int lastTime = 0;


	/**
	 * Constructor of <code>LoginState</code>.
	 * @param game The <code>Game</code> instance.
	 */
	public EndState(Game game) {
		super(EGameState.EndState, game);
	}

	@Override
	protected void initializeWorld() {
		//this.world = (World)DataManager.getInstance().getWorld(EWorld.Login);
	}

	@Override
	protected void initializeState() {
		this.buildGUIPass();
                this.timer.reset();
                this.lastTime = 0;
	}
	
	/**
	 * Build the GUI render pass.
	 */
	private void buildGUIPass() {
		MouseInput.get().setCursorVisible(true);
		this.gui = new EndGUI(seconds);
		this.gui.initialize();
		this.game.getPassManager().add(this.gui);
	}
	
	@Override
	protected void updateState(float interpolation) {
            int newTime = (int)timer.getTimeInSeconds();
            if(newTime > lastTime) {
                gui.setCountdown(seconds - newTime);
                lastTime = newTime;
                
                if(seconds - newTime == 0) {
                    this.setActive(false);
                    TaskManager.getInstance().createTask(ETask.Authenticate, String.valueOf(System.currentTimeMillis()), "");
                }
            }
	}
	
	/**
	 * Retrieve the <code>EndGUI</code> instance.
	 * @return The <code>EndGUI</code> instance.
	 */
	public EndGUI getGUI() {
		return this.gui;
	}
}

