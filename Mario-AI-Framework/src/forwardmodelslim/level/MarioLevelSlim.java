package forwardmodelslim.level;

import engine.core.MarioLevel;
import engine.helper.SpriteType;

import java.util.ArrayList;

// TODO: remove some of the magic numbers, sprite type?

public class MarioLevelSlim {
    public int width;
    private int tileWidth;
    public int height;
    private int tileHeight;
    public int exitTileX;

    private StaticLevel staticLevel;
    private static int cutoutTileWidth;

    private LevelPart[] levelCutout;
    private int currentCutoutCenter;
    private int cutoutArrayBeginning; // index of the current array beginning
    private int cutoutLeftBorderX;

    public MarioLevelSlim(MarioLevel level, int cutoutTileWidth, int marioTileX) {
        this.width = level.width;
        this.tileWidth = level.tileWidth;
        this.height = level.height;
        this.tileHeight = level.tileHeight;
        this.exitTileX = level.exitTileX;

        MarioLevelSlim.cutoutTileWidth = cutoutTileWidth;
        if (MarioLevelSlim.cutoutTileWidth > tileWidth)
            MarioLevelSlim.cutoutTileWidth = tileWidth;

        staticLevel = new StaticLevel();
        staticLevel.data = new LevelPart[level.levelTiles.length][level.levelTiles[0].length];
        for (int x = 0; x < level.levelTiles.length; x++) {
            for (int y = 0; y < level.levelTiles[x].length; y++) {
                LevelPart levelPart;
                if (level.levelTiles[x][y] != 0) {
                    if (level.levelTiles[x][y] == 39 || level.levelTiles[x][y] == 40)
                        levelPart = LevelPart.EMPTY; // flag is ignored
                    else
                        levelPart = LevelPart.getLevelPart(level.levelTiles[x][y], true);
                }
                else
                    levelPart = LevelPart.getLevelPart(level.spriteTemplates[x][y].getValue(), false);

                staticLevel.data[x][y] = levelPart;
            }
        }

        levelCutout = new LevelPart[cutoutTileWidth * this.tileHeight];
        currentCutoutCenter = marioTileX;
        cutoutArrayBeginning = 0;
        cutoutLeftBorderX = marioTileX - cutoutTileWidth / 2;

        int copyStart = marioTileX - cutoutTileWidth / 2;
        int copyEnd = cutoutTileWidth % 2 == 0 ? marioTileX + cutoutTileWidth / 2 - 1 : marioTileX + cutoutTileWidth / 2;

        int column = 0;
        for (int x = copyStart; x <= copyEnd; x++) {
            for (int y = 0; y < this.tileHeight; y++) {
                if (x < 0 || x >= tileWidth)
                    levelCutout[column * tileHeight + y] = LevelPart.EMPTY;
                else
                    levelCutout[column * tileHeight + y] = staticLevel.data[x][y];
            }
            column++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarioLevelSlim that = (MarioLevelSlim) o;
        boolean equal = width == that.width &&
                tileWidth == that.tileWidth &&
                height == that.height &&
                tileHeight == that.tileHeight &&
                exitTileX == that.exitTileX &&
                currentCutoutCenter == that.currentCutoutCenter &&
                cutoutArrayBeginning == that.cutoutArrayBeginning &&
                cutoutLeftBorderX == that.cutoutLeftBorderX &&
                compareCutouts(levelCutout, that.levelCutout);
        if (equal) {
            System.out.println("LEVEL EQUAL");
            return true;
        }
        else {
            System.out.println("LEVEL NOT EQUAL");
            return false;
        }
    }

    private boolean compareCutouts(LevelPart[] a1, LevelPart[] a2) {
        if (a1.length != a2.length)
            return false;
        for (int i = 0; i < a1.length; i++) {
            if (a1[i].getValue() != a2[i].getValue())
                return false;
        }
        return true;
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

    public void update(int marioTileX) {
        if (currentCutoutCenter != marioTileX) {
            if (currentCutoutCenter < marioTileX) { // move right
                int newColumnIndex = cutoutTileWidth % 2 == 0 ? marioTileX + cutoutTileWidth / 2 - 1 : marioTileX + cutoutTileWidth / 2;
                if (newColumnIndex >= tileWidth) // beyond end of level
                    return;
                int y = 0;
                for (int i = cutoutArrayBeginning; i < cutoutArrayBeginning + tileHeight; i++) {
                    levelCutout[i] = staticLevel.data[newColumnIndex][y];
                    y++;
                }
                currentCutoutCenter++;
                cutoutLeftBorderX++;
                cutoutArrayBeginning = (cutoutArrayBeginning + tileHeight) % (cutoutTileWidth * this.tileHeight);
            }
            else { // move left
                if (cutoutLeftBorderX <= 0) // left cutout border <= beginning of level
                    return;
                int lastColumnIndex = cutoutArrayBeginning - tileHeight;
                if (lastColumnIndex < 0)
                    lastColumnIndex = (cutoutTileWidth * this.tileHeight) - tileHeight;
                int y = 0;
                for (int i = lastColumnIndex; i < lastColumnIndex + tileHeight; i++) {
                    levelCutout[i] = staticLevel.data[marioTileX - cutoutTileWidth / 2][y];
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

    public boolean isBlocking(int xTile, int yTile, float ya) {
        LevelPart block = this.getBlock(xTile, yTile);
        ArrayList<TileFeaturesSlim> features = TileFeaturesSlim.getTileFeatures(block);
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

    public SpriteType getSpriteType(int xTile, int yTile) {
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
