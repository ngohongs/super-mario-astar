package forwardmodelslim.core;

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
        for (int i = 1; i < 16; i++) {
            // set up original OOP world
            String level = getLevel("./levels/original/lvl-" + i + ".txt");
            MarioWorld setupWorld = new MarioWorld(null);
            setupWorld.visuals = false;
            setupWorld.initializeLevel(level, 1000 * 200);
            setupWorld.mario.isLarge = false;
            setupWorld.mario.isFire = false;
            setupWorld.update(new boolean[MarioActions.numberOfActions()]);

            // set level cutout width
            int levelCutoutTileWidth = 0;

            // create original OOP forward model
            MarioForwardModel originalModel = new MarioForwardModel(setupWorld.clone());

            // convert to slim OOP forward model
            MarioForwardModelSlim slimModel = Converter.convert(originalModel, levelCutoutTileWidth);

            // advance both models
            boolean[] actions = { false, true, false, false, false }; // left, right, down, speed, jump
            for (int j = 0; j < 1000; j++) {
                originalModel.advance(actions);
                slimModel.advance(actions);
            }

            // make a control slim model
            MarioForwardModelSlim controlSlimModel = Converter.convert(originalModel, levelCutoutTileWidth);

            // compare the two slim models
            if (slimModel.equals(controlSlimModel)) {
                System.out.println("-------------");
                System.out.println("EQUAL");
                System.out.println("-------------");
            }
            else {
                System.out.println("-------------");
                System.out.println("NOT EQUAL");
                return;
            }
        }
        System.out.println("-------------");
        System.out.println("ALL EQUAL");
    }
}
