package forwardmodelslim.core;

public class MarioForwardModelSlim {

    private MarioWorldSlim world;

    MarioForwardModelSlim(MarioWorldSlim world) {
        this.world = world;
    }

    void advance(boolean[] actions) {
        this.world.update(actions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarioForwardModelSlim that = (MarioForwardModelSlim) o;
        return world.equals(that.world);
    }

    public MarioForwardModelSlim clone() {
        return null;
    }
}
