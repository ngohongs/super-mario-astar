package forwardmodelslim.core;

import engine.core.MarioForwardModel;

class Converter {
    static MarioForwardModelSlim convert(MarioForwardModel originalModel, int levelCutoutTileWidth) {
        MarioWorldSlim marioWorldSlim = new MarioWorldSlim(originalModel.world, levelCutoutTileWidth);
        return new MarioForwardModelSlim(marioWorldSlim);
    }
}
