package mff.agents.astarWaypoints;

import mff.agents.astarHelper.MarioAction;
import mff.agents.benchmark.IAgentBenchmark;
import mff.agents.benchmark.IAgentBenchmarkBacktrack;
import mff.agents.common.IGridHeuristic;
import mff.agents.common.IGridWaypoints;
import mff.agents.common.IMarioAgentMFF;
import mff.agents.common.MarioTimerSlim;
import mff.agents.gridSearch.GridSearchNode;
import mff.forwardmodel.slim.core.MarioForwardModelSlim;

import java.util.ArrayList;

public class Agent implements IMarioAgentMFF, IAgentBenchmark, IGridHeuristic, IAgentBenchmarkBacktrack, IGridWaypoints {
    private ArrayList<boolean[]> actionsList = new ArrayList<>();
    private float bestDistanceToNextWaypoint = Float.MAX_VALUE;
    private int bestWaypointBeingFollowed = -1;
    private boolean finished = false;
    private int totalSearchCalls = 0;
    private int totalNodesEvaluated = 0;
    private int mostBacktrackedNodes = 0;
    private int[][] levelTilesWithPath;

    @Override
    public void initialize(MarioForwardModelSlim model) {
        AStarTree.winFound = false;
        AStarTree.exitTileX = model.getWorld().level.exitTileX * 16;
        initializeWaypoints(AStarTree.gridPath);
    }

    private void initializeWaypoints(ArrayList<GridSearchNode> gridPath) {
        int waypointsSpacing = AStarTree.WAYPOINT_DENSITY - 1;
        for (GridSearchNode node : gridPath) {
            waypointsSpacing++;

            if (waypointsSpacing < AStarTree.WAYPOINT_DENSITY)
                continue;

            AStarTree.waypoints.add(new AStarTree.Waypoint(node.tileX * 16, node.tileY * 16));
            waypointsSpacing = 0;
        }
        AStarTree.Waypoint lastIncludedWaypoint = AStarTree.waypoints.get(AStarTree.waypoints.size() - 1);
        GridSearchNode lastNodeOnPath = gridPath.get(gridPath.size() - 1);
        if (lastIncludedWaypoint.x == lastNodeOnPath.tileX && lastIncludedWaypoint.y == lastNodeOnPath.tileY)
            return;
        AStarTree.waypoints.add(new AStarTree.Waypoint(lastNodeOnPath.tileX * 16, lastNodeOnPath.tileY * 16));
    }

    @Override
    public void receiveLevelWithPath(int[][] levelTilesWithPath) {
        this.levelTilesWithPath = levelTilesWithPath;
    }

    @Override
    public void receiveGridPath(ArrayList<GridSearchNode> gridPath) {
        AStarTree.gridPath = gridPath;
    }

    @Override
    public boolean[] getActions(MarioForwardModelSlim model, MarioTimerSlim timer) {
        if (finished) {
            if (actionsList.size() == 0)
                return MarioAction.NO_ACTION.value;
            else
                return actionsList.remove(actionsList.size() - 1);
        }

        AStarTree tree = new AStarTree(model, 3, levelTilesWithPath);
        ArrayList<boolean[]> newActionsList = tree.search(timer);
        totalSearchCalls++;
        this.totalNodesEvaluated += tree.nodesEvaluated;
        this.mostBacktrackedNodes = Math.max(tree.mostBacktrackedNodes, this.mostBacktrackedNodes);

        if (AStarTree.winFound) {
            actionsList = newActionsList;
            finished = true;
            return actionsList.remove(actionsList.size() - 1);
        }

        if (tree.currentGoalWaypointIndex > bestWaypointBeingFollowed ||
            (tree.currentGoalWaypointIndex == bestWaypointBeingFollowed &&
            tree.furthestNodeTowardsWaypointDistanceFromWaypoint < bestDistanceToNextWaypoint)) {
                bestWaypointBeingFollowed = tree.currentGoalWaypointIndex;
                bestDistanceToNextWaypoint = tree.furthestNodeTowardsWaypointDistanceFromWaypoint;
                actionsList = newActionsList;
        }

        if (actionsList.size() == 0) {
            // TODO: change from exception to return of NO_ACTION
            throw new IllegalStateException("Path further not found!");
        }

        return actionsList.remove(actionsList.size() - 1);
    }

    @Override
    public int getSearchCalls() {
        return totalSearchCalls;
    }

    @Override
    public int getNodesEvaluated() {
        return totalNodesEvaluated;
    }

    @Override
    public int getMostBacktrackedNodes() {
        return mostBacktrackedNodes;
    }

    @Override
    public String getAgentName() {
        return "MFF AStar Agent";
    }
}
