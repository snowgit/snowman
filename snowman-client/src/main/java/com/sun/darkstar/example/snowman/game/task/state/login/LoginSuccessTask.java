package com.sun.darkstar.example.snowman.game.task.state.login;

import com.jmex.game.state.GameStateManager;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.input.util.InputManager;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.LoginState;
import com.sun.darkstar.example.snowman.game.state.scene.login.LoginGUI;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;

/**
 * <code>LoginSuccessTask</code> extends <code>RealTimeTask</code> to notify
 * the user that the login has succeeded
 * <p>
 * <code>LoginSuccessTaskk</code> execution logic:
 * 1. Update LoginGUI with waiting status message
 * <p>
 * @author Owen Kellett
 */
public class LoginSuccessTask extends RealTimeTask {

    public LoginSuccessTask(Game game) {
        super(ETask.LoginSuccess, game);
    }

    @Override
    public void execute() {
        final LoginGUI gui = ((LoginState) GameStateManager.getInstance().getChild(EGameState.LoginState.toString())).getGUI();
        gui.setStatus(gui.getWaitingStatus());
    }
}


