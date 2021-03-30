package forwardmodelslim.level;

import engine.helper.SpriteType;

public enum LevelPart {
    // sprites - multiplied by -1 to avoid collisions with tiles
    // only sprites that are a part of the level layout
    GOOMBA(-2),
    GOOMBA_WINGED(-3),
    RED_KOOPA(-4),
    RED_KOOPA_WINGED(-5),
    GREEN_KOOPA(-6),
    GREEN_KOOPA_WINGED(-7),
    SPIKY(-8),
    SPIKY_WINGED(-9),
    ENEMY_FLOWER(-11),

    // special
    PIPE_TOP_LEFT_WITH_FLOWER(100), // ENEMY_FLOWER is here
    PIPE_TOP_LEFT_WITHOUT_FLOWER(101), // ENEMY_FLOWER was already spawned

    // tiles
    EMPTY(0),
    GROUND_BLOCK(1),
    PYRAMID_BLOCK(2),
    BULLET_BILL_CANNON(3),
    BULLET_BILL_BASE(4),
    BULLET_BILL_COLUMN(5),
    NORMAL_BRICK_BLOCK(6),
    COIN_BRICK_BLOCK(7),
    POWER_UP_QUESTION_BLOCK(8),
    COIN_QUESTION_BLOCK(11),
    USED(14),
    COIN(15),
    PIPE_TOP_LEFT(18),
    PIPE_TOP_RIGHT(19),
    PIPE_BODY_LEFT(20),
    PIPE_BODY_RIGHT(21),
    // TODO: 39 = flag, 40 = flag pole, needed?
    JUMP_THROUGH_BLOCK_ALONE(43),
    JUMP_THROUGH_BLOCK_LEFT(44),
    JUMP_THROUGH_BLOCK_RIGHT(45),
    JUMP_THROUGH_BLOCK_CENTER(46),
    JUMP_THROUGH_BLOCK_BACKGROUND(47),
    INVISIBLE_HEALTH_UP_BLOCK(48),
    INVISIBLE_COIN_BLOCK(49),
    POWER_UP_BRICK_BLOCK(50),
    HEALTH_UP_BRICK_BLOCK(51),
    PIPE_SINGLE_TOP(52),
    PIPE_SINGLE_BODY(53);

    private int value;

    LevelPart(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

    static LevelPart getLevelPart(int value, boolean levelTile) {
        if (value == 18 || value == -11) // assume pipe with flower
            return PIPE_TOP_LEFT_WITH_FLOWER;
        if (!levelTile)
            value *= -1;
        for (LevelPart levelPart : LevelPart.values()) {
            if (levelPart.value == value)
                return levelPart;
        }
        throw new IllegalArgumentException();
    }

    static LevelPart checkLevelBlock(LevelPart levelPart) {
        if (levelPart == PIPE_TOP_LEFT_WITH_FLOWER ||
            levelPart == PIPE_TOP_LEFT_WITHOUT_FLOWER)
            return PIPE_TOP_LEFT;
        if (levelPart.value < 0)
            return EMPTY;
        else
            return levelPart;
    }

    static SpriteType getLevelSprite(LevelPart levelPart) {
        if (levelPart == PIPE_TOP_LEFT_WITH_FLOWER)
            return SpriteType.getSpriteType(-ENEMY_FLOWER.value);
        if (levelPart == PIPE_TOP_LEFT_WITHOUT_FLOWER)
            return SpriteType.NONE;

        int value = levelPart.value;
        if (value >= 0)
            return SpriteType.NONE;
        else {
            value *= -1;
            return SpriteType.getSpriteType(value);
        }
    }

    static boolean isDynamic(LevelPart levelPart) {
        switch (levelPart) {
            case GOOMBA:
            case GOOMBA_WINGED:
            case RED_KOOPA:
            case RED_KOOPA_WINGED:
            case GREEN_KOOPA:
            case GREEN_KOOPA_WINGED:
            case SPIKY:
            case SPIKY_WINGED:
            case ENEMY_FLOWER:
            case NORMAL_BRICK_BLOCK:
            case COIN_BRICK_BLOCK:
            case POWER_UP_QUESTION_BLOCK:
            case COIN_QUESTION_BLOCK:
            case COIN:
            case INVISIBLE_HEALTH_UP_BLOCK:
            case INVISIBLE_COIN_BLOCK:
            case POWER_UP_BRICK_BLOCK:
            case HEALTH_UP_BRICK_BLOCK:
            case PIPE_TOP_LEFT_WITH_FLOWER:
                return true;
            case EMPTY:
            case GROUND_BLOCK:
            case PYRAMID_BLOCK:
            case BULLET_BILL_CANNON:
            case BULLET_BILL_BASE:
            case BULLET_BILL_COLUMN:
            case USED:
            case PIPE_TOP_LEFT:
            case PIPE_TOP_RIGHT:
            case PIPE_BODY_LEFT:
            case PIPE_BODY_RIGHT:
            case JUMP_THROUGH_BLOCK_ALONE:
            case JUMP_THROUGH_BLOCK_LEFT:
            case JUMP_THROUGH_BLOCK_RIGHT:
            case JUMP_THROUGH_BLOCK_CENTER:
            case JUMP_THROUGH_BLOCK_BACKGROUND:
            case PIPE_SINGLE_TOP:
            case PIPE_SINGLE_BODY:
            case PIPE_TOP_LEFT_WITHOUT_FLOWER:
                return false;
            default:
                throw new IllegalArgumentException();
        }
    }

    static LevelPart getUsedState(LevelPart levelPart) {
        switch (levelPart) {
            case GOOMBA:
            case GOOMBA_WINGED:
            case RED_KOOPA:
            case RED_KOOPA_WINGED:
            case GREEN_KOOPA:
            case GREEN_KOOPA_WINGED:
            case SPIKY:
            case SPIKY_WINGED:
            case NORMAL_BRICK_BLOCK:
            case COIN:
                return EMPTY;
            case COIN_BRICK_BLOCK:
            case POWER_UP_QUESTION_BLOCK:
            case COIN_QUESTION_BLOCK:
            case INVISIBLE_HEALTH_UP_BLOCK:
            case INVISIBLE_COIN_BLOCK:
            case POWER_UP_BRICK_BLOCK:
            case HEALTH_UP_BRICK_BLOCK:
                return USED;
            case PIPE_TOP_LEFT_WITH_FLOWER:
                return PIPE_TOP_LEFT_WITHOUT_FLOWER;
            default:
                throw new IllegalArgumentException();
        }
    }
}
