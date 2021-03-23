package forwardmodelslimOOP;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MarioUpdateContext {

    public MarioWorldSlim world;
    public boolean[] actions;
    public int fireballsOnScreen;

    public ArrayList<FireballSlim> fireballsToCheck = new ArrayList<>();
    public ArrayList<ShellSlim> shellsToCheck = new ArrayList<>();
    public ArrayList<MarioSpriteSlim> addedSprites = new ArrayList<>();
    public ArrayList<MarioSpriteSlim> removedSprites = new ArrayList<>();

    private static ConcurrentLinkedQueue<MarioUpdateContext> pool = new ConcurrentLinkedQueue<>();

    public static MarioUpdateContext get() {
        MarioUpdateContext ctx = pool.poll();
        if (ctx != null) return ctx;
        return new MarioUpdateContext();
    }

    public static void back(MarioUpdateContext ctx) {
        pool.add(ctx);
    }
}