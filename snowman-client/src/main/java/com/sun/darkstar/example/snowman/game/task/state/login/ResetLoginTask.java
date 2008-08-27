package com.sun.darkstar.example.snowman.game.task.state.login;

import com.jmex.game.state.GameStateManager;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.LoginState;
import com.sun.darkstar.example.snowman.game.state.scene.login.LoginGUI;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>ResetLoginTask</code> extends <code>RealTimeTask</code> to reset the
 * login scene after an attempt to login failed.
 * <p>
 * <code>ResetLoginTask</code> execution logic:
 * 1. Reset <code>LoginGUI</code> status text.
 * 2. Reset <code>LoginGUI</code> button text.
 * 3. Enable <code>LoginGUI</code> failed text.
 * 4. Clear <code>LoginGUI</code> text fields.
 * 5. Enable all input.
 * <p>
 * <code>ResetLoginTask</code> does not have a more detailed 'equals'
 * comparison. All <code>ResetLoginTask</code> are considered 'equal',
 * therefore, a newer version can always replace the older one.
 * 
 * @author Yi Wang (Neakor)
 * @version Creation date: 07-10-2008 18:20 EST
 * @version Modified date: 07-16-2008 11:34 EST
 */
public class ResetLoginTask extends RealTimeTask {
    private String reason;

    public ResetLoginTask(Game game, String reason) {
        super(ETask.ResetLogin, game);
        this.reason = reason;
    }

    @Override
    public void execute() {
        final LoginGUI gui = ((LoginState) GameStateManager.getInstance().getChild(EGameState.LoginState.toString())).getGUI();
        gui.setStatus(gui.getFailedStatus()+" : "+reason);
    }
}
