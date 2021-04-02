package forwardmodelslim.sprites;

import engine.sprites.Enemy;
import forwardmodelslim.core.MarioSpriteSlim;
import forwardmodelslim.core.MarioUpdateContext;
import forwardmodelslim.level.SpriteTypeSlim;

public class EnemySlim extends MarioSpriteSlim {
    private static final int GOOMBA = 2;
    private static final int GOOMBA_WINGED = 3;
    private static final int RED_KOOPA = 4;
    private static final int RED_KOOPA_WINGED = 5;
    private static final int GREEN_KOOPA = 6;
    private static final int GREEN_KOOPA_WINGED = 7;
    private static final int SPIKY = 8;
    private static final int SPIKY_WINGED = 9;

    public static final float GROUND_INERTIA = 0.89f;
    public static final float AIR_INERTIA = 0.89f;
    private static final int width = 4;

    private int typeCode;

    private float xa, ya;
    private int facing;

    int height;

    private boolean onGround;
    private boolean avoidCliffs;
    private boolean winged;
    private boolean noFireballDeath;

    private EnemySlim() { }

    public EnemySlim(Enemy originalEnemy) {
        this.x = originalEnemy.x;
        this.y = originalEnemy.y;
        this.alive = originalEnemy.alive;
        this.typeCode = originalEnemy.type.getValue();
        this.xa = originalEnemy.xa;
        this.ya = originalEnemy.ya;
        this.facing = originalEnemy.facing;
        this.height = originalEnemy.height;

        Enemy.PrivateEnemyCopyInfo info = originalEnemy.getPrivateCopyInfo();
        this.onGround = info.onGround;
        this.avoidCliffs = info.avoidCliffs;
        this.winged = info.winged;
        this.noFireballDeath = info.noFireballDeath;
    }

    public EnemySlim(float x, float y, int dir, SpriteTypeSlim type) {
        this.x = x;
        this.y = y;
        this.typeCode = convertSpriteTypeSlim(type);
        this.height = 24;
        if (this.typeCode != RED_KOOPA && this.typeCode != GREEN_KOOPA
                && this.typeCode != RED_KOOPA_WINGED && this.typeCode != GREEN_KOOPA_WINGED) {
            this.height = 12;
        }
        this.winged = this.typeCode % 2 == 1;
        this.avoidCliffs = this.typeCode == RED_KOOPA || this.typeCode == RED_KOOPA_WINGED;
        this.noFireballDeath = this.typeCode == SPIKY || this.typeCode == SPIKY_WINGED;
        this.onGround = false;
        this.facing = dir;
        if (this.facing == 0) {
            this.facing = 1;
        }
    }

    private int convertSpriteTypeSlim(SpriteTypeSlim type) {
        switch (type) {
            case GOOMBA:
                return GOOMBA;
            case GOOMBA_WINGED:
                return GOOMBA_WINGED;
            case RED_KOOPA:
                return RED_KOOPA;
            case RED_KOOPA_WINGED:
                return RED_KOOPA_WINGED;
            case GREEN_KOOPA:
                return GREEN_KOOPA;
            case GREEN_KOOPA_WINGED:
                return GREEN_KOOPA_WINGED;
            case SPIKY:
                return SPIKY;
            case SPIKY_WINGED:
                return SPIKY_WINGED;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnemySlim that = (EnemySlim) o;
        boolean equal = Float.compare(that.xa, xa) == 0 &&
                Float.compare(that.ya, ya) == 0 &&
                facing == that.facing &&
                height == that.height &&
                onGround == that.onGround &&
                avoidCliffs == that.avoidCliffs &&
                winged == that.winged &&
                noFireballDeath == that.noFireballDeath &&
                typeCode == that.typeCode &&
                Float.compare(x, that.x) == 0 &&
                Float.compare(y, that.y) == 0 &&
                alive == that.alive;
        if (equal) {
            System.out.println("    ENEMY EQUAL");
            return true;
        }
        else {
            System.out.println("    ENEMY NOT EQUAL");
            return false;
        }
    }

    @Override
    public SpriteTypeSlim getType() {
        return SpriteTypeSlim.getSpriteTypeSlim(typeCode);
    }

    public MarioSpriteSlim clone() {
        EnemySlim clone = new EnemySlim();
        clone.x = this.x;
        clone.y = this.y;
        clone.alive = this.alive;
        clone.typeCode = this.typeCode;
        clone.xa = this.xa;
        clone.ya = this.ya;
        clone.facing = this.facing;
        clone.height = this.height;
        clone.onGround = this.onGround;
        clone.avoidCliffs = this.avoidCliffs;
        clone.winged = this.winged;
        clone.noFireballDeath = this.noFireballDeath;
        return clone;
    }

    @Override
    public void collideCheck(MarioUpdateContext updateContext) {
        if (!this.alive) {
            return;
        }

        float xMarioD = updateContext.world.mario.x - x;
        float yMarioD = updateContext.world.mario.y - y;
        if (xMarioD > -width * 2 - 4 && xMarioD < width * 2 + 4) {
            if (yMarioD > -height && yMarioD < updateContext.world.mario.height) {
                if (typeCode != SPIKY && typeCode != SPIKY_WINGED && updateContext.world.mario.ya > 0 &&
                        yMarioD <= 0 && (!updateContext.world.mario.onGround || !updateContext.world.mario.wasOnGround)) {
                    updateContext.world.mario.stomp(this, updateContext);
                    if (winged) {
                        winged = false;
                        ya = 0;
                    } else {
                        if (typeCode == GREEN_KOOPA || typeCode == GREEN_KOOPA_WINGED) {
                            updateContext.world.addSprite(new ShellSlim(x, y), updateContext);
                        } else if (typeCode == RED_KOOPA || typeCode == RED_KOOPA_WINGED) {
                            updateContext.world.addSprite(new ShellSlim(x, y), updateContext);
                        }
                        updateContext.world.removeSprite(this, updateContext);
                    }
                } else {
                    updateContext.world.mario.getHurt(updateContext);
                }
            }
        }
    }

    @Override
    public void update(MarioUpdateContext updateContext) {
        if (!this.alive) {
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

        ya *= winged ? 0.95f : 0.85f;
        if (onGround) {
            xa *= GROUND_INERTIA;
        } else {
            xa *= AIR_INERTIA;
        }

        if (!onGround) {
            if (winged) {
                ya += 0.6f;
            } else {
                ya += 2;
            }
        } else if (winged) {
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
            else if (avoidCliffs && onGround &&
                    !updateContext.world.level.isBlocking((int) ((x + xa + width) / 16), (int) ((y) / 16 + 1), 1))
                collide = true;
        }
        else if (xa < 0) {
            if (isBlocking(x + xa - width, y + ya - height, ya, updateContext))
                collide = true;
            else if (isBlocking(x + xa - width, y + ya - height / 2, ya, updateContext))
                collide = true;
            else if (isBlocking(x + xa - width, y + ya, ya, updateContext))
                collide = true;
            else if (avoidCliffs && onGround
                    && !updateContext.world.level.isBlocking((int) ((x + xa - width) / 16), (int) ((y) / 16 + 1), 1))
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
            y += ya;
            return true;
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
    public boolean shellCollideCheck(ShellSlim shell, MarioUpdateContext updateContext) {
        if (!this.alive) {
            return false;
        }

        float xD = shell.x - x;
        float yD = shell.y - y;

        if (xD > -16 && xD < 16) {
            if (yD > -height && yD < ShellSlim.height) {
                xa = shell.facing * 2;
                ya = -5;
                updateContext.world.removeSprite(this, updateContext);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean fireballCollideCheck(FireballSlim fireball, MarioUpdateContext updateContext) {
        if (!this.alive) {
            return false;
        }

        float xD = fireball.x - x;
        float yD = fireball.y - y;

        if (xD > -16 && xD < 16) {
            if (yD > -height && yD < FireballSlim.height) {
                if (noFireballDeath)
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
    public void bumpCheck(int xTile, int yTile, MarioUpdateContext updateContext) {
        if (!this.alive) {
            return;
        }

        if (x + width > xTile * 16 && x - width < xTile * 16 + 16 && yTile == (int) ((y - 1) / 16)) {
            xa = -updateContext.world.mario.facing * 2;
            ya = -5;
            updateContext.world.removeSprite(this, updateContext);
        }
    }
}
