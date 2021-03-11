package forwardmodelslimOOP;

import engine.helper.SpriteType;

class BulletBillSlim extends MarioSpriteSlim {
    static final SpriteType type = SpriteType.BULLET_BILL;
    static final int width = 4;
    static final int height = 12;

    byte facing;

    public BulletBillSlim(float x, float y, int facing) {
        this.x = x;
        this.y = y;
        this.facing = (byte) facing;
    }

    @Override
    public SpriteType getType() {
        return type;
    }

    @Override
    public void update() {
        if (!alive) return;
        x += facing * 4f;
    }

    @Override
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

    @Override
    public boolean fireballCollideCheck(FireballSlim fireball) {
        if (!alive) return false;

        float xD = fireball.x - x;
        float yD = fireball.y - y;

        if (xD > -16 && xD < 16)
            return yD > -height && yD < FireballSlim.height;
        else
            return false;
    }

    @Override
    public boolean shellCollideCheck(ShellSlim shell) {
        if (!alive) return false;

        float xD = shell.x - x;
        float yD = shell.y - y;

        if (xD > -16 && xD < 16) {
            if (yD > -height && yD < ShellSlim.height) {
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

