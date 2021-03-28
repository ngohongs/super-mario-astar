package forwardmodelslim.core;

import engine.core.MarioForwardModel;

public class Converter {
    public static MarioForwardModelSlim convert(MarioForwardModel originalModel, int levelCutoutTileWidth) {
        MarioWorldSlim marioWorldSlim = new MarioWorldSlim(originalModel.world, levelCutoutTileWidth);
        return new MarioForwardModelSlim(marioWorldSlim);
    }
}
