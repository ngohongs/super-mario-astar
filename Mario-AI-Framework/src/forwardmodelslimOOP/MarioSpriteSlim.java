package forwardmodelslimOOP;

//TODO:
//sprite.update and collideCheck for every sprite
//sprites, their x and y, type

import engine.helper.SpriteType;

abstract class MarioSpriteSlim {
    public float x, y;
    public boolean alive = true;
    public MarioWorldSlim world = null;

    public abstract SpriteType getType();
    public abstract void update();

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
