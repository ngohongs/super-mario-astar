package mff.forwardmodel.slim.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SlimTest {
    private static String getLevel(String filepath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
            return content;
        } catch (IOException ignored) {
            // try with working directory set one folder down
        }
        try {
            content = new String(Files.readAllBytes(Paths.get("." + filepath)));
        }
        catch (IOException e) {
            System.out.println("Level couldn't be loaded, please check the path provided with regards to your working directory.");
            System.exit(1);
        }
        return content;
    }

    public static void main(String[] args) {
        //humanTest();
        //correctnessTest();
        advanceSpeedTest();
    }

    private static void humanTest() {
        MarioGameSlim game = new MarioGameSlim(false, false);
        game.playGame(getLevel("./levels/original/lvl-1.txt"), 200, 0);
    }

    private static void correctnessTest() {
        for (int i = 1; i < 16; i++) {
            MarioGameSlim game = new MarioGameSlim(true, false);
            game.runGame(new agents.robinBaumgarten.Agent(), getLevel("./levels/original/lvl-" + i + ".txt"), 200, 0, true);
        }
    }

    private static void advanceSpeedTest() {
        for (int i = 1; i < 16; i++) {
            double originalTime = 0;
            double slimTime = 0;

            for (int j = 0; j < 2; j++) {
                MarioGameSlim game = new MarioGameSlim(false, true);
                game.runGame(new agents.robinBaumgarten.Agent(), getLevel("./levels/original/lvl-" + i + ".txt"), 200, 0, true);
            }
            for (int k = 0; k < 10; k++) {
                MarioGameSlim game = new MarioGameSlim(false, true);
                TestResult testResult = game.runGame(new agents.robinBaumgarten.Agent(), getLevel("./levels/original/lvl-" + i + ".txt"), 200, 0, true);
                originalTime += testResult.originalTime;
                slimTime += testResult.slimTime;
            }

            System.out.println("Level: " + i + " original update time: " + originalTime);
            System.out.println("Level: " + i + " slim update time: " + slimTime);
            System.out.println();
            //System.out.print("Level " + i + ": ");
        }
    }
}
