package mff.agents.astarGrid;

import mff.agents.common.IGridHeuristic;
import mff.agents.common.IMarioAgentMFF;
import mff.agents.gridSearch.GridSearch;
import mff.agents.gridSearch.GridSearchNode;

import java.util.ArrayList;

public class AStarGridHelper {
    public static void giveLevelTilesWithPath(IMarioAgentMFF agent, String levelPath) {
        GridSearch gridSearch = GridSearch.initGridSearch(levelPath);
        ArrayList<GridSearchNode> gridPath = gridSearch.findGridPath();
        int[][] levelTilesWithPath = gridSearch.markGridPathInLevelTiles(gridPath);
        ((IGridHeuristic) agent).receiveLevelWithPath(levelTilesWithPath);
    }
}
