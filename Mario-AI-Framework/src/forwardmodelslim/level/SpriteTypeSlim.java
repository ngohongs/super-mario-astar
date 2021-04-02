package forwardmodelslim.level;

public enum SpriteTypeSlim {
    NONE(0),
    MARIO(-31),
    FIREBALL(16),
    GOOMBA(2),
    GOOMBA_WINGED(3),
    RED_KOOPA(4),
    RED_KOOPA_WINGED(5),
    GREEN_KOOPA(6),
    GREEN_KOOPA_WINGED(7),
    SPIKY(8),
    SPIKY_WINGED(9),
    BULLET_BILL(10),
    ENEMY_FLOWER(11),
    MUSHROOM(12),
    FIRE_FLOWER(13),
    SHELL(14),
    LIFE_MUSHROOM(15);

    private int value;

    SpriteTypeSlim(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SpriteTypeSlim getSpriteTypeSlim(byte value) {
        for (SpriteTypeSlim spriteTypeSlim : SpriteTypeSlim.values()) {
            if (spriteTypeSlim.value == value)
                return spriteTypeSlim;
        }
        throw new IllegalArgumentException();
    }
}
