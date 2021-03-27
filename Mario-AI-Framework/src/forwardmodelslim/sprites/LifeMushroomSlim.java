package forwardmodelslim.sprites;

import engine.helper.SpriteType;
import engine.sprites.LifeMushroom;
import forwardmodelslim.core.MarioSpriteSlim;
import forwardmodelslim.core.MarioUpdateContext;

public class LifeMushroomSlim extends MarioSpriteSlim {
    public static final float GROUND_INERTIA = 0.89f;
    public static final float AIR_INERTIA = 0.89f;
    private static final SpriteType type = SpriteType.LIFE_MUSHROOM;
    private static final int width = 4;
    private static final int height = 12;

    private float xa, ya;
    private int facing;
    private boolean onGround;
    private int life;

    public LifeMushroomSlim(LifeMushroom originalLifeMushroom) {
        this.x = originalLifeMushroom.x;
        this.y = originalLifeMushroom.y;
        this.alive = originalLifeMushroom.alive;
        this.xa = originalLifeMushroom.xa;
        this.ya = originalLifeMushroom.ya;
        this.facing = originalLifeMushroom.facing;
        this.onGround = originalLifeMushroom.isOnGround();
        this.life = originalLifeMushroom.getLife();
    }

    public LifeMushroomSlim(float x, float y) {
        this.x = x;
        this.y = y;
        this.facing = 1;
        this.life = 0;
        this.onGround = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LifeMushroomSlim that = (LifeMushroomSlim) o;
        boolean equal = Float.compare(that.xa, xa) == 0 &&
                Float.compare(that.ya, ya) == 0 &&
                facing == that.facing &&
                onGround == that.onGround &&
                life == that.life &&
                Float.compare(x, that.x) == 0 &&
                Float.compare(y, that.y) == 0 &&
                alive == that.alive;
        if (equal) {
            System.out.println("    LIFE MUSHROOM EQUAL");
            return true;
        }
        else {
            System.out.println("    LIFE MUSHROOM NOT EQUAL");
            return false;
        }
    }

    @Override
    public SpriteType getType() {
        return type;
    }

    @Override
    public MarioSpriteSlim clone() {
        /*LifeMushroom m = new LifeMushroom(false, this.x, this.y);
        m.xa = this.xa;
        m.ya = this.ya;
        m.initialCode = this.initialCode;
        m.width = this.width;
        m.height = this.height;
        m.facing = this.facing;
        m.life = this.life;
        m.onGround = this.onGround;
        return m;*/
        return null;
    }

    @Override
    public void collideCheck(MarioUpdateContext updateContext) {
        if (!this.alive) {
            return;
        }

        float xMarioD = updateContext.world.mario.x - x;
        float yMarioD = updateContext.world.mario.y - y;
        if (xMarioD > -16 && xMarioD < 16) {
            if (yMarioD > -height && yMarioD < updateContext.world.mario.height) {
                updateContext.world.mario.collect1Up(updateContext);
                updateContext.world.removeSprite(this, updateContext);
            }
        }
    }

    private boolean isBlocking(float _x, float _y, float ya, MarioUpdateContext updateContext) {
        int x = (int) (_x / 16);
        int y = (int) (_y / 16);
        if (x == (int) (this.x / 16) && y == (int) (this.y / 16))
            return false;

        return updateContext.world.level.isBlocking(x, y, ya);
    }

    @Override
    public void bumpCheck(int xTile, int yTile, MarioUpdateContext updateContext) {
        if (!this.alive) {
            return;
        }

        if (x + width > xTile * 16 && x - width < xTile * 16 + 16 && yTile == (int) ((y - 1) / 16)) {
            facing = -updateContext.world.mario.facing;
            ya = -10;
        }
    }

    // either xa or ya is always zero
    private boolean move(float xa, float ya, MarioUpdateContext updateContext) {
        if (xa != 0) {
            float stepX = Math.signum(xa) * 8;
            while (Math.abs(xa) > Math.abs(stepX)) {
                xa -= stepX;
                if (!moveStepX(stepX, updateContext))
                    return false;
            }
            return moveStepX(xa, updateContext);
        } else {
            float stepY = Math.signum(ya) * 8;
            while (Math.abs(ya) > Math.abs(stepY)) {
                ya -= stepY;
                if (!moveStepY(stepY, updateContext))
                    return false;
            }
            return moveStepY(ya, updateContext);
        }
    }

    // return true if move is successful, false if blocked
    private boolean moveStepX(float xa, MarioUpdateContext updateContext) {
        float ya = 0;
        boolean collide = false;
        if (xa > 0) {
            if (isBlocking(x + xa + width, y + ya - height, ya, updateContext))
                collide = true;
            else if (isBlocking(x + xa + width, y + ya - height / 2, ya, updateContext))
                collide = true;
            else if (isBlocking(x + xa + width, y + ya, ya, updateContext))
                collide = true;
        }
        else if (xa < 0) {
            if (isBlocking(x + xa - width, y + ya - height, ya, updateContext))
                collide = true;
            else if (isBlocking(x + xa - width, y + ya - height / 2, ya, updateContext))
                collide = true;
            else if (isBlocking(x + xa - width, y + ya, ya, updateContext))
                collide = true;
        }
        if (collide) {
            if (xa < 0) {
                x = (int) ((x - width) / 16) * 16 + width;
                this.xa = 0;
            }
            else if (xa > 0) {
                x = (int) ((x + width) / 16 + 1) * 16 - width - 1;
                this.xa = 0;
            }
            return false;
        } else {
            x += xa;
            y += ya;
            return true;
        }
    }

    // return true if move is successful, false if blocked
    private boolean moveStepY(float ya, MarioUpdateContext updateContext) {
        float xa = 0;
        boolean collide = false;
        if (ya > 0) {
            if (isBlocking(x + xa - width, y + ya, 0, updateContext))
                collide = true;
            else if (isBlocking(x + xa + width, y + ya, 0, updateContext))
                collide = true;
            else if (isBlocking(x + xa - width, y + ya + 1, ya, updateContext))
                collide = true;
            else if (isBlocking(x + xa + width, y + ya + 1, ya, updateContext))
                collide = true;
        }
        else if (ya < 0) {
            if (isBlocking(x + xa, y + ya - height, ya, updateContext))
                collide = true;
            else if (isBlocking(x + xa - width, y + ya - height, ya, updateContext))
                collide = true;
            else if (isBlocking(x + xa + width, y + ya - height, ya, updateContext))
                collide = true;
        }

        if (collide) {
            if (ya < 0) {
                y = (int) ((y - height) / 16) * 16 + height;
                this.ya = 0;
            }
            else if (ya > 0) {
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

    @Override
    public void update(MarioUpdateContext updateContext) {
        if (!this.alive) {
            return;
        }

        if (life < 9) {
            y--;
            life++;
            return;
        }
        float sideWaysSpeed = 1.75f;
        if (xa > 2) {
            facing = 1;
        }
        if (xa < -2) {
            facing = -1;
        }

        xa = facing * sideWaysSpeed;

        if (!move(xa, 0, updateContext))
            facing = -facing;
        onGround = false;
        move(0, ya, updateContext);

        ya *= 0.85f;
        if (onGround) {
            xa *= GROUND_INERTIA;
        } else {
            xa *= AIR_INERTIA;
        }

        if (!onGround) {
            ya += 2;
        }
    }
}
