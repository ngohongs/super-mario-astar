package mff.agentsBenchmark;

import engine.core.MarioLevelGenerator;
import engine.core.MarioLevelModel;
import engine.core.MarioTimer;
import mff.agents.common.AgentMarioGame;

public class Benchmark {
    public static void main(String[] args) {
        MarioLevelGenerator generator = new levelGenerators.noiseBased.LevelGenerator();
        String level = generator.getGeneratedLevel(new MarioLevelModel(150, 16),
                new MarioTimer(5 * 60 * 60 * 1000));
        AgentMarioGame game = new AgentMarioGame();
        game.runGame(new mff.agents.astarPlanningDynamic.Agent(), level, 200, 0, false);
    }
}
