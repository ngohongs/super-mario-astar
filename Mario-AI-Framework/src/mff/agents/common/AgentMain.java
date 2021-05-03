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
        } catch (IOException ignored) {
        }
        return content;
    }

    public static void main(String[] args) {
        IMarioAgentMFF agent = new mff.agents.astar.Agent();
        //testLevel(agent);
        //testAllOriginalLevels(agent);
        testAllAgents();
    }

    private static void testLevel(IMarioAgentMFF agent) {
        AgentMarioGame game = new AgentMarioGame();
        game.runGame(agent, getLevel("./levels/original/lvl-1.txt"), 200, 0, true);
    }

    private static void testAllOriginalLevels(IMarioAgentMFF agent) {
        for (int i = 1; i < 16; i++) {
            AgentMarioGame game = new AgentMarioGame();
            System.out.print("Level " + i + ": ");
            game.runGame(agent, getLevel("./levels/original/lvl-" + i + ".txt"), 200, 0, true);
        }
    }

    private static void testAllAgents() {
        ArrayList<IMarioAgentMFF> agents = new ArrayList<>() {{
            add(new mff.agents.astar.Agent());
            add(new mff.agents.astarFast.Agent());
            add(new mff.agents.astarJump.Agent());
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
