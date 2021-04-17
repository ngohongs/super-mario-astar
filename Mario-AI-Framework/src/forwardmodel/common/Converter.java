package forwardmodel.common;

import engine.core.MarioForwardModel;
import forwardmodel.bin.core.MarioBinData;
import forwardmodel.bin.core.MarioForwardModelBin;
import forwardmodel.slim.core.MarioForwardModelSlim;
import forwardmodel.slim.core.MarioWorldSlim;

public class Converter {
    public static MarioForwardModelSlim originalToSlim(MarioForwardModel originalModel, int levelCutoutTileWidth) {
        MarioWorldSlim marioWorldSlim = new MarioWorldSlim(originalModel.getWorld(), levelCutoutTileWidth);
        return new MarioForwardModelSlim(marioWorldSlim);
    }

    public static MarioForwardModelBin slimToBin(MarioForwardModelSlim slimModel) {
        MarioBinData marioBinData = new MarioBinData(slimModel.getWorld());
        return new MarioForwardModelBin(marioBinData);
    }
}
