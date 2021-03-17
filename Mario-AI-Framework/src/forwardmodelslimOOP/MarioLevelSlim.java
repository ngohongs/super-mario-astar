package forwardmodelslimOOP;

import engine.core.MarioLevel;
import engine.helper.SpriteType;
import engine.helper.TileFeature;

import java.util.ArrayList;
// TODO: removed magic numbers?
// TODO: make static and dynamic parts of the level
public class MarioLevelSlim {
    public int width;
    public int tileWidth;
    public int height;
    public int tileHeight;
    public static int totalCoins;
    public int exitTileX, exitTileY;

    private LevelPart[][] levelParts;

    public MarioLevelSlim(MarioLevel level) {
        this.width = level.width;
        this.tileWidth = level.tileWidth;
        this.height = level.height;
        this.tileHeight = level.tileHeight;
        totalCoins = level.totalCoins;
        this.exitTileX = level.exitTileX;
        this.exitTileY = level.exitTileY;

        levelParts = new LevelPart[level.levelTiles[0].length][level.levelTiles.length];
        for (int y = 0; y < levelParts.length; y++) {
            for (int x = 0; x < levelParts[y].length; x++) {
                if (level.levelTiles[x][y] != 0)
                    levelParts[x][y] = LevelPart.getLevelPart(level.levelTiles[x][y], true);
                else
                    levelParts[x][y] = LevelPart.getLevelPart(level.spriteTemplates[x][y].getValue(), false);
            }
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

    public boolean isBlocking(int xTile, int yTile, float xa, float ya) {
        int block = this.getBlock(xTile, yTile);
        ArrayList<TileFeature> features = TileFeature.getTileType(block);
        boolean blocking = features.contains(TileFeature.BLOCK_ALL);
        blocking |= (ya < 0) && features.contains(TileFeature.BLOCK_UPPER);
        blocking |= (ya > 0) && features.contains(TileFeature.BLOCK_LOWER);

        return blocking;
    }

    public int getBlock(int xTile, int yTile) {
        if (xTile < 0) {
            xTile = 0;
        }
        if (xTile > this.tileWidth - 1) {
            xTile = this.tileWidth - 1;
        }
        if (yTile < 0 || yTile > this.tileHeight - 1) {
            return 0;
        }
        return LevelPart.getLevelBlock(this.levelParts[xTile][yTile]);
    }

    public void setBlock(int xTile, int yTile, int index) {
        if (xTile < 0 || yTile < 0 || xTile > this.tileWidth - 1 || yTile > this.tileHeight - 1) {
            return;
        }
        this.levelParts[xTile][yTile] = LevelPart.getLevelPart(index, true);
    }

    SpriteType getSpriteType(int xTile, int yTile) {
        if (xTile < 0 || yTile < 0 || xTile >= this.tileWidth || yTile >= this.tileHeight) {
            return SpriteType.NONE;
        }
        return LevelPart.getLevelSprite(this.levelParts[xTile][yTile]);
    }

    /*public int getLastSpawnTick(int xTile, int yTile) {
        if (xTile < 0 || yTile < 0 || xTile > this.tileWidth - 1 || yTile > this.tileHeight - 1) {
            return 0;
        }
        return this.lastSpawnTime[xTile][yTile];
    }*/

    /*public void setLastSpawnTick(int xTile, int yTile, int tick) {
        if (xTile < 0 || yTile < 0 || xTile > this.tileWidth - 1 || yTile > this.tileHeight - 1) {
            return;
        }
        this.lastSpawnTime[xTile][yTile] = tick;
    }*/

    /*public String getSpriteCode(int xTile, int yTile) {
        return xTile + "_" + yTile + "_" + this.getSpriteType(xTile, yTile).getValue();
    }*/
}
