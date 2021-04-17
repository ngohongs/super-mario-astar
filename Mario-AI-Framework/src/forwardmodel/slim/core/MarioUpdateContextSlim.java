package forwardmodel.slim.core;

import forwardmodel.slim.sprites.FireballSlim;
import forwardmodel.slim.sprites.ShellSlim;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MarioUpdateContextSlim {

    public MarioWorldSlim world;
    public boolean[] actions;
    public int fireballsOnScreen;

    public final ArrayList<FireballSlim> fireballsToCheck = new ArrayList<>();
    public final ArrayList<ShellSlim> shellsToCheck = new ArrayList<>();
    final ArrayList<MarioSpriteSlim> addedSprites = new ArrayList<>();
    final ArrayList<MarioSpriteSlim> removedSprites = new ArrayList<>();

    private static final ConcurrentLinkedQueue<MarioUpdateContextSlim> pool = new ConcurrentLinkedQueue<>();

    public static MarioUpdateContextSlim get() {
        MarioUpdateContextSlim ctx = pool.poll();
        if (ctx != null) return ctx;
        return new MarioUpdateContextSlim();
    }

    static void back(MarioUpdateContextSlim ctx) {
        pool.add(ctx);
    }
}
