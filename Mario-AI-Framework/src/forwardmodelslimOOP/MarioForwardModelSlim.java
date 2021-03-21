package forwardmodelslimOOP;

public class MarioForwardModelSlim {

    private MarioWorldSlim world;

    MarioForwardModelSlim(MarioWorldSlim world) {
        this.world = world;
    }

    public void advance(boolean[] actions) {
        this.world.update(actions);
    }

    public MarioForwardModelSlim clone() {
        return null;
    }
}
