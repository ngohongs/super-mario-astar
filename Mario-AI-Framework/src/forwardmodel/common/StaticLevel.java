package forwardmodel.common;

import forwardmodel.slim.level.LevelPart;

public class StaticLevel {
    public LevelTile[][] data;

    public static class LevelTile {
        public int id;
        public LevelPart levelPart;

        public LevelTile(int id, LevelPart levelPart) {
            this.id = id;
            this.levelPart = levelPart;
        }
    }
}
