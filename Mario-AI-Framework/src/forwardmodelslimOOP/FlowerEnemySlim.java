package forwardmodelslimOOP;

import engine.helper.SpriteType;

public class FlowerEnemySlim extends MarioSpriteSlim {
    static final SpriteType type = SpriteType.ENEMY_FLOWER;
    static final int width = 2;

    MarioWorldSlim world;
    private float yStart;
    private int tick, waitTime;
    private boolean alive = true;
    private float x, y, ya;

    public FlowerEnemySlim(float x, float y) {
        this.x = x;
        this.y = y;
        this.yStart = this.y;
        this.ya = -1;
        this.y -= 1;
        for (int i = 0; i < 4; i++) {
            this.update();
        }
    }

    @Override
    public MarioSpriteSlim clone() {
        /*FlowerEnemy sprite = new FlowerEnemy(false, this.x, this.y);
        sprite.xa = this.xa;
        sprite.ya = this.ya;
        sprite.initialCode = this.initialCode;
        sprite.width = this.width;
        sprite.height = this.height;
        sprite.onGround = this.onGround;
        sprite.winged = this.winged;
        sprite.avoidCliffs = this.avoidCliffs;
        sprite.noFireballDeath = this.noFireballDeath;
        sprite.yStart = yStart;
        sprite.waitTime = waitTime;
        return sprite;*/
        return null;
    }

    @Override
    public void update() {
        if (!this.alive) {
            return;
        }

        if (ya > 0) {
            if (y >= yStart) {
                y = yStart;
                int xd = (int) (Math.abs(world.mario.x - x));
                waitTime++;
                if (waitTime > 40 && xd > 24) {
                    waitTime = 0;
                    ya = -1;
                }
            }
        } else if (ya < 0) {
            if (yStart - y > 20) {
                y = yStart - 20;
                waitTime++;
                if (waitTime > 40) {
                    waitTime = 0;
                    ya = 1;
                }
            }
        }
        y += ya;
    }
}
