package forwardmodelslim.level;

class StaticLevel {
    LevelTile[][] data;

    static class LevelTile {
        int id;
        LevelPart levelPart;

        LevelTile(int id, LevelPart levelPart) {
            this.id = id;
            this.levelPart = levelPart;
        }
    }
}
