package forwardmodelslimOOP;

import engine.helper.SpriteType;

abstract class MarioSpriteSlim {
    public float x, y;
    public boolean alive;
    public MarioWorldSlim world;

    public abstract SpriteType getType();
    public void update() { }

    public void collideCheck() { }
    public void bumpCheck(int xTile, int yTile) { }
    public boolean shellCollideCheck(ShellSlim shell) {
        return false;
    }
    public boolean fireballCollideCheck(FireballSlim fireball) {
        return false;
    }

    public abstract MarioSpriteSlim clone();
}
