package forwardmodelslimOOP;

import engine.helper.SpriteType;

public class FireFlowerSlim extends MarioSpriteSlim {
    private static final int width = 4;
    private static final int height = 12;
    private static final SpriteType type = SpriteType.FIRE_FLOWER;

    private int life;

    public FireFlowerSlim(float x, float y) {
        this.x = x;
        this.y = y;
        this.life = 0;
    }

    @Override
    public SpriteType getType() {
        return type;
    }

    @Override
    public MarioSpriteSlim clone() {
        /*FireFlower f = new FireFlower(false, x, y);
        f.xa = this.xa;
        f.ya = this.ya;
        f.initialCode = this.initialCode;
        f.width = this.width;
        f.height = this.height;
        f.facing = this.facing;
        f.life = this.life;
        return f;*/
        return null;
    }

    @Override
    public void collideCheck() {
        if (!this.alive) {
            return;
        }

        float xMarioD = world.mario.x - x;
        float yMarioD = world.mario.y - y;
        if (xMarioD > -16 && xMarioD < 16) {
            if (yMarioD > -height && yMarioD < world.mario.height) {
                //world.addEvent(EventType.COLLECT, this.type.getValue());
                world.mario.getFlower();
                world.removeSprite(this);
            }
        }
    }

    @Override
    public void update() {
        if (!this.alive) {
            return;
        }

       life++;
        if (life < 9) {
            this.y--;
        }
    }
}
