package mff.agents.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class AgentMain {
    private static String getLevel(String filepath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
            return content;
        } catch (IOException ignored) {
            // try with working directory set one folder down
        }
        try {
            content = new String(Files.readAllBytes(Paths.get("." + filepath)));
        }
        catch (IOException e) {
            System.out.println("Level couldn't be loaded, please check the path provided with regards to your working directory.");
            System.exit(1);
        }
        return content;
    }

    public static void main(String[] args) {
        //testLevel();
        testAllOriginalLevels();
        //testAllAgents();
    }

    private static void testLevel() {
        AgentMarioGame game = new AgentMarioGame();
        game.runGame(new mff.agents.astar.Agent(), getLevel("./levels/original/lvl-1.txt"),
                200, 0, true);
    }

    private static void testAllOriginalLevels() {
        for (int i = 1; i < 16; i++) {
            AgentMarioGame game = new AgentMarioGame();
            game.runGame(new mff.agents.astar.Agent(), getLevel("./levels/original/lvl-" + i + ".txt"),
                    200, 0, true);
        }
    }

    private static void testAllAgents() {
        ArrayList<IMarioAgentMFF> agents = new ArrayList<>() {{
            add(new mff.agents.astar.Agent());
            add(new mff.agents.astarDistanceMetric.Agent());
            add(new mff.agents.astarFast.Agent());
            add(new mff.agents.astarJump.Agent());
            add(new mff.agents.astarPlanning.Agent());
            add(new mff.agents.astarPlanningDynamic.Agent());
            add(new mff.agents.robinBaumgartenSlim.Agent());
            add(new mff.agents.robinBaumgartenSlimImproved.Agent());
        }};

        for (var agent : agents) {
            AgentMarioGame game = new AgentMarioGame();
            System.out.println("Testing " + agent.getAgentName());
            game.runGame(agent, getLevel("./levels/original/lvl-1.txt"), 200, 0, true);
        }
    }
}
