package forwardmodel.bin.sprites;

import forwardmodel.bin.core.MarioBinData;

public class FireballBin {

    public static boolean getAlive(MarioBinData data, int entityIndex) {
        return data.bools[data.spriteStorageInfo[MarioBinData.BOOLS_FIREBALL_START]
                + entityIndex * MarioBinData.FIREBALL_BOOLS + MarioBinData.FIREBALL_ALIVE];
    }

}
