package forwardmodelslimOOP;

import engine.helper.MarioActions;
import engine.helper.SpriteType;
import engine.sprites.Mario;

public class MarioSlim extends MarioSpriteSlim {
    private static final SpriteType type = SpriteType.MARIO;
    private     static final int width = 4;
    private static final float GROUND_INERTIA = 0.89f;
    private static final float AIR_INERTIA = 0.89f;
    private static final int POWERUP_TIME = 3;

    int height = 24;
    boolean[] actions;
    private int invulnerableTime;
    boolean onGround, wasOnGround;
    boolean isLarge;
    private boolean isDucking, mayJump, canShoot, isFire, oldLarge, oldFire;
    private float xa;
    float ya;
    byte facing;
    private int jumpTime;
    private float xJumpSpeed, yJumpSpeed, xJumpStart;

    MarioSlim(Mario originalMario) {
        Mario.PrivateMarioCopyInfo info = originalMario.getPrivateCopyInfo();
        this.invulnerableTime = info.invulnerableTime;
        this.oldLarge = info.oldLarge;
        this.oldFire = info.oldFire;
        this.xJumpSpeed = info.xJumpSpeed;
        this.yJumpSpeed = info.yJumpSpeed;
        this.xJumpStart = info.xJumpStart;

        this.x = originalMario.x;
        this.y = originalMario.y;
        this.height = originalMario.height;
        this.actions = originalMario.actions;
        this.wasOnGround = originalMario.wasOnGround;
        this.onGround = originalMario.onGround;
        this.isDucking = originalMario.isDucking;
        this.isLarge = originalMario.isLarge;
        this.mayJump = originalMario.mayJump;
        this.canShoot = originalMario.canShoot;
        this.isFire = originalMario.isFire;
        this.xa = originalMario.xa;
        this.ya = originalMario.ya;
        this.facing = (byte) originalMario.facing;
        this.jumpTime = originalMario.jumpTime;
    }

    @Override
    public SpriteType getType() {
        return type;
    }

    @Override
    public void update() {
        if (!alive) return;

        if (invulnerableTime > 0) {
            invulnerableTime--;
        }
        this.wasOnGround = this.onGround;

        float sideWaysSpeed = actions[MarioActions.SPEED.getValue()] ? 1.2f : 0.6f;

        if (onGround) {
            isDucking = actions[MarioActions.DOWN.getValue()] && isLarge;
        }

        if (isLarge) {
            height = isDucking ? 12 : 24;
        } else {
            height = 12;
        }

        if (xa > 2) {
            facing = 1;
        }
        if (xa < -2) {
            facing = -1;
        }

        if (actions[MarioActions.JUMP.getValue()] || (jumpTime < 0 && !onGround)) {
            if (jumpTime < 0) {
                xa = xJumpSpeed;
                ya = -jumpTime * yJumpSpeed;
                jumpTime++;
            } else if (onGround && mayJump) {
                xJumpSpeed = 0;
                yJumpSpeed = -1.9f;
                jumpTime = 7;
                ya = jumpTime * yJumpSpeed;
                onGround = false;
                if (!(isBlocking(x, y - 4 - height, 0, -4) || isBlocking(x - width, y - 4 - height, 0, -4)
                        || isBlocking(x + width, y - 4 - height, 0, -4))) {
                    this.xJumpStart = this.x;
                    //this.world.addEvent(EventType.JUMP, 0);
                }
            } else if (jumpTime > 0) {
                xa += xJumpSpeed;
                ya = jumpTime * yJumpSpeed;
                jumpTime--;
            }
        } else {
            jumpTime = 0;
        }

        if (actions[MarioActions.LEFT.getValue()] && !isDucking) {
            xa -= sideWaysSpeed;
            if (jumpTime >= 0)
                facing = -1;
        }

        if (actions[MarioActions.RIGHT.getValue()] && !isDucking) {
            xa += sideWaysSpeed;
            if (jumpTime >= 0)
                facing = 1;
        }

        if (actions[MarioActions.SPEED.getValue()] && canShoot && isFire && world.fireballsOnScreen < 2) {
            world.addSprite(new FireballSlim(x + facing * 6, y - 20, facing));
        }

        canShoot = !actions[MarioActions.SPEED.getValue()];

        mayJump = onGround && !actions[MarioActions.JUMP.getValue()];

        if (Math.abs(xa) < 0.5f) {
            xa = 0;
        }

        onGround = false;
        move(xa, 0);
        move(0, ya);
        if (!wasOnGround && onGround && this.xJumpStart >= 0) {
            this.xJumpStart = -100;
        }

        if (x < 0) {
            x = 0;
            xa = 0;
        }

        if (x > world.level.exitTileX * 16) {
            x = world.level.exitTileX * 16;
            xa = 0;
            this.world.win();
        }

        ya *= 0.85f;
        if (onGround) {
            xa *= GROUND_INERTIA;
        } else {
            xa *= AIR_INERTIA;
        }

        if (!onGround) {
            ya += 3;
        }
    }

    private boolean isBlocking(float _x, float _y, float xa, float ya) {
        int xTile = (int) (_x / 16);
        int yTile = (int) (_y / 16);
        if (xTile == (int) (this.x / 16) && yTile == (int) (this.y / 16))
            return false;

        boolean blocking = world.level.isBlocking(xTile, yTile, xa, ya);
        LevelPart block = world.level.getBlock(xTile, yTile);

        if (TileFeaturesSlim.getTileType(block).contains(TileFeaturesSlim.PICKABLE)) {
            this.collectCoin();
            world.level.setBlock(xTile, yTile, 0);
        }
        if (blocking && ya < 0) {
            world.bump(xTile, yTile, isLarge);
        }
        return blocking;
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
                jumpTime = 0;
                this.ya = 0;
            }
            if (ya > 0) {
                y = (int) ((y - 1) / 16 + 1) * 16 - 1;
                onGround = true;
            }
            return false;
        } else {
            x += xa;
            y += ya;
            return true;
        }
    }

    public void stomp(EnemySlim enemy) {
        if (!this.alive) {
            return;
        }
        float targetY = enemy.y - enemy.height / 2;
        move(0, targetY - y);

        xJumpSpeed = 0;
        yJumpSpeed = -1.9f;
        jumpTime = 8;
        ya = jumpTime * yJumpSpeed;
        onGround = false;
        invulnerableTime = 1;
    }

    public void stomp(ShellSlim shell) {
        if (!this.alive) {
            return;
        }
        float targetY = shell.y - shell.height / 2;
        move(0, targetY - y);

        xJumpSpeed = 0;
        yJumpSpeed = -1.9f;
        jumpTime = 8;
        ya = jumpTime * yJumpSpeed;
        onGround = false;
        invulnerableTime = 1;
    }

    public void getHurt() {
        if (invulnerableTime > 0 || !this.alive)
            return;

        if (isLarge) {
            world.pauseTimer = 3 * POWERUP_TIME;
            this.oldLarge = this.isLarge;
            this.oldFire = this.isFire;
            if (isFire) {
                this.isFire = false;
            } else {
                this.isLarge = false;
            }
            invulnerableTime = 32;
        } else {
            if (this.world != null) {
                this.world.lose();
            }
        }
    }

    public void getFlower() {
        if (!this.alive) {
            return;
        }

        if (!isFire) {
            world.pauseTimer = 3 * POWERUP_TIME;
            this.oldFire = this.isFire;
            this.oldLarge = this.isLarge;
            this.isFire = true;
            this.isLarge = true;
        } else {
            this.collectCoin();
        }
    }

    public void getMushroom() {
        if (!this.alive) {
            return;
        }

        if (!isLarge) {
            world.pauseTimer = 3 * POWERUP_TIME;
            this.oldFire = this.isFire;
            this.oldLarge = this.isLarge;
            this.isLarge = true;
        } else {
            this.collectCoin();
        }
    }

    public void kick(ShellSlim shell) {
        if (!this.alive) {
            return;
        }

        invulnerableTime = 1;
    }

    public void stomp(BulletBillSlim bill) {
        if (!this.alive) {
            return;
        }

        float targetY = bill.y - bill.height / 2;
        move(0, targetY - y);

        xJumpSpeed = 0;
        yJumpSpeed = -1.9f;
        jumpTime = 8;
        ya = jumpTime * yJumpSpeed;
        onGround = false;
        invulnerableTime = 1;
    }

    void collect1Up() {
        if (!this.alive) {
            return;
        }

        this.world.lives++;
    }

    void collectCoin() {
        if (!this.alive) {
            return;
        }

        this.world.coins++;
        if (this.world.coins % 100 == 0) {
            collect1Up();
        }
    }

    public MarioSlim clone() {
        return null;
    }
}
