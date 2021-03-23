package forwardmodelslimOOP;

import engine.helper.SpriteType;
import engine.sprites.Shell;

import java.util.ArrayList;

public class ShellSlim extends MarioSpriteSlim {
    private static final float GROUND_INERTIA = 0.89f;
    private static final float AIR_INERTIA = 0.89f;
    private static final SpriteType type = SpriteType.SHELL;
    private static final int width = 4;
    static final int height = 12;

    private boolean onGround;

    private float xa, ya;
    byte facing;

    ShellSlim(Shell originalShell) {
        this.x = originalShell.x;
        this.y = originalShell.y;
        this.xa = originalShell.xa;
        this.ya = originalShell.ya;
        this.facing = (byte) originalShell.facing;
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

    public void update(ArrayList<ShellSlim> shellsToCheck) {
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
            shellsToCheck.add(this);
        }

        if (!move(xa, 0)) {
            facing = (byte) -facing;
        }
        onGround = false;
        move(0, ya);

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
    public boolean fireballCollideCheck(FireballSlim fireball) {
        if (!this.alive) return false;

        float xD = fireball.x - x;
        float yD = fireball.y - y;

        if (xD > -16 && xD < 16) {
            if (yD > -height && yD < FireballSlim.height) {
                if (facing != 0)
                    return true;

                xa = fireball.facing * 2;
                ya = -5;
                this.world.removeSprite(this);
                return true;
            }
        }
        return false;
    }

    @Override
    public void collideCheck() {
        if (!this.alive) return;

        float xMarioD = world.mario.x - x;
        float yMarioD = world.mario.y - y;
        if (xMarioD > -16 && xMarioD < 16) {
            if (yMarioD > -height && yMarioD < world.mario.height) {
                if (world.mario.ya > 0 && yMarioD <= 0 && (!world.mario.onGround || !world.mario.wasOnGround)) {
                    world.mario.stomp(this);
                    if (facing != 0) {
                        xa = 0;
                        facing = 0;
                    } else {
                        facing = world.mario.facing;
                    }
                } else {
                    if (facing != 0) {
                        //world.addEvent(EventType.HURT, this.type.getValue());
                        world.mario.getHurt();
                    } else {
                        //world.addEvent(EventType.KICK, this.type.getValue());
                        world.mario.kick(this);
                        facing = world.mario.facing;
                    }
                }
            }
        }
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

        if (blocking && ya == 0 && xa != 0) {
            world.bump(x, y, true);
        }

        return blocking;
    }

    @Override
    public void bumpCheck(int xTile, int yTile) {
        if (!this.alive) return;

        if (x + width > xTile * 16 && x - width < xTile * 16 + 16 && yTile == (int) ((y - 1) / 16)) {
            facing = (byte) -world.mario.facing;
            ya = -10;
        }
    }

    @Override
    public boolean shellCollideCheck(ShellSlim shell) {
        if (!this.alive) return false;

        float xD = shell.x - x;
        float yD = shell.y - y;

        if (xD > -16 && xD < 16) {
            if (yD > -height && yD < height) {
                //this.world.addEvent(EventType.SHELL_KILL, this.type.getValue());
                if (this != shell) {
                    this.world.removeSprite(shell);
                }
                this.world.removeSprite(this);
                return true;
            }
        }
        return false;
    }
}
