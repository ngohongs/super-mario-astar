package forwardmodelslimOOP;

import engine.helper.SpriteType;

// TODO: can this be byte, not int?
public enum LevelPart {
    // sprites - multiplied by -1 to avoid collisions with tiles
    NONE(0),
    UNDEF(42),
    MARIO(31),
    FIREBALL(-16),
    GOOMBA(-2),
    GOOMBA_WINGED(-3),
    RED_KOOPA(-4),
    RED_KOOPA_WINGED(-5),
    GREEN_KOOPA(-6),
    GREEN_KOOPA_WINGED(-7),
    SPIKY(-8),
    SPIKY_WINGED(-9),
    BULLET_BILL(-10),
    ENEMY_FLOWER(-11), // also means PIPE_MULTI_0 //TODO: take care
    MUSHROOM(-12),
    FIRE_FLOWER(-13),
    SHELL(-14),
    LIFE_MUSHROOM(-15),

    // tiles
    FLOOR(1),
    PYRAMID_BLOCK(2),
    JUMP_THROUGH_BLOCK_0(43),
    JUMP_THROUGH_BLOCK_1(44),
    JUMP_THROUGH_BLOCK_2(45),
    JUMP_THROUGH_BLOCK_3(46),
    JUMP_THROUGH_BLOCK_BACKGROUND(47),
    BULLET_BILL_HEAD(3),
    BULLET_BILL_1(4),
    BULLET_BILL_2(5),
    MUSHROOM_QUESTION_BLOCK(8),
    COIN_QUESTION_BLOCK(11),
    INVISIBLE_UP_BLOCK(48),
    INVISIBLE_COIN_BLOCK(49),
    USED(14),
    NORMAL_BLOCK(6),
    COIN_BLOCK(7),
    MUSHROOM_BLOCK(50),
    UP_BLOCK(51),
    COIN(15),
    PIPE_SINGLE_0(52),
    PIPE_SINGLE_1(53),
    PIPE_SINGLE_2(54),
    PIPE_SINGLE_3(55),
    PIPE_MULTI_0(18),
    PIPE_MULTI_1(19),
    PIPE_MULTI_2(20),
    PIPE_MULTI_3(21);

    private int value;

    LevelPart(int value) {
        this.value = value;
    }

    static LevelPart getLevelPart(int value, boolean levelTile) {
        if (!levelTile)
            value *= -1;
        for (LevelPart levelPart : LevelPart.values()) {
            if (levelPart.value == value)
                return levelPart;
        }
        throw new IllegalArgumentException();
    }

    static int getBlock(LevelPart levelPart) {
        int value = levelPart.value;
        if (value < 0 || value == 42 || value == 31)
            return 0;
        else
            return value;
    }

    static SpriteType getSpriteType(LevelPart levelPart) {
        int value = levelPart.value;
        if (value != 42 && value != 31 && value >= 0)
            return SpriteType.NONE;
        else {
            value *= -1;
            return SpriteType.getSpriteType(value);
        }
    }

    public MarioSpriteSlim spawnSprite(int x, int y, int dir) {
        return null; //TODO
    }
}
