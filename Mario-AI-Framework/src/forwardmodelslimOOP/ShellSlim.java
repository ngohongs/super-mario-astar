package forwardmodelslimOOP;

import engine.helper.SpriteType;
import engine.sprites.Shell;

public class ShellSlim extends MarioSpriteSlim {
    public static final float GROUND_INERTIA = 0.89f;
    public static final float AIR_INERTIA = 0.89f;
    private static final SpriteType type = SpriteType.SHELL;
    private static final int width = 4;
    static final int height = 12;

    private boolean onGround;

    private float xa, ya;
    int facing;

    ShellSlim(Shell originalShell) {
        this.x = originalShell.x;
        this.y = originalShell.y;
        this.xa = originalShell.xa;
        this.ya = originalShell.ya;
        this.facing = originalShell.facing;
        this.onGround = originalShell.isOnGround();
    }

    ShellSlim(float x, float y) {
        this.x = x;
        this.y = y;
        this.facing = 0;
        this.ya = -5;
        this.onGround = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShellSlim that = (ShellSlim) o;
        return  onGround == that.onGround &&
                Float.compare(that.xa, xa) == 0 &&
                Float.compare(that.ya, ya) == 0 &&
                facing == that.facing &&
                Float.compare(x, that.x) == 0 &&
                Float.compare(y, that.y) == 0 &&
                alive == that.alive;
    }

    @Override
    public SpriteType getType() {
        return type;
    }

    @Override
    public MarioSpriteSlim clone() {
        /*Shell sprite = new Shell(false, this.x, this.y, this.shellType, this.initialCode);
        sprite.xa = this.xa;
        sprite.ya = this.ya;
        sprite.width = this.width;
        sprite.height = this.height;
        sprite.facing = this.facing;
        sprite.onGround = this.onGround;
        return sprite;*/
        return null;
    }

    @Override
    public void update(MarioUpdateContext updateContext) {
        if (!this.alive) return;

        float sideWaysSpeed = 11f;

        if (xa > 2) {
            facing = 1;
        }
        if (xa < -2) {
            facing = -1;
        }

        xa = facing * sideWaysSpeed;

        if (facing != 0) {
            updateContext.shellsToCheck.add(this);
        }

        if (!move(xa, 0, updateContext)) {
            facing = -facing;
        }
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

    @Override
    public boolean fireballCollideCheck(FireballSlim fireball, MarioUpdateContext updateContext) {
        if (!this.alive) return false;

        float xD = fireball.x - x;
        float yD = fireball.y - y;

        if (xD > -16 && xD < 16) {
            if (yD > -height && yD < FireballSlim.height) {
                if (facing != 0)
                    return true;

                xa = fireball.facing * 2;
                ya = -5;
                updateContext.world.removeSprite(this, updateContext);
                return true;
            }
        }
        return false;
    }

    @Override
    public void collideCheck(MarioUpdateContext updateContext) {
        if (!this.alive) return;

        float xMarioD = updateContext.world.mario.x - x;
        float yMarioD = updateContext.world.mario.y - y;
        if (xMarioD > -16 && xMarioD < 16) {
            if (yMarioD > -height && yMarioD < updateContext.world.mario.height) {
                if (updateContext.world.mario.ya > 0 && yMarioD <= 0 && (!updateContext.world.mario.onGround || !updateContext.world.mario.wasOnGround)) {
                    updateContext.world.mario.stomp(this, updateContext);
                    if (facing != 0) {
                        xa = 0;
                        facing = 0;
                    } else {
                        facing = updateContext.world.mario.facing;
                    }
                } else {
                    if (facing != 0) {
                        //world.addEvent(EventType.HURT, this.type.getValue());
                        updateContext.world.mario.getHurt(updateContext);
                    } else {
                        //world.addEvent(EventType.KICK, this.type.getValue());
                        updateContext.world.mario.kick(this);
                        facing = updateContext.world.mario.facing;
                    }
                }
            }
        }
    }

    private boolean move(float xa, float ya, MarioUpdateContext updateContext) {
        while (xa > 8) {
            if (!move(8, 0, updateContext))
                return false;
            xa -= 8;
        }
        while (xa < -8) {
            if (!move(-8, 0, updateContext))
                return false;
            xa += 8;
        }
        while (ya > 8) {
            if (!move(0, 8, updateContext))
                return false;
            ya -= 8;
        }
        while (ya < -8) {
            if (!move(0, -8, updateContext))
                return false;
            ya += 8;
        }

        boolean collide = false;
        if (ya > 0) {
            if (isBlocking(x + xa - width, y + ya, xa, 0, updateContext))
                collide = true;
            else if (isBlocking(x + xa + width, y + ya, xa, 0, updateContext))
                collide = true;
            else if (isBlocking(x + xa - width, y + ya + 1, xa, ya, updateContext))
                collide = true;
            else if (isBlocking(x + xa + width, y + ya + 1, xa, ya, updateContext))
                collide = true;
        }
        if (ya < 0) {
            if (isBlocking(x + xa, y + ya - height, xa, ya, updateContext))
                collide = true;
            else if (collide || isBlocking(x + xa - width, y + ya - height, xa, ya, updateContext))
                collide = true;
            else if (collide || isBlocking(x + xa + width, y + ya - height, xa, ya, updateContext))
                collide = true;
        }
        if (xa > 0) {
            if (isBlocking(x + xa + width, y + ya - height, xa, ya, updateContext))
                collide = true;
            if (isBlocking(x + xa + width, y + ya - height / 2, xa, ya, updateContext))
                collide = true;
            if (isBlocking(x + xa + width, y + ya, xa, ya, updateContext))
                collide = true;

        }
        if (xa < 0) {
            if (isBlocking(x + xa - width, y + ya - height, xa, ya, updateContext))
                collide = true;
            if (isBlocking(x + xa - width, y + ya - height / 2, xa, ya, updateContext))
                collide = true;
            if (isBlocking(x + xa - width, y + ya, xa, ya, updateContext))
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

    private boolean isBlocking(float _x, float _y, float xa, float ya, MarioUpdateContext updateContext) {
        int x = (int) (_x / 16);
        int y = (int) (_y / 16);
        if (x == (int) (this.x / 16) && y == (int) (this.y / 16))
            return false;

        boolean blocking = updateContext.world.level.isBlocking(x, y, xa, ya);

        if (blocking && ya == 0 && xa != 0) {
            updateContext.world.bump(x, y, true, updateContext);
        }

        return blocking;
    }

    @Override
    public void bumpCheck(int xTile, int yTile, MarioUpdateContext updateContext) {
        if (!this.alive) return;

        if (x + width > xTile * 16 && x - width < xTile * 16 + 16 && yTile == (int) ((y - 1) / 16)) {
            facing = -updateContext.world.mario.facing;
            ya = -10;
        }
    }

    @Override
    public boolean shellCollideCheck(ShellSlim shell, MarioUpdateContext updateContext) {
        if (!this.alive) return false;

        float xD = shell.x - x;
        float yD = shell.y - y;

        if (xD > -16 && xD < 16) {
            if (yD > -height && yD < height) {
                //this.world.addEvent(EventType.SHELL_KILL, this.type.getValue());
                if (this != shell) {
                    updateContext.world.removeSprite(shell, updateContext);
                }
                updateContext.world.removeSprite(this, updateContext);
                return true;
            }
        }
        return false;
    }
}
