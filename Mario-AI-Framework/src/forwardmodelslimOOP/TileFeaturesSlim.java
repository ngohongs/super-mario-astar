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

    public static ArrayList<TileFeaturesSlim> getTileFeatures(LevelPart levelPart) {
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
            case PIPE_TOP_LEFT_WITH_FLOWER:
            case PIPE_TOP_LEFT_WITHOUT_FLOWER:
                return blockAll;
            case JUMP_THROUGH_BLOCK_ALONE:
            case JUMP_THROUGH_BLOCK_LEFT:
            case JUMP_THROUGH_BLOCK_RIGHT:
            case JUMP_THROUGH_BLOCK_CENTER:
                return blockLower;
            case INVISIBLE_HEALTH_UP_BLOCK:
                return blockUpper_life_bumpable;
            case INVISIBLE_COIN_BLOCK:
                return bumpable_blockUpper;
            case BULLET_BILL_CANNON:
                return blockAll_spawner;
            case POWER_UP_QUESTION_BLOCK:
                return blockAll_special_bumpable_animated;
            case COIN_QUESTION_BLOCK:
                return blockAll_bumpable_animated;
            case NORMAL_BRICK_BLOCK:
                return blockAll_breakable;
            case COIN_BRICK_BLOCK:
                return blockAll_bumpable;
            case COIN:
                return pickable_animated;
            case POWER_UP_BRICK_BLOCK:
                return blockAll_special_bumpable;
            case HEALTH_UP_BRICK_BLOCK:
                return blockAll_life_bumpable;
            case EMPTY:
            default:
                return empty;
        }
    }

    private static ArrayList<TileFeaturesSlim> blockAll = new ArrayList<>() {{
        add(BLOCK_ALL);
    }};

    private static ArrayList<TileFeaturesSlim> blockLower = new ArrayList<>() {{
        add(BLOCK_LOWER);
    }};

    private static ArrayList<TileFeaturesSlim> blockUpper_life_bumpable = new ArrayList<>() {{
        add(BLOCK_UPPER);
        add(LIFE);
        add(BUMPABLE);
    }};

    private static ArrayList<TileFeaturesSlim> bumpable_blockUpper = new ArrayList<>() {{
        add(BUMPABLE);
        add(BLOCK_UPPER);
    }};

    private static ArrayList<TileFeaturesSlim> blockAll_spawner = new ArrayList<>() {{
        add(BLOCK_ALL);
        add(SPAWNER);
    }};

    private static ArrayList<TileFeaturesSlim> blockAll_special_bumpable_animated = new ArrayList<>() {{
        add(BLOCK_ALL);
        add(SPECIAL);
        add(BUMPABLE);
        add(ANIMATED);
    }};

    private static ArrayList<TileFeaturesSlim> blockAll_bumpable_animated = new ArrayList<>() {{
        add(BLOCK_ALL);
        add(BUMPABLE);
        add(ANIMATED);
    }};

    private static ArrayList<TileFeaturesSlim> blockAll_breakable = new ArrayList<>() {{
        add(BLOCK_ALL);
        add(BREAKABLE);
    }};

    private static ArrayList<TileFeaturesSlim> blockAll_bumpable = new ArrayList<>() {{
        add(BLOCK_ALL);
        add(BUMPABLE);
    }};

    private static ArrayList<TileFeaturesSlim> pickable_animated = new ArrayList<>() {{
        add(PICKABLE);
        add(ANIMATED);
    }};

    private static ArrayList<TileFeaturesSlim> blockAll_special_bumpable = new ArrayList<>() {{
        add(BLOCK_ALL);
        add(SPECIAL);
        add(BUMPABLE);
    }};

    private static ArrayList<TileFeaturesSlim> blockAll_life_bumpable = new ArrayList<>() {{
        add(BLOCK_ALL);
        add(LIFE);
        add(BUMPABLE);
    }};

    private static ArrayList<TileFeaturesSlim> empty = new ArrayList<>();
}
