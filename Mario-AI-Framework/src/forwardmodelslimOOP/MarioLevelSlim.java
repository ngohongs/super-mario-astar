package forwardmodelslimOOP;

import engine.core.MarioLevel;
import engine.helper.SpriteType;

import java.util.ArrayList;

// TODO: remove magic numbers?
// TODO: remove some of the magic numbers, sprite type?

public class MarioLevelSlim {
    public int width;
    public int tileWidth;
    public int height;
    public int tileHeight;
    public static int totalCoins;
    public int exitTileX, exitTileY;

    private static LevelPart[][] staticLevelParts;
    private static int cutoutTileWidth;

    private int currentCutoutCenter;
    private LevelPart[] levelCutout;
    private int cutoutArrayBeginning; // index of the current array beginning
    private int cutoutLeftBorderX;

    public MarioLevelSlim(MarioLevel level, int cutoutTileWidth, int marioX) {
        MarioLevelSlim.cutoutTileWidth = cutoutTileWidth;

        this.width = level.width;
        this.tileWidth = level.tileWidth;
        this.height = level.height;
        this.tileHeight = level.tileHeight;
        totalCoins = level.totalCoins;
        this.exitTileX = level.exitTileX;
        this.exitTileY = level.exitTileY;

        staticLevelParts = new LevelPart[level.levelTiles[0].length][level.levelTiles.length];
        for (int y = 0; y < level.levelTiles.length; y++) {
            for (int x = 0; x < level.levelTiles[y].length; x++) {
                LevelPart levelPart;
                if (level.levelTiles[x][y] != 0)
                    levelPart = LevelPart.getLevelPart(level.levelTiles[x][y], true);
                else
                    levelPart = LevelPart.getLevelPart(level.spriteTemplates[x][y].getValue(), false);

                staticLevelParts[x][y] = levelPart;
            }
        }

        levelCutout = new LevelPart[cutoutTileWidth * this.tileHeight];
        cutoutArrayBeginning = 0;
        currentCutoutCenter = marioX;
        cutoutLeftBorderX = marioX - cutoutTileWidth / 2;
        int column = 0;
        // TODO: what if left side is beyond level border - start cutout at level border?; generally what if cutout > level
        for (int x = marioX - cutoutTileWidth / 2; x < marioX + cutoutTileWidth / 2; x++) {
            for (int y = 0; y < this.tileHeight; y++) {
                levelCutout[column * tileHeight + y] = staticLevelParts[x][y];
            }
            column++;
        }
    }

    public MarioLevelSlim clone() {
        /*MarioLevel level = new MarioLevel("", false);
        level.width = this.width;
        level.height = this.height;
        level.tileWidth = this.tileWidth;
        level.tileHeight = this.tileHeight;
        level.totalCoins = this.totalCoins;
        level.marioTileX = this.marioTileX;
        level.marioTileY = this.marioTileY;
        level.exitTileX = this.exitTileX;
        level.exitTileY = this.exitTileY;
        level.levelTiles = new int[this.levelTiles.length][this.levelTiles[0].length];
        level.lastSpawnTime = new int[this.levelTiles.length][this.levelTiles[0].length];
        for (int x = 0; x < level.levelTiles.length; x++) {
            for (int y = 0; y < level.levelTiles[x].length; y++) {
                level.levelTiles[x][y] = this.levelTiles[x][y];
                level.lastSpawnTime[x][y] = this.lastSpawnTime[x][y];
            }
        }
        level.spriteTemplates = this.spriteTemplates;
        return level;*/
        return null;
    }

    void update(int marioX) { // TODO: call from MarioWorldSlim.update
        if (currentCutoutCenter != marioX) {
            if (currentCutoutCenter < marioX) { // move right
                if (currentCutoutCenter + cutoutTileWidth / 2 == tileWidth) // beyond end of level
                    return;
                int y = 0;
                for (int i = cutoutArrayBeginning; i < cutoutArrayBeginning + tileHeight; i++) {
                    levelCutout[i] = staticLevelParts[marioX + cutoutTileWidth / 2 - 1][y];
                    y++;
                }
                currentCutoutCenter++;
                cutoutLeftBorderX++;
                cutoutArrayBeginning = (cutoutArrayBeginning + tileHeight) % (cutoutTileWidth * this.tileHeight);
            }
            else { // move left
                if (cutoutLeftBorderX == 0) // left cutout border == beginning of level
                    return;
                int lastColumnIndex = cutoutArrayBeginning - tileHeight;
                if (lastColumnIndex < 0)
                    lastColumnIndex = (cutoutTileWidth * this.tileHeight) - tileHeight;
                int y = 0;
                for (int i = lastColumnIndex; i < lastColumnIndex + tileHeight; i++) {
                    levelCutout[i] = staticLevelParts[marioX - cutoutTileWidth / 2][y];
                    y++;
                }
                currentCutoutCenter--;
                cutoutLeftBorderX--;
                cutoutArrayBeginning -= tileHeight;
                if (cutoutArrayBeginning < 0)
                    cutoutArrayBeginning = (cutoutTileWidth * this.tileHeight) - tileHeight;
            }
        }
    }

    public boolean isBlocking(int xTile, int yTile, float xa, float ya) {
        LevelPart block = this.getBlock(xTile, yTile);
        ArrayList<TileFeaturesSlim> features = TileFeaturesSlim.getTileType(block);
        boolean blocking = features.contains(TileFeaturesSlim.BLOCK_ALL);
        blocking |= (ya < 0) && features.contains(TileFeaturesSlim.BLOCK_UPPER);
        blocking |= (ya > 0) && features.contains(TileFeaturesSlim.BLOCK_LOWER);

        return blocking;
    }

    public LevelPart getBlock(int xTile, int yTile) {
        if (xTile < 0) {
            xTile = 0;
        }
        if (xTile > this.tileWidth - 1) {
            xTile = this.tileWidth - 1;
        }
        if (yTile < 0 || yTile > this.tileHeight - 1) {
            return LevelPart.EMPTY;
        }

        LevelPart toReturn = levelCutout[calculateCutoutIndex(xTile, yTile)];
        return LevelPart.checkLevelBlock(toReturn);
    }

    public void setBlock(int xTile, int yTile, int index) {
        if (xTile < 0 || yTile < 0 || xTile > this.tileWidth - 1 || yTile > this.tileHeight - 1) {
            return;
        }

        if (levelCutout[calculateCutoutIndex(xTile, yTile)] == LevelPart.PIPE_TOP_LEFT_WITH_FLOWER)
            levelCutout[calculateCutoutIndex(xTile, yTile)] = LevelPart.PIPE_TOP_LEFT_WITHOUT_FLOWER;
        // a block that is set is necessarily dynamic
        levelCutout[calculateCutoutIndex(xTile, yTile)] = LevelPart.getLevelPart(index, true);
    }

    SpriteType getSpriteType(int xTile, int yTile) {
        if (xTile < 0 || yTile < 0 || xTile >= this.tileWidth || yTile >= this.tileHeight) {
            return SpriteType.NONE;
        }
        return LevelPart.getLevelSprite(levelCutout[calculateCutoutIndex(xTile, yTile)]);
    }

    private int calculateCutoutIndex(int x, int y) {
        int cutoutX = x - cutoutLeftBorderX;
        return (cutoutArrayBeginning + cutoutX * tileHeight + y) % (cutoutTileWidth * this.tileHeight);
    }
}
