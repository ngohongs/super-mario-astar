package forwardmodelslimOOP;

import engine.helper.SpriteType;

public class FireballSlim extends MarioSpriteSlim {
    private static final float GROUND_INERTIA = 0.89f;
    private static final float AIR_INERTIA = 0.89f;
    static final SpriteType type = SpriteType.FIREBALL;
    static final int width = 4;
    static final int height = 8;

    float x, y, xa, ya;
    private boolean alive = true;
    byte facing;
    MarioWorldSlim world;
    private boolean onGround = false;

    public FireballSlim(float x, float y, int facing) {
        this.x = x;
        this.y = y;
        this.facing = (byte) facing;
        this.ya = 4;
    }

    @Override
    public MarioSpriteSlim clone() {
        /*Fireball f = new Fireball(false, this.x, this.y, this.facing);
        f.xa = this.xa;
        f.ya = this.ya;
        f.initialCode = this.initialCode;
        f.width = this.width;
        f.height = this.height;
        f.onGround = this.onGround;
        return f;*/
        return null;
    }

    private boolean move(float xa, float ya) {
        while (xa > 8) {
            if (!move(8, 0))
                return false;
            xa -= 8;
        }
        while (xa < -8) {
            if (!move(-8, 0))
                return false;
            xa += 8;
        }
        while (ya > 8) {
            if (!move(0, 8))
                return false;
            ya -= 8;
        }
        while (ya < -8) {
            if (!move(0, -8))
                return false;
            ya += 8;
        }

        boolean collide = false;
        if (ya > 0) {
            if (isBlocking(x + xa - width, y + ya, xa, 0))
                collide = true;
            else if (isBlocking(x + xa + width, y + ya, xa, 0))
                collide = true;
            else if (isBlocking(x + xa - width, y + ya + 1, xa, ya))
                collide = true;
            else if (isBlocking(x + xa + width, y + ya + 1, xa, ya))
                collide = true;
        }
        if (ya < 0) {
            if (isBlocking(x + xa, y + ya - height, xa, ya))
                collide = true;
            else if (collide || isBlocking(x + xa - width, y + ya - height, xa, ya))
                collide = true;
            else if (collide || isBlocking(x + xa + width, y + ya - height, xa, ya))
                collide = true;
        }
        if (xa > 0) {
            if (isBlocking(x + xa + width, y + ya - height, xa, ya))
                collide = true;
            if (isBlocking(x + xa + width, y + ya - height / 2, xa, ya))
                collide = true;
            if (isBlocking(x + xa + width, y + ya, xa, ya))
                collide = true;
        }
        if (xa < 0) {
            if (isBlocking(x + xa - width, y + ya - height, xa, ya))
                collide = true;
            if (isBlocking(x + xa - width, y + ya - height / 2, xa, ya))
                collide = true;
            if (isBlocking(x + xa - width, y + ya, xa, ya))
                collide = true;
        }

        if (collide) {
            if (xa < 0) {
                x = (int) ((x - width) / 16) * 16 + width;
                this.xa = 0;
            }
            if (xa > 0) {
                x = (int) ((x + width) / 16 + 1) * 16 - width - 1;
                this.xa = 0;
            }
            if (ya < 0) {
                y = (int) ((y - height) / 16) * 16 + height;
                this.ya = 0;
            }
            if (ya > 0) {
                y = (int) (y / 16 + 1) * 16 - 1;
                onGround = true;
            }
            return false;
        } else {
            x += xa;
            y += ya;
            return true;
        }
    }

    private boolean isBlocking(float _x, float _y, float xa, float ya) {
        int x = (int) (_x / 16);
        int y = (int) (_y / 16);
        if (x == (int) (this.x / 16) && y == (int) (this.y / 16))
            return false;

        boolean blocking = world.level.isBlocking(x, y, xa, ya);

        return blocking;
    }

    @Override
    public void update() {
        if (!this.alive) {
            return;
        }

        float sideWaysSpeed = 8f;
        if (xa > 2) {
            facing = 1;
        }
        if (xa < -2) {
            facing = -1;
        }
        xa = facing * sideWaysSpeed;

        world.checkFireballCollide(this);

        if (!move(xa, 0)) {
            this.world.removeSprite(this);
            return;
        }

        onGround = false;
        move(0, ya);
        if (onGround)
            ya = -10;

        ya *= 0.95f;
        if (onGround) {
            xa *= GROUND_INERTIA;
        } else {
            xa *= AIR_INERTIA;
        }

        if (!onGround) {
            ya += 1.5;
        }
    }
}
