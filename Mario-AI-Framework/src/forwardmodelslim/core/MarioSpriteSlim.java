package forwardmodelslim.core;

import forwardmodelslim.level.SpriteTypeSlim;
import forwardmodelslim.sprites.FireballSlim;
import forwardmodelslim.sprites.ShellSlim;

public abstract class MarioSpriteSlim {
    public float x, y;
    public boolean alive;

    public abstract SpriteTypeSlim getType();
    public abstract void update(MarioUpdateContext updateContext);

    public void collideCheck(MarioUpdateContext updateContext) { }
    public void bumpCheck(int xTile, int yTile, MarioUpdateContext updateContext) { }
    public boolean shellCollideCheck(ShellSlim shell, MarioUpdateContext updateContext) { return false; }
    public boolean fireballCollideCheck(FireballSlim fireball, MarioUpdateContext updateContext) { return false; }

    public abstract MarioSpriteSlim clone();
}
