package com.sun.darkstar.example.snowman.game.task.state.battle;

import com.sun.darkstar.example.snowman.common.protocol.messages.ClientMessages;
import com.sun.darkstar.example.snowman.common.protocol.enumn.ETeamColor;
import com.sun.darkstar.example.snowman.common.util.enumn.EStats;
import com.sun.darkstar.example.snowman.common.interfaces.IEntity;
import com.sun.darkstar.example.snowman.common.entity.view.View;
import com.sun.darkstar.example.snowman.game.Game;
import com.sun.darkstar.example.snowman.game.task.RealTimeTask;
import com.sun.darkstar.example.snowman.game.task.enumn.ETask;
import com.sun.darkstar.example.snowman.game.state.enumn.EGameState;
import com.sun.darkstar.example.snowman.game.state.scene.BattleState;
import com.sun.darkstar.example.snowman.game.entity.util.EntityManager;
import com.sun.darkstar.example.snowman.game.entity.view.util.ViewManager;

/**
 * <code>ScoreTask</code> extends <code>RealTimeTask</code> to score a win
 * for the character.
 * <p>
 * <code>ScoreTask</code> execution logic:
 * 1. Check to see if the position is within the goal radius of the color's goal
 * 2. Send 'score' packet to server if check succeeds
 * 
 * @author Owen Kellett
 */
public class ScoreTask extends RealTimeTask {

    /**
     * The color of the scoring snowman
     */
    private ETeamColor color;
    /**
     * The x coordinate to score at.
     */
    private final float x;
    /**
     * The z coordinate to score at.
     */
    private final float z;

    /**
     * Constructor of <code>ScoreTask</code>.
     * @param game The <code>Game</code> instance.
     * @param x The x coordinate to score at.
     * @param z The z coordinate to score at.
     */
    public ScoreTask(Game game, ETeamColor color, float x, float z) {
        super(ETask.Score, game);
        this.color = color;
        this.x = x;
        this.z = z;
    }

    @Override
    public void execute() {
        BattleState state = ((BattleState) this.game.getGameState(EGameState.BattleState));
        Integer goalId = state.getFlagGoalId(color);
        IEntity goalEntity = EntityManager.getInstance().getEntity(goalId);
        View goalView = (View) ViewManager.getInstance().getView(goalEntity);
        float goalX = goalView.getLocalTranslation().x;
        float goalZ = goalView.getLocalTranslation().z;
        
        float distanceSqd = (goalX - x)*(goalX - x) + (goalZ - z)*(goalZ - z);
        if(distanceSqd < EStats.GoalRadius.getValue()*EStats.GoalRadius.getValue())
            this.game.getClient().send(ClientMessages.createScorePkt(x, z));
    }

    @Override
    public boolean equals(Object object) {
        if (super.equals(object)) {
            if (object instanceof ScoreTask) {
                ScoreTask given = (ScoreTask) object;
                return (this.x == given.x && this.z == given.z);
            }
        }
        return false;
    }
}
