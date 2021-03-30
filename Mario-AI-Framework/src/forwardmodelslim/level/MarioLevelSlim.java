package forwardmodelslim.level;

import engine.core.MarioLevel;
import engine.helper.SpriteType;

import java.util.ArrayList;
import java.util.Arrays;

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
    private boolean[] aliveFlags;
    private int currentCutoutCenter;
    private int cutoutArrayBeginningIndex; // index of the current array beginning
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
        staticLevel.data = new StaticLevel.LevelTile[level.levelTiles.length][level.levelTiles[0].length];
        int dynamicTileCounter = 0;
        for (int x = 0; x < level.levelTiles.length; x++) {
            for (int y = 0; y < level.levelTiles[x].length; y++) {
                LevelPart levelPart;
                if (level.levelTiles[x][y] != 0) {
                    if (level.levelTiles[x][y] == 39 || level.levelTiles[x][y] == 40)
                        levelPart = LevelPart.EMPTY; // flag is ignored
                    // pipe top left - might have flower
                    else if (level.levelTiles[x][y] == 18) {
                        if (level.spriteTemplates[x][y] == SpriteType.ENEMY_FLOWER)
                            levelPart = LevelPart.PIPE_TOP_LEFT_WITH_FLOWER;
                        else
                            levelPart = LevelPart.PIPE_TOP_LEFT_WITHOUT_FLOWER;
                    }
                    else
                        levelPart = LevelPart.getLevelPart(level.levelTiles[x][y], true);
                }
                else
                    levelPart = LevelPart.getLevelPart(level.spriteTemplates[x][y].getValue(), false);

                if (LevelPart.isDynamic(levelPart)) {
                    staticLevel.data[x][y] = new StaticLevel.LevelTile(dynamicTileCounter, levelPart);
                    dynamicTileCounter++;
                }
                else
                    staticLevel.data[x][y] = new StaticLevel.LevelTile(-1, levelPart);
            }
        }

        levelCutout = new LevelPart[cutoutTileWidth * this.tileHeight];
        aliveFlags = new boolean[dynamicTileCounter];
        Arrays.fill(aliveFlags, true);
        currentCutoutCenter = marioTileX;
        cutoutArrayBeginningIndex = 0;
        cutoutLeftBorderX = marioTileX - cutoutTileWidth / 2;

        int copyStart = marioTileX - cutoutTileWidth / 2;
        int copyEnd = cutoutTileWidth % 2 == 0 ? marioTileX + cutoutTileWidth / 2 - 1 : marioTileX + cutoutTileWidth / 2;

        // cutout shifted to not overlap the map if needed
        if (copyStart < 0) {
            copyStart = 0;
            copyEnd = copyStart + cutoutTileWidth - 1;
            currentCutoutCenter = cutoutTileWidth / 2;
            cutoutLeftBorderX = 0;
        }
        else if (copyEnd >= tileWidth) {
            copyStart = (tileWidth - 1) - (cutoutTileWidth - 1);
            copyEnd = tileWidth - 1;
            int centerShift = cutoutTileWidth % 2 == 0 ? cutoutTileWidth / 2 - 1 : cutoutTileWidth / 2;
            currentCutoutCenter = (tileWidth - 1) - centerShift;
            cutoutLeftBorderX = copyStart;

        }

        int column = 0;
        for (int x = copyStart; x <= copyEnd; x++) {
            for (int y = 0; y < this.tileHeight; y++) {
                if (x < 0 || x >= tileWidth)
                    levelCutout[column * tileHeight + y] = LevelPart.EMPTY;
                else
                    // no need for alive check, still initializing
                    levelCutout[column * tileHeight + y] = staticLevel.data[x][y].levelPart;
            }
            column++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarioLevelSlim that = (MarioLevelSlim) o;
        boolean propertiesEqual = width == that.width &&
                tileWidth == that.tileWidth &&
                height == that.height &&
                tileHeight == that.tileHeight &&
                exitTileX == that.exitTileX &&
                currentCutoutCenter == that.currentCutoutCenter &&
                cutoutLeftBorderX == that.cutoutLeftBorderX;
        if (propertiesEqual)
            System.out.println("LEVEL PROPERTIES EQUAL");
        else
            System.out.println("LEVEL PROPERTIES NOT EQUAL");

        boolean cutoutsEqual = compareCutouts(this, that);

        return propertiesEqual && cutoutsEqual;
    }

    private boolean compareCutouts(MarioLevelSlim l1, MarioLevelSlim l2) {
        LevelPart[] a1 = l1.levelCutout;
        LevelPart[] a2 = l2.levelCutout;

        if (a1.length != a2.length) {
            System.out.println("LEVEL CUTOUTS NOT EQUAL");
            return false;
        }

        int i1, i2;
        for (int i = 0; i < a1.length; i++) {
            i1 = (i + l1.cutoutArrayBeginningIndex) % a1.length;
            i2 = (i + l2.cutoutArrayBeginningIndex) % a2.length;
            if (a1[i1].getValue() != a2[i2].getValue()) {
                System.out.println("LEVEL CUTOUTS NOT EQUAL");
                return false;
            }
        }

        System.out.println("LEVEL CUTOUTS EQUAL");
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
            if (currentCutoutCenter < marioTileX && cutoutLeftBorderX + cutoutTileWidth != tileWidth) { // move right
                int newColumnIndex = cutoutTileWidth % 2 == 0 ? marioTileX + cutoutTileWidth / 2 - 1 : marioTileX + cutoutTileWidth / 2;
                if (newColumnIndex >= tileWidth) // beyond end of level
                    return;
                int y = 0;
                for (int i = cutoutArrayBeginningIndex; i < cutoutArrayBeginningIndex + tileHeight; i++) {
                    if (staticLevel.data[newColumnIndex][y].id == -1 || aliveFlags[staticLevel.data[newColumnIndex][y].id])
                        levelCutout[i] = staticLevel.data[newColumnIndex][y].levelPart;
                    else
                        levelCutout[i] = LevelPart.getUsedState(staticLevel.data[newColumnIndex][y].levelPart);
                    y++;
                }
                currentCutoutCenter++;
                cutoutLeftBorderX++;
                cutoutArrayBeginningIndex = (cutoutArrayBeginningIndex + tileHeight) % (cutoutTileWidth * this.tileHeight);
            }
            else if (currentCutoutCenter > marioTileX && cutoutLeftBorderX - 1 >= 0) { // move left
                if (cutoutLeftBorderX <= 0) // left cutout border <= beginning of level
                    return;
                int cutoutLastColumnIndex = cutoutArrayBeginningIndex - tileHeight;
                if (cutoutLastColumnIndex < 0)
                    cutoutLastColumnIndex = (cutoutTileWidth * this.tileHeight) - tileHeight;
                int newColumnIndex = marioTileX - cutoutTileWidth / 2;
                int y = 0;
                for (int i = cutoutLastColumnIndex; i < cutoutLastColumnIndex + tileHeight; i++) {
                    if (staticLevel.data[newColumnIndex][y].id == -1
                            || aliveFlags[staticLevel.data[newColumnIndex][y].id])
                        levelCutout[i] = staticLevel.data[newColumnIndex][y].levelPart;
                    else
                        levelCutout[i] = LevelPart.getUsedState(staticLevel.data[newColumnIndex][y].levelPart);
                    y++;
                }
                currentCutoutCenter--;
                cutoutLeftBorderX--;
                cutoutArrayBeginningIndex -= tileHeight;
                if (cutoutArrayBeginningIndex < 0)
                    cutoutArrayBeginningIndex = (cutoutTileWidth * this.tileHeight) - tileHeight;
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

    // a block that is set is necessarily dynamic
    public void setBlock(int xTile, int yTile, int index) {
        if (xTile < 0 || yTile < 0 || xTile > this.tileWidth - 1 || yTile > this.tileHeight - 1) {
            return;
        }

        if (levelCutout[calculateCutoutIndex(xTile, yTile)] == LevelPart.PIPE_TOP_LEFT_WITH_FLOWER) {
            levelCutout[calculateCutoutIndex(xTile, yTile)] = LevelPart.PIPE_TOP_LEFT_WITHOUT_FLOWER;
            aliveFlags[staticLevel.data[xTile][yTile].id] = false;
        }
        else {
            levelCutout[calculateCutoutIndex(xTile, yTile)] = LevelPart.getLevelPart(index, true);
            aliveFlags[staticLevel.data[xTile][yTile].id] = false;
        }
    }

    public SpriteType getSpriteType(int xTile, int yTile) {
        if (xTile < 0 || yTile < 0 || xTile >= this.tileWidth || yTile >= this.tileHeight) {
            return SpriteType.NONE;
        }
        return LevelPart.getLevelSprite(levelCutout[calculateCutoutIndex(xTile, yTile)]);
    }

    private int calculateCutoutIndex(int x, int y) {
        int cutoutX = x - cutoutLeftBorderX;
        // TODO: only for testing
        if (cutoutX < 0 || cutoutX >= cutoutTileWidth)
            throw new IllegalStateException("Cutout not wide enough");
        return (cutoutArrayBeginningIndex + cutoutX * tileHeight + y) % (cutoutTileWidth * this.tileHeight);
    }
}
