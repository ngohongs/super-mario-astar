package forwardmodelslimOOP;

import engine.core.MarioForwardModel;
import engine.core.MarioWorld;
import engine.helper.MarioActions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainSlim {
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
        MarioWorld originalWorld = new MarioWorld(null);
        originalWorld.visuals = false;
        originalWorld.initializeLevel(level, 1000 * 200);
        originalWorld.mario.isLarge = false;
        originalWorld.mario.isFire = false;
        originalWorld.update(new boolean[MarioActions.numberOfActions()]);

        // set level cutout width
        int levelCutoutTileWidth = 19;

        // create original OOP forward model
        MarioForwardModel originalModel = new MarioForwardModel(originalWorld.clone());

        // convert to slim OOP forward model
        MarioForwardModelSlim slimModel = Converter.convert(originalModel, levelCutoutTileWidth);

        // advance both models TODO: more complicated actions, test more worlds
        boolean[] actions = { false, false, false, false, false };
        originalModel.advance(actions);
        slimModel.advance(actions);

        // make a control slim model
        MarioForwardModelSlim controlSlimModel = Converter.convert(originalModel, levelCutoutTileWidth);

        // compare the two slim models
        if (slimModel.equals(controlSlimModel)) {
            System.out.println("-------------");
            System.out.println("EQUAL");
        }
        else {
            System.out.println("-------------");
            System.out.println("NOT EQUAL");
        }
    }
}
