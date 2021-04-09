package forwardmodel.bin.core;

import forwardmodel.common.StaticLevel;
import forwardmodel.slim.core.MarioSpriteSlim;
import forwardmodel.slim.core.MarioWorldSlim;
import forwardmodel.slim.level.LevelPart;
import forwardmodel.common.SpriteTypeSlim;
import forwardmodel.slim.sprites.*;

import java.util.concurrent.ConcurrentLinkedQueue;

public class MarioBinData {
    private static final short SPRITE_STORAGE_INFO = 38;

    private static final int BULLET_BILL_BOOLS = 2; // bin model special "isValid" field
    private static final int ENEMY_BOOLS = 5;
    private static final int FIREBALL_BOOLS = 2;  // bin model special "isValid" field
    private static final int FIRE_FLOWER_BOOLS = 2;
    private static final int FLOWER_ENEMY_BOOLS = 1;
    private static final int LIFE_MUSHROOM_BOOLS = 2;
    private static final int MARIO_BOOLS = 10;
    private static final int MUSHROOM_BOOLS = 2;
    private static final int SHELL_BOOLS = 2;

    private static final int WORLD_INTS = 6;
    private static final int LEVEL_INTS = 8;
    private static final int BULLET_BILL_INTS = 1;
    private static final int ENEMY_INTS = 3;
    private static final int FIREBALL_INTS = 1;
    private static final int FIRE_FLOWER_INTS = 1;
    private static final int FLOWER_ENEMY_INTS = 1;
    private static final int LIFE_MUSHROOM_INTS = 2;
    private static final int MARIO_INTS = 4;
    private static final int MUSHROOM_INTS = 2;
    private static final int SHELL_INTS = 1;

    private static final int WORLD_FLOATS = 2;
    public static final int BULLET_BILL_FLOATS = 2;
    private static final int ENEMY_FLOATS = 4;
    private static final int FIREBALL_FLOATS = 4;
    private static final int FIRE_FLOWER_FLOATS = 2;
    private static final int FLOWER_ENEMY_FLOATS = 4;
    private static final int LIFE_MUSHROOM_FLOATS = 4;
    private static final int MARIO_FLOATS = 7;
    private static final int MUSHROOM_FLOATS = 4;
    private static final int SHELL_FLOATS = 4;

    private static final int PAUSE_TIMER = 0;
    private static final int CURRENT_TIMER = 1;
    private static final int CURRENT_TICK = 2;
    private static final int COINS = 3;
    private static final int LIVES = 4;
    private static final int GAME_STATUS_CODE = 5;

    private static final int WIDTH = 6;
    private static final int TILE_WIDTH = 7;
    private static final int HEIGHT = 8;
    private static final int TILE_HEIGHT = 9;
    private static final int EXIT_TILE_X = 10;
    private static final int CURRENT_CUTOUT_CENTER = 11;
    private static final int CUTOUT_ARRAY_BEGINNING_INDEX = 12;
    private static final int CUTOUT_LEFT_BORDER_X = 13;

    private static final int SPRITES_START = 0;
    private static final int SPRITES_COUNT = 1;

    private static final int BULLET_BILL_MAX_COUNT = 2;
    private static final int ENEMY_COUNT = 3;
    private static final int FIREBALL_MAX_COUNT = 4;
    private static final int FIRE_FLOWER_COUNT = 5;
    private static final int FLOWER_ENEMY_COUNT = 6;
    private static final int LIFE_MUSHROOM_COUNT = 7;
    private static final int MARIO_COUNT = 8;
    private static final int MUSHROOM_COUNT = 9;
    private static final int SHELL_COUNT = 10;

    public static final int BOOLS_BULLET_BILL_START = 11;
    private static final int BOOLS_ENEMY_START = 12;
    private static final int BOOLS_FIREBALL_START = 13;
    private static final int BOOLS_FIRE_FLOWER_START = 14;
    private static final int BOOLS_FLOWER_ENEMY_START = 15;
    private static final int BOOLS_LIFE_MUSHROOM_START = 16;
    private static final int BOOLS_MARIO_START = 17;
    private static final int BOOLS_MUSHROOM_START = 18;
    private static final int BOOLS_SHELL_START = 19;

    private static final int INTS_BULLET_BILL_START = 20;
    private static final int INTS_ENEMY_START = 21;
    private static final int INTS_FIREBALL_START = 22;
    private static final int INTS_FIRE_FLOWER_START = 23;
    private static final int INTS_FLOWER_ENEMY_START = 24;
    private static final int INTS_LIFE_MUSHROOM_START = 25;
    private static final int INTS_MARIO_START = 26;
    private static final int INTS_MUSHROOM_START = 27;
    private static final int INTS_SHELL_START = 28;

    public static final int FLOATS_BULLET_BILL_START = 29;
    private static final int FLOATS_ENEMY_START = 30;
    private static final int FLOATS_FIREBALL_START = 31;
    private static final int FLOATS_FIRE_FLOWER_START = 32;
    private static final int FLOATS_FLOWER_ENEMY_START = 33;
    private static final int FLOATS_LIFE_MUSHROOM_START = 34;
    private static final int FLOATS_MARIO_START = 35;
    private static final int FLOATS_MUSHROOM_START = 36;
    private static final int FLOATS_SHELL_START = 37;

    /**
     * BOOLS LAYOUT
     * - ALIVE FLAGS
     * - SPRITE INFO
     *
     * BYTES LAYOUT
     * - LEVEL CUTOUT
     *
     * INTS LAYOUT
     *  WORLD
     * 0 = pauseTimer
     * 1 = currentTimer
     * 2 = currentTick
     * 3 = coins
     * 4 = lives
     * 5 = gameStatusCode
     *  LEVEL
     * 6 = width
     * 7 = tileWidth
     * 8 = height
     * 9 = tileHeight
     * 10 = exitTileX
     * 11 = currentCutoutCenter
     * 12 = cutoutArrayBeginningIndex
     * 13 = cutoutLeftBorderX
     *  SPAWNED SPRITES, SPRITE INFO
     *  - 14+
     *
     * FLOATS LAYOUT
     *  WORLD
     * 0 = cameraX
     * 1 = cameraY
     *  SPRITE INFO
     * - 2+
     *
     * STORAGE INFO
     *  SPRITES
     * 0 = sprites start
     * 1 = sprites count
     *  SPRITE COUNTS
     * 2 = bullet bill max count
     * 3 = enemy count
     * 4 = fireball max count
     * 5 = fire flower count
     * 6 = flower enemy count
     * 7 = life mushroom count
     * 8 = mario count
     * 9 = mushroom count
     * 10 = shell count
     *  BOOLS
     * 11 = bullet bill start
     * 12 = enemy start
     * 13 = fireball start
     * 14 = fire flower start
     * 15 = flower enemy start
     * 16 = life mushroom start
     * 17 = mario start
     * 18 = mushroom start
     * 19 = shell start
     *  INTS
     * 20 = bullet bill start
     * 21 = enemy start
     * 22 = fireball start
     * 23 = fire flower start
     * 24 = flower enemy start
     * 25 = life mushroom start
     * 26 = mario start
     * 27 = mushroom start
     * 28 = shell start
     *  FLOATS
     * 29 = bullet bill start
     * 30 = enemy start
     * 31 = fireball start
     * 32 = fire flower start
     * 33 = flower enemy start
     * 34 = life mushroom start
     * 35 = mario start
     * 36 = mushroom start
     * 37 = shell start
     */

    public short[] spriteStorageInfo;
    public boolean[] bools;
    public byte[] bytes;
    public int[] ints;
    public float[] floats;
    public StaticLevel staticLevel;

    public MarioBinData(MarioWorldSlim slimWorld) {
        staticLevel = slimWorld.level.staticLevel;

        int bulletBillCounter = 0;
        int enemyCounter = 0;
        int fireballCounter = 2; // there can't be more than 2 fireballs at a time
        int fireFlowerCounter = 0;
        int flowerEnemyCounter = 0;
        int lifeMushroomCounter = 0;
        int marioCounter = 1;
        int mushroomCounter = 0;
        int shellCounter = 0;

        for (MarioSpriteSlim spriteSlim : slimWorld.sprites) {
            if (spriteSlim instanceof EnemySlim) {
                enemyCounter++;
                if (spriteSlim.getType() == SpriteTypeSlim.RED_KOOPA ||
                    spriteSlim.getType() == SpriteTypeSlim.RED_KOOPA_WINGED ||
                    spriteSlim.getType() == SpriteTypeSlim.GREEN_KOOPA ||
                    spriteSlim.getType() == SpriteTypeSlim.GREEN_KOOPA_WINGED)
                    shellCounter++;
            }
            else if (spriteSlim instanceof FireFlowerSlim)
                fireFlowerCounter++;
            else if (spriteSlim instanceof FlowerEnemySlim)
                flowerEnemyCounter++;
            else if (spriteSlim instanceof LifeMushroomSlim)
                lifeMushroomCounter++;
            else if (spriteSlim instanceof MushroomSlim)
                mushroomCounter++;
            else if (spriteSlim instanceof ShellSlim)
                shellCounter++;
        }

        for (int x = 0; x < slimWorld.level.staticLevel.data.length; x++) {
            for (int y = 0; y < slimWorld.level.staticLevel.data[0].length; y++) {
                StaticLevel.LevelTile levelTile = slimWorld.level.staticLevel.data[x][y];
                LevelPart levelPart = levelTile.levelPart;
                int id = levelTile.id;

                if (levelPart == LevelPart.BULLET_BILL_CANNON)
                    bulletBillCounter++; // each cannon should have max one bill spawned at a time
                else if (id != -1 && slimWorld.level.aliveFlags[id]) {
                    if (levelPart == LevelPart.GOOMBA || levelPart == LevelPart.GOOMBA_WINGED ||
                        levelPart == LevelPart.RED_KOOPA || levelPart == LevelPart.RED_KOOPA_WINGED ||
                        levelPart == LevelPart.GREEN_KOOPA || levelPart == LevelPart.GREEN_KOOPA_WINGED ||
                        levelPart == LevelPart.SPIKY || levelPart == LevelPart.SPIKY_WINGED) {
                        enemyCounter++;
                        if (levelPart == LevelPart.RED_KOOPA || levelPart == LevelPart.RED_KOOPA_WINGED ||
                            levelPart == LevelPart.GREEN_KOOPA || levelPart == LevelPart.GREEN_KOOPA_WINGED)
                            shellCounter++;
                    }
                    else if (levelPart == LevelPart.POWER_UP_QUESTION_BLOCK || levelPart == LevelPart.POWER_UP_BRICK_BLOCK) {
                        fireFlowerCounter++;
                        mushroomCounter++;
                    }
                    else if (levelPart == LevelPart.PIPE_TOP_LEFT_WITH_FLOWER)
                        flowerEnemyCounter++;
                    else if (levelPart == LevelPart.INVISIBLE_HEALTH_UP_BLOCK || levelPart == LevelPart.HEALTH_UP_BRICK_BLOCK)
                        lifeMushroomCounter++;
                }
            }
        }

        int boolsSize = slimWorld.level.aliveFlags.length +
                bulletBillCounter * BULLET_BILL_BOOLS +
                enemyCounter * ENEMY_BOOLS +
                fireballCounter * FIREBALL_BOOLS +
                fireFlowerCounter * FIRE_FLOWER_BOOLS +
                flowerEnemyCounter * FLOWER_ENEMY_BOOLS +
                lifeMushroomCounter * LIFE_MUSHROOM_BOOLS +
                marioCounter * MARIO_BOOLS +
                mushroomCounter * MUSHROOM_BOOLS +
                shellCounter * SHELL_BOOLS;

        int bytesSize = slimWorld.level.levelCutout.length;

        int spriteCount = bulletBillCounter + enemyCounter + fireballCounter +
                fireFlowerCounter + flowerEnemyCounter + lifeMushroomCounter +
                marioCounter + mushroomCounter + shellCounter;

        int intsSize = WORLD_INTS + LEVEL_INTS + spriteCount +
                bulletBillCounter * BULLET_BILL_INTS +
                enemyCounter * ENEMY_INTS +
                fireballCounter * FIREBALL_INTS +
                fireFlowerCounter * FIRE_FLOWER_INTS +
                flowerEnemyCounter * FLOWER_ENEMY_INTS +
                lifeMushroomCounter * LIFE_MUSHROOM_INTS +
                marioCounter * MARIO_INTS +
                mushroomCounter * MUSHROOM_INTS +
                shellCounter * SHELL_INTS;

        int floatsSize = WORLD_FLOATS +
                bulletBillCounter * BULLET_BILL_FLOATS +
                enemyCounter * ENEMY_FLOATS +
                fireballCounter * FIREBALL_FLOATS +
                fireFlowerCounter * FIRE_FLOWER_FLOATS +
                flowerEnemyCounter * FLOWER_ENEMY_FLOATS +
                lifeMushroomCounter * LIFE_MUSHROOM_FLOATS +
                marioCounter * MARIO_FLOATS +
                mushroomCounter * MUSHROOM_FLOATS +
                shellCounter * SHELL_FLOATS;

        bools = new boolean[boolsSize];
        bytes = new byte[bytesSize];
        ints = new int[intsSize];
        floats = new float[floatsSize];
        spriteStorageInfo = new short[SPRITE_STORAGE_INFO];

        /* SPRITE STORAGE INFO */
        /* SPRITES */
        spriteStorageInfo[SPRITES_START] = WORLD_INTS + LEVEL_INTS;
        spriteStorageInfo[SPRITES_COUNT] = 0;
        /* SPRITE COUNTS */
        spriteStorageInfo[BULLET_BILL_MAX_COUNT] = (short) bulletBillCounter;
        spriteStorageInfo[ENEMY_COUNT] = 0;
        spriteStorageInfo[FIREBALL_MAX_COUNT] = (short) fireballCounter;
        spriteStorageInfo[FIRE_FLOWER_COUNT] = 0;
        spriteStorageInfo[FLOWER_ENEMY_COUNT] = 0;
        spriteStorageInfo[LIFE_MUSHROOM_COUNT] = 0;
        spriteStorageInfo[MARIO_COUNT] = 0;
        spriteStorageInfo[MUSHROOM_COUNT] = 0;
        spriteStorageInfo[SHELL_COUNT] = 0;
        /* BOOLS */
        spriteStorageInfo[BOOLS_BULLET_BILL_START] = (short) slimWorld.level.aliveFlags.length;
        spriteStorageInfo[BOOLS_ENEMY_START] = (short) (spriteStorageInfo[BOOLS_BULLET_BILL_START] + bulletBillCounter * BULLET_BILL_BOOLS);
        spriteStorageInfo[BOOLS_FIREBALL_START] = (short) (spriteStorageInfo[BOOLS_ENEMY_START] + enemyCounter * ENEMY_BOOLS);
        spriteStorageInfo[BOOLS_FIRE_FLOWER_START] = (short) (spriteStorageInfo[BOOLS_FIREBALL_START] + fireballCounter * FIREBALL_BOOLS);
        spriteStorageInfo[BOOLS_FLOWER_ENEMY_START] = (short) (spriteStorageInfo[BOOLS_FIRE_FLOWER_START] + fireFlowerCounter * FIRE_FLOWER_BOOLS);
        spriteStorageInfo[BOOLS_LIFE_MUSHROOM_START] = (short) (spriteStorageInfo[BOOLS_FLOWER_ENEMY_START] + flowerEnemyCounter * FLOWER_ENEMY_BOOLS);
        spriteStorageInfo[BOOLS_MARIO_START] = (short) (spriteStorageInfo[BOOLS_LIFE_MUSHROOM_START] + lifeMushroomCounter * LIFE_MUSHROOM_BOOLS);
        spriteStorageInfo[BOOLS_MUSHROOM_START] = (short) (spriteStorageInfo[BOOLS_MARIO_START] + marioCounter * MARIO_BOOLS);
        spriteStorageInfo[BOOLS_SHELL_START] = (short) (spriteStorageInfo[BOOLS_MUSHROOM_START] + mushroomCounter * MUSHROOM_BOOLS);
        /* INTS */
        spriteStorageInfo[INTS_BULLET_BILL_START] = (short) (WORLD_INTS + LEVEL_INTS + spriteCount);
        spriteStorageInfo[INTS_ENEMY_START] = (short) (spriteStorageInfo[INTS_BULLET_BILL_START] + bulletBillCounter * BULLET_BILL_INTS);
        spriteStorageInfo[INTS_FIREBALL_START] = (short) (spriteStorageInfo[INTS_ENEMY_START] + enemyCounter * ENEMY_INTS);
        spriteStorageInfo[INTS_FIRE_FLOWER_START] = (short) (spriteStorageInfo[INTS_FIREBALL_START] + fireballCounter * FIREBALL_INTS);
        spriteStorageInfo[INTS_FLOWER_ENEMY_START] = (short) (spriteStorageInfo[INTS_FIRE_FLOWER_START] + fireFlowerCounter * FIRE_FLOWER_INTS);
        spriteStorageInfo[INTS_LIFE_MUSHROOM_START] = (short) (spriteStorageInfo[INTS_FLOWER_ENEMY_START] + flowerEnemyCounter * FLOWER_ENEMY_INTS);
        spriteStorageInfo[INTS_MARIO_START] = (short) (spriteStorageInfo[INTS_LIFE_MUSHROOM_START] + lifeMushroomCounter * LIFE_MUSHROOM_INTS);
        spriteStorageInfo[INTS_MUSHROOM_START] = (short) (spriteStorageInfo[INTS_MARIO_START] + marioCounter * MARIO_INTS);
        spriteStorageInfo[INTS_SHELL_START] = (short) (spriteStorageInfo[INTS_MUSHROOM_START] + mushroomCounter * MUSHROOM_INTS);
        /* FLOATS */
        spriteStorageInfo[FLOATS_BULLET_BILL_START] = WORLD_FLOATS;
        spriteStorageInfo[FLOATS_ENEMY_START] = (short) (spriteStorageInfo[FLOATS_BULLET_BILL_START] + bulletBillCounter * BULLET_BILL_INTS);
        spriteStorageInfo[FLOATS_FIREBALL_START] = (short) (spriteStorageInfo[FLOATS_ENEMY_START] + enemyCounter * ENEMY_INTS);
        spriteStorageInfo[FLOATS_FIRE_FLOWER_START] = (short) (spriteStorageInfo[FLOATS_FIREBALL_START] + fireballCounter * FIREBALL_INTS);
        spriteStorageInfo[FLOATS_FLOWER_ENEMY_START] = (short) (spriteStorageInfo[FLOATS_FIRE_FLOWER_START] + fireFlowerCounter * FIRE_FLOWER_INTS);
        spriteStorageInfo[FLOATS_LIFE_MUSHROOM_START] = (short) (spriteStorageInfo[FLOATS_FLOWER_ENEMY_START] + flowerEnemyCounter * FLOWER_ENEMY_INTS);
        spriteStorageInfo[FLOATS_MARIO_START] = (short) (spriteStorageInfo[FLOATS_LIFE_MUSHROOM_START] + lifeMushroomCounter * LIFE_MUSHROOM_INTS);
        spriteStorageInfo[FLOATS_MUSHROOM_START] = (short) (spriteStorageInfo[FLOATS_MARIO_START] + marioCounter * MARIO_INTS);
        spriteStorageInfo[FLOATS_SHELL_START] = (short) (spriteStorageInfo[FLOATS_MUSHROOM_START] + mushroomCounter * MUSHROOM_INTS);

        /* BOOLS */
        System.arraycopy(slimWorld.level.aliveFlags, 0, bools, 0, slimWorld.level.aliveFlags.length);

        /* BYTES */
        System.arraycopy(slimWorld.level.levelCutout, 0, bytes, 0, slimWorld.level.levelCutout.length);

        /* INTS */
        /* WORLD */
        ints[PAUSE_TIMER] = slimWorld.pauseTimer;
        ints[CURRENT_TIMER] = slimWorld.currentTimer;
        ints[CURRENT_TICK] = slimWorld.currentTick;
        ints[COINS] = slimWorld.coins;
        ints[LIVES] = slimWorld.lives;
        ints[GAME_STATUS_CODE] = slimWorld.gameStatusCode;
        /* LEVEL */
        ints[WIDTH] = slimWorld.level.width;
        ints[TILE_WIDTH] = slimWorld.level.tileWidth;
        ints[HEIGHT] = slimWorld.level.height;
        ints[TILE_HEIGHT] = slimWorld.level.tileHeight;
        ints[EXIT_TILE_X] = slimWorld.level.exitTileX;
        ints[CURRENT_CUTOUT_CENTER] = slimWorld.level.currentCutoutCenter;
        ints[CUTOUT_ARRAY_BEGINNING_INDEX] = slimWorld.level.cutoutArrayBeginningIndex;
        ints[CUTOUT_LEFT_BORDER_X] = slimWorld.level.cutoutLeftBorderX;

        /* FLOATS */
        /* WORLD */
        floats[0] = slimWorld.cameraX;
        floats[1] = slimWorld.cameraY;

        for (MarioSpriteSlim spriteSlim : slimWorld.sprites) {
            int spriteCode;
            if (spriteSlim.getType() == SpriteTypeSlim.MARIO) {
                spriteCode = addMario((MarioSlim) spriteSlim);
            }
            else if (spriteSlim instanceof EnemySlim) {
                EnemySlim enemySlim = (EnemySlim) spriteSlim;
                spriteCode = addEnemy(enemySlim.x, enemySlim.y, enemySlim.alive, enemySlim.typeCode, enemySlim.xa,
                        enemySlim.ya, enemySlim.facing, enemySlim.height, enemySlim.onGround,
                        enemySlim.avoidCliffs, enemySlim.winged, enemySlim.noFireballDeath);
            }
            else if (spriteSlim.getType() == SpriteTypeSlim.BULLET_BILL) {
                BulletBillSlim bulletBillSlim = (BulletBillSlim) spriteSlim;
                spriteCode = addBulletBill(bulletBillSlim.x, bulletBillSlim.y, bulletBillSlim.alive, bulletBillSlim.facing);
            }
            else {
                // TODO: rest of the sprites
            }
        }

        // TODO: only for testing
        /*boolsCloning = new boolean[this.bools.length];
        bytesCloning = new byte[this.bytes.length];
        shortsCloning = new short[this.spriteStorageInfo.length];
        intsCloning = new int[this.ints.length];
        floatsCloning = new float[this.floats.length];*/
    }

    // TODO: methods - addEnemy etc. return type+id as int

    /**
     Bullet Bill data storage order:
     BOOL:
      0 = isValid
      1 = alive
     INT:
      0 = facing
     FLOAT:
      0 = x
      1 = y
     */

    private static final int BULLET_BILL_IS_VALID = 0;
    private static final int BULLET_BILL_ALIVE = 1;

    private static final int BULLET_BILL_FACING = 0;

    public static final int BULLET_BILL_X = 0;
    private static final int BULLET_BILL_Y = 1;

// TODO: assert for validity
    int addBulletBill(float x, float y, boolean alive, int facing) {
        int id = 0;
        while (bools[spriteStorageInfo[BOOLS_BULLET_BILL_START] + id * BULLET_BILL_BOOLS + BULLET_BILL_IS_VALID]) {
            id++;
        }
        bools[spriteStorageInfo[BOOLS_BULLET_BILL_START] + id * BULLET_BILL_BOOLS + BULLET_BILL_IS_VALID] = true;
        bools[spriteStorageInfo[BOOLS_BULLET_BILL_START] + id * BULLET_BILL_BOOLS + BULLET_BILL_ALIVE] = alive;

        ints[spriteStorageInfo[INTS_BULLET_BILL_START] + id * BULLET_BILL_INTS + BULLET_BILL_FACING] = facing;

        floats[spriteStorageInfo[FLOATS_BULLET_BILL_START] + id * BULLET_BILL_FLOATS + BULLET_BILL_X] = x;
        floats[spriteStorageInfo[FLOATS_BULLET_BILL_START] + id * BULLET_BILL_FLOATS + BULLET_BILL_Y] = y;

        int spriteCode = id << 16;
        spriteCode |= SpriteTypeSlim.BULLET_BILL.getValue();

        return spriteCode;
    }

    /**
     Enemy data storage order:
     BOOL:
     0 = alive
     1 = onGround
     2 = avoidCliffs
     3 = winged
     4 = noFireballDeath
     INT:
     0 = typeCode
     1 = facing
     2 = height
     FLOAT:
     0 = x
     1 = y
     2 = xa
     3 = ya
    */

    private static final int ENEMY_ALIVE = 0;
    private static final int ENEMY_ON_GROUND = 1;
    private static final int ENEMY_AVOID_CLIFFS = 2;
    private static final int ENEMY_WINGED = 3;
    private static final int ENEMY_NO_FIREBALL_DEATH = 4;

    private static final int ENEMY_TYPE_CODE = 0;
    private static final int ENEMY_FACING = 1;
    private static final int ENEMY_HEIGHT = 2;

    private static final int ENEMY_X = 0;
    private static final int ENEMY_Y = 1;
    private static final int ENEMY_XA = 2;
    private static final int ENEMY_YA = 3;

    int addEnemy(float x, float y, boolean alive, int typeCode, float xa, float ya, int facing, int height,
                 boolean onGround, boolean avoidCliffs, boolean winged, boolean noFireballDeath) {
        int id = ints[spriteStorageInfo[ENEMY_COUNT]];
        spriteStorageInfo[ENEMY_COUNT]++;
        bools[spriteStorageInfo[BOOLS_ENEMY_START] + id * ENEMY_BOOLS + ENEMY_ALIVE] = alive;
        bools[spriteStorageInfo[BOOLS_ENEMY_START] + id * ENEMY_BOOLS + ENEMY_ON_GROUND] = onGround;
        bools[spriteStorageInfo[BOOLS_ENEMY_START] + id * ENEMY_BOOLS + ENEMY_AVOID_CLIFFS] = avoidCliffs;
        bools[spriteStorageInfo[BOOLS_ENEMY_START] + id * ENEMY_BOOLS + ENEMY_WINGED] = winged;
        bools[spriteStorageInfo[BOOLS_ENEMY_START] + id * ENEMY_BOOLS + ENEMY_NO_FIREBALL_DEATH] = noFireballDeath;

        ints[spriteStorageInfo[INTS_ENEMY_START] + id * ENEMY_INTS + ENEMY_TYPE_CODE] = typeCode;
        ints[spriteStorageInfo[INTS_ENEMY_START] + id * ENEMY_INTS + ENEMY_FACING] = facing;
        ints[spriteStorageInfo[INTS_ENEMY_START] + id * ENEMY_INTS + ENEMY_HEIGHT] = height;

        floats[spriteStorageInfo[FLOATS_ENEMY_START] + id * ENEMY_FLOATS + ENEMY_X] = x;
        floats[spriteStorageInfo[FLOATS_ENEMY_START] + id * ENEMY_FLOATS + ENEMY_Y] = y;
        floats[spriteStorageInfo[FLOATS_ENEMY_START] + id * ENEMY_FLOATS + ENEMY_XA] = xa;
        floats[spriteStorageInfo[FLOATS_ENEMY_START] + id * ENEMY_FLOATS + ENEMY_YA] = ya;

        int spriteCode = id << 16;
        spriteCode |= typeCode;

        return spriteCode;
    }

    /**
     Mario data storage order:
     BOOL:
     0 = alive
     1 = onGround
     2 = wasOnGround
     3 = isLarge
     4 = isDucking
     5 = mayJump
     6 = canShoot
     7 = isFire
     8 = oldLarge
     9 = oldFire
     INT:
     0 = height
     1 = invulnerableTime
     2 = facing
     3 = jumpTime
     FLOAT:
     0 = x
     1 = y
     2 = xa
     3 = ya
     4 = xJumpSpeed
     5 = yJumpSpeed
     6 = xJumpStart
     */

    private static final int MARIO_ALIVE = 0;
    private static final int MARIO_ON_GROUND = 1;
    private static final int MARIO_WAS_ON_GROUND = 2;
    private static final int MARIO_IS_LARGE = 3;
    private static final int MARIO_IS_DUCKING = 4;
    private static final int MARIO_MAY_JUMP = 5;
    private static final int MARIO_CAN_SHOOT = 6;
    private static final int MARIO_IS_FIRE = 7;
    private static final int MARIO_OLD_LARGE = 8;
    private static final int MARIO_OLD_FIRE = 9;

    private static final int MARIO_HEIGHT = 0;
    private static final int MARIO_INVULNERABLE_TIME = 1;
    private static final int MARIO_FACING = 2;
    private static final int MARIO_JUMP_TIME = 3;

    private static final int MARIO_X = 0;
    private static final int MARIO_Y = 1;
    private static final int MARIO_XA = 2;
    private static final int MARIO_YA = 3;
    private static final int MARIO_X_JUMP_SPEED = 4;
    private static final int MARIO_Y_JUMP_SPEED = 5;
    private static final int MARIO_X_JUMP_START = 6;

    int addMario(MarioSlim marioSlim) { // only created once
        spriteStorageInfo[MARIO_COUNT]++; // TODO: assert count == 0
        bools[spriteStorageInfo[BOOLS_MARIO_START] + MARIO_ALIVE] = marioSlim.alive;
        bools[spriteStorageInfo[BOOLS_MARIO_START] + MARIO_ON_GROUND] = marioSlim.onGround;
        bools[spriteStorageInfo[BOOLS_MARIO_START] + MARIO_WAS_ON_GROUND] = marioSlim.wasOnGround;
        bools[spriteStorageInfo[BOOLS_MARIO_START] + MARIO_IS_LARGE] = marioSlim.isLarge;
        bools[spriteStorageInfo[BOOLS_MARIO_START] + MARIO_IS_DUCKING] = marioSlim.isDucking;
        bools[spriteStorageInfo[BOOLS_MARIO_START] + MARIO_MAY_JUMP] = marioSlim.mayJump;
        bools[spriteStorageInfo[BOOLS_MARIO_START] + MARIO_CAN_SHOOT] = marioSlim.canShoot;
        bools[spriteStorageInfo[BOOLS_MARIO_START] + MARIO_IS_FIRE] = marioSlim.isFire;
        bools[spriteStorageInfo[BOOLS_MARIO_START] + MARIO_OLD_LARGE] = marioSlim.oldLarge;
        bools[spriteStorageInfo[BOOLS_MARIO_START] + MARIO_OLD_FIRE] = marioSlim.oldFire;

        ints[spriteStorageInfo[INTS_MARIO_START] + MARIO_HEIGHT] = marioSlim.height;
        ints[spriteStorageInfo[INTS_MARIO_START] + MARIO_INVULNERABLE_TIME] = marioSlim.invulnerableTime;
        ints[spriteStorageInfo[INTS_MARIO_START] + MARIO_FACING] = marioSlim.facing;
        ints[spriteStorageInfo[INTS_MARIO_START] + MARIO_JUMP_TIME] = marioSlim.jumpTime;

        floats[spriteStorageInfo[FLOATS_MARIO_START] + MARIO_X] = marioSlim.x;
        floats[spriteStorageInfo[FLOATS_MARIO_START] + MARIO_Y] = marioSlim.y;
        floats[spriteStorageInfo[FLOATS_MARIO_START] + MARIO_XA] = marioSlim.xa;
        floats[spriteStorageInfo[FLOATS_MARIO_START] + MARIO_YA] = marioSlim.ya;
        floats[spriteStorageInfo[FLOATS_MARIO_START] + MARIO_X_JUMP_SPEED] = marioSlim.xJumpSpeed;
        floats[spriteStorageInfo[FLOATS_MARIO_START] + MARIO_Y_JUMP_SPEED] = marioSlim.yJumpSpeed;
        floats[spriteStorageInfo[FLOATS_MARIO_START] + MARIO_X_JUMP_START] = marioSlim.xJumpStart;

        return SpriteTypeSlim.MARIO.getValue();
    }

    // TODO: world, level and these three sprites to have a proof that this approach works

    private MarioBinData() { }

    // TODO: array pooling?, different array copy?
    public MarioBinData clone() {
        MarioBinData clone = new MarioBinData();

        clone.staticLevel = this.staticLevel;

        clone.bools = new boolean[this.bools.length];
        //clone.bools = boolsCloning;
        //clone.bools = getBools();
        System.arraycopy(this.bools, 0, clone.bools, 0, this.bools.length);

        clone.bytes = new byte[this.bytes.length];
        //clone.bytes = bytesCloning;
        //clone.bytes = getBytes();
        System.arraycopy(this.bytes, 0, clone.bytes, 0, this.bytes.length);

        clone.spriteStorageInfo = new short[this.spriteStorageInfo.length];
        //clone.spriteStorageInfo = shortsCloning;
        //clone.spriteStorageInfo = getShorts();
        System.arraycopy(this.spriteStorageInfo, 0, clone.spriteStorageInfo, 0, this.spriteStorageInfo.length);

        clone.ints = new int[this.ints.length];
        //clone.ints = intsCloning;
        //clone.ints = getInts();
        System.arraycopy(this.ints, 0, clone.ints, 0, this.ints.length);

        clone.floats = new float[this.floats.length];
        //clone.floats = floatsCloning;
        //clone.floats = getFloats();
        System.arraycopy(this.floats, 0, clone.floats, 0, this.floats.length);

        return clone;
    }

    // pooling test
    /*
    public void returnArrays() {
        boolsPool.add(bools);
        bytesPool.add(bytes);
        shortsPool.add(spriteStorageInfo);
        intsPool.add(ints);
        floatsPool.add(floats);
    }

    private static ConcurrentLinkedQueue<boolean[]> boolsPool = new ConcurrentLinkedQueue<>();
    private boolean[] getBools() {
        boolean[] bools = boolsPool.poll();
        if (bools != null) return bools;
        return new boolean[this.bools.length];
    }

    private static ConcurrentLinkedQueue<byte[]> bytesPool = new ConcurrentLinkedQueue<>();
    private byte[] getBytes() {
        byte[] bytes = bytesPool.poll();
        if (bytes != null) return bytes;
        return new byte[this.bytes.length];
    }

    private static ConcurrentLinkedQueue<short[]> shortsPool = new ConcurrentLinkedQueue<>();
    private short[] getShorts() {
        short[] shorts = shortsPool.poll();
        if (shorts != null) return shorts;
        return new short[this.spriteStorageInfo.length];
    }

    private static ConcurrentLinkedQueue<int[]> intsPool = new ConcurrentLinkedQueue<>();
    private int[] getInts() {
        int[] ints = intsPool.poll();
        if (ints != null) return ints;
        return new int[this.ints.length];
    }

    private static ConcurrentLinkedQueue<float[]> floatsPool = new ConcurrentLinkedQueue<>();
    private float[] getFloats() {
        float[] floats = floatsPool.poll();
        if (floats != null) return floats;
        return new float[this.floats.length];
    }
    */

    // cloning speed test
    /*
    private boolean[] boolsCloning;
    private byte[] bytesCloning;
    private short[] shortsCloning;
    private int[] intsCloning;
    private float[] floatsCloning;
    */
}
