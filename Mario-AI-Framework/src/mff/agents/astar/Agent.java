package mff.agents.astar;

import mff.agents.common.IMarioAgentMFF;
import mff.agents.astarHelper.MarioAction;
import mff.agents.common.MarioTimerSlim;
import mff.forwardmodel.slim.core.MarioForwardModelSlim;

import java.util.ArrayList;

public class Agent implements IMarioAgentMFF {

    private ArrayList<boolean[]> actionsList = new ArrayList<>();
    private float furthestDistance = -1;
    private boolean finished = false;

    @Override
    public void initialize(MarioForwardModelSlim model) {
        AStarTree.winFound = false;
        AStarTree.exitTileX = model.getWorld().level.exitTileX * 16;
    }

    @Override
    public boolean[] getActions(MarioForwardModelSlim model, MarioTimerSlim timer) {
        if (finished) {
            if (actionsList.size() == 0)
                return MarioAction.NO_ACTION.value;
            else
                return actionsList.remove(actionsList.size() - 1);
        }

        AStarTree tree = new AStarTree(model, 1);
        ArrayList<boolean[]> newActionsList = tree.search(timer);

        if (AStarTree.winFound) {
            actionsList = newActionsList;
            finished = true;
            return actionsList.remove(actionsList.size() - 1);
        }

        if (tree.furthestNodeDistance > furthestDistance) {
            furthestDistance = tree.furthestNodeDistance;
            actionsList = newActionsList;
        }

        if (actionsList.size() == 0) {
            System.out.println("NO ACTIONS LEFT!!!");
            // TODO: needs solving
            finished = true;
            return MarioAction.NO_ACTION.value;
        }

        return actionsList.remove(actionsList.size() - 1);
    }

    @Override
    public String getAgentName() {
        return "MFF AStar Agent";
    }
}
