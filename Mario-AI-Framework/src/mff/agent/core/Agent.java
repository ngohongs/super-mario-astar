package mff.agent.core;

import engine.core.MarioTimer;
import engine.helper.MarioActions;
import mff.agent.helper.IMarioAgentSlim;
import mff.forwardmodel.slim.core.MarioForwardModelSlim;

public class Agent implements IMarioAgentSlim {
    private boolean[] action;
    private AStarTree tree;

    @Override
    public void initialize(MarioForwardModelSlim model, MarioTimer timer) {
        this.action = new boolean[MarioActions.numberOfActions()];
        this.tree = new AStarTree();
    }

    @Override
    public boolean[] getActions(MarioForwardModelSlim model, MarioTimer timer) {
        action = this.tree.optimise(model, timer);
        return action;
    }

    @Override
    public String getAgentName() {
        return "Robin Baumgarten agent with slim forward model from MFF";
    }
}
