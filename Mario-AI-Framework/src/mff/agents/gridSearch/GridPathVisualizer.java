package mff.agents.gridSearch;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;

public class GridPathVisualizer {
    public static void visualizePath(String level, int[][] levelTiles, ArrayList<GridSearchNode> path) {
        int [][] levelTilesWithPath = levelTiles.clone();
        for (GridSearchNode node : path) {
            levelTilesWithPath[node.tileX][node.tileY] = 42;
        }

        String l = level.stripTrailing();
        StringBuilder sb = new StringBuilder(l.length());
        CharacterIterator it = new StringCharacterIterator(l);
        int char_counter = 0;
        for (char c = it.first(); c != CharacterIterator.DONE; c = it.next()) {
            if (levelTilesWithPath[char_counter % levelTiles.length][char_counter / levelTiles.length] == 42)
                sb.append('■');
            else
                sb.append(c);

            if (c != '\r' && c != '\n')
                char_counter++;
        }

        String result = sb.toString();
        System.out.println(result);
    }
}
