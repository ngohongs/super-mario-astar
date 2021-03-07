package forwardmodel;

import engine.core.MarioWorld;
import engine.helper.SpriteType;
import engine.sprites.Fireball;
import engine.sprites.Shell;

class BulletBillSlim extends MarioSpriteSlim {
    static final SpriteType type = SpriteType.BULLET_BILL;
    static final int width = 4;
    static final int height = 12;

    float x;
    float y;
    short facing;
    boolean alive = true;
    MarioWorld world;

    public BulletBillSlim(float x, float y, short facing) {
        this.x = x;
        this.y = y;
        this.facing = facing;
    }

    @Override
    public void update() {
        if (!alive) return;
        x += facing * 4f;
    }

    public void collideCheck() {
        if (!alive) return;
        float xMarioD = world.mario.x - x;
        float yMarioD = world.mario.y - y;
        if (xMarioD > -16 && xMarioD < 16) {
            if (yMarioD > -height && yMarioD < world.mario.height) {
                if (world.mario.ya > 0 && yMarioD <= 0 && (!world.mario.onGround || !world.mario.wasOnGround)) {
                    world.mario.stomp(this);
                    this.world.removeSprite(this);
                } else {
                    //this.world.addEvent(EventType.HURT, this.type.getValue());
                    world.mario.getHurt();
                }
            }
        }
    }

    public boolean fireballCollideCheck(Fireball fireball) {
        if (!alive) return false;

        float xD = fireball.x - x;
        float yD = fireball.y - y;

        if (xD > -16 && xD < 16)
            return yD > -height && yD < fireball.height;
        else
            return false;
    }

    public boolean shellCollideCheck(Shell shell) {
        if (!alive) return false;

        float xD = shell.x - x;
        float yD = shell.y - y;

        if (xD > -16 && xD < 16) {
            if (yD > -height && yD < shell.height) {
                //this.world.addEvent(EventType.SHELL_KILL, this.type.getValue());
                this.world.removeSprite(this);
                return true;
            }
        }
        return false;
    }

    @Override
    public BulletBillSlim clone() {
        return null;
    }
}

