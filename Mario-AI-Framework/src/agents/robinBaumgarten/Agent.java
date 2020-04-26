package agents.robinBaumgarten;

import engine.core.MarioAgent;
import engine.core.MarioForwardModel;
import engine.core.MarioTimer;
import engine.helper.MarioActions;

/**
 * @author RobinBaumgarten
 */
public class Agent implements MarioAgent {
    private boolean[] action;
    private AStarTree tree;

    @Override
    public void initialize(MarioForwardModel model, MarioTimer timer) {
        this.action = new boolean[MarioActions.numberOfActions()];
        this.tree = new AStarTree();


        for (int i = 0; i < 1000; i++) {
            model.clone();
        }
        long time = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            model.clone();
        }
        long duration = System.currentTimeMillis() - time;
        System.out.println("TIME: " + duration + " ms");
        System.out.println("Clones per second: " + 100000 / (duration / 1000.0) + " clones");

    }

    @Override
    public boolean[] getActions(MarioForwardModel model, MarioTimer timer) {
        action = this.tree.optimise(model, timer);
        return action;
    }

    @Override
    public String getAgentName() {
        return "RobinBaumgartenAgent";
    }

}
