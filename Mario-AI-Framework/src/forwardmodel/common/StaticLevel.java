package forwardmodel.common;

public class StaticLevel {
    public LevelTile[][] tiles;

    public static class LevelTile {
        public int id;
        public LevelPart levelPart;

        public LevelTile(int id, LevelPart levelPart) {
            this.id = id;
            this.levelPart = levelPart;
        }
    }
}
