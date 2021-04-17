package forwardmodel.bin.core;

import engine.core.MarioForwardModel;
import engine.core.MarioWorld;
import engine.helper.MarioActions;
import forwardmodel.common.Converter;
import forwardmodel.slim.core.MarioForwardModelSlim;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BinBasicTest {
    private static String getLevel(String filepath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException ignored) {
        }
        return content;
    }

    public static void main(String[] args) {
        // set up original OOP world
        String level = getLevel("./levels/original/lvl-1.txt");
        MarioWorld setupWorld = new MarioWorld(null);
        setupWorld.visuals = false;
        setupWorld.initializeLevel(level, 1000 * 200);
        setupWorld.mario.isLarge = false;
        setupWorld.mario.isFire = false;
        setupWorld.update(new boolean[MarioActions.numberOfActions()]);

        // set level cutout width (0 means it will be set automatically)
        int levelCutoutTileWidth = 0;

        // create original OOP forward model
        MarioForwardModel originalModel = new MarioForwardModel(setupWorld.clone());

        // convert to slim OOP forward model
        MarioForwardModelSlim slimModel = Converter.originalToSlim(originalModel, levelCutoutTileWidth);

        // convert to bin model
        MarioForwardModelBin binModel = Converter.slimToBin(slimModel);

    }
}
