package mff.agents.gridSearch;

import engine.core.MarioEvent;
import engine.core.MarioGame;
import engine.core.MarioWorld;
import mff.LevelLoader;

import java.util.ArrayList;

public class GridSearchMain {
    public static void main(String[] args) {
        MarioGame marioGame = new MarioGame();
        String level = LevelLoader.getLevel("./levels/original/lvl-1.txt");
        MarioEvent[] killEvents = new MarioEvent[0];
        MarioWorld world = new MarioWorld(killEvents);
        world.initializeLevel(level, 1000000);
        int[][] levelTiles = world.level.getLevelTiles();
        int marioTileX = world.level.marioTileX;
        int marioTileY = world.level.marioTileY;

        GridSearch gridSearch = new GridSearch(levelTiles, marioTileX, marioTileY);
        ArrayList<GridSearchNode> resultPath = gridSearch.findGridPath();

        GridPathVisualizer.visualizePath(level, levelTiles, resultPath);
    }
}
