package forwardmodelslimOOP;

import java.util.ArrayList;

public enum TileFeaturesSlim {
    BLOCK_UPPER,
    BLOCK_ALL,
    BLOCK_LOWER,
    SPECIAL,
    LIFE,
    BUMPABLE,
    BREAKABLE,
    PICKABLE,
    ANIMATED,
    SPAWNER;

    public static ArrayList<TileFeaturesSlim> getTileType(LevelPart levelPart) {
        ArrayList<TileFeaturesSlim> features = new ArrayList<>();
        switch (levelPart) {
            case GROUND_BLOCK:
            case PYRAMID_BLOCK:
            case USED:
            case PIPE_TOP_LEFT:
            case PIPE_TOP_RIGHT:
            case PIPE_BODY_LEFT:
            case PIPE_BODY_RIGHT:
            case BULLET_BILL_BASE:
            case BULLET_BILL_COLUMN:
            case PIPE_SINGLE_TOP:
            case PIPE_SINGLE_BODY:
                features.add(BLOCK_ALL);
                break;
            case JUMP_THROUGH_BLOCK_ALONE:
            case JUMP_THROUGH_BLOCK_LEFT:
            case JUMP_THROUGH_BLOCK_RIGHT:
            case JUMP_THROUGH_BLOCK_CENTER:
                features.add(BLOCK_LOWER);
                break;
            case INVISIBLE_HEALTH_UP_BLOCK:
                features.add(BLOCK_UPPER);
                features.add(LIFE);
                features.add(BUMPABLE);
                break;
            case INVISIBLE_COIN_BLOCK:
                features.add(BUMPABLE);
                features.add(BLOCK_UPPER);
                break;
            case BULLET_BILL_CANNON:
                features.add(BLOCK_ALL);
                features.add(SPAWNER);
                break;
            case POWER_UP_QUESTION_BLOCK:
                features.add(BLOCK_ALL);
                features.add(SPECIAL);
                features.add(BUMPABLE);
                features.add(ANIMATED);
                break;
            case COIN_QUESTION_BLOCK:
                features.add(BLOCK_ALL);
                features.add(BUMPABLE);
                features.add(ANIMATED);
                break;
            case NORMAL_BRICK_BLOCK:
                features.add(BLOCK_ALL);
                features.add(BREAKABLE);
                break;
            case COIN_BRICK_BLOCK:
                features.add(BLOCK_ALL);
                features.add(BUMPABLE);
                break;
            case COIN:
                features.add(PICKABLE);
                features.add(ANIMATED);
                break;
            case POWER_UP_BRICK_BLOCK:
                features.add(BLOCK_ALL);
                features.add(SPECIAL);
                features.add(BUMPABLE);
                break;
            case HEALTH_UP_BRICK_BLOCK:
                features.add(BLOCK_ALL);
                features.add(LIFE);
                features.add(BUMPABLE);
                break;
            default:
                throw new IllegalArgumentException();
        }
        return features;
    }
}
