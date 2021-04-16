package forwardmodel.slim.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import engine.core.MarioGame;

public class SlimTest {
    private static String getLevel(String filepath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filepath)));
        } catch (IOException ignored) {
        }
        return content;
    }

    public static void main(String[] args) {
        //humanTest();
        agentTest();
    }

    private static void humanTest() {
        MarioGame game = new MarioGame();
        game.playGame(getLevel("./levels/original/lvl-1.txt"), 200, 0);
    }

    private static void agentTest() {
        for (int i = 1; i < 16; i++) {
            MarioGame game = new MarioGame();
            game.runGame(new agents.robinBaumgarten.Agent(), getLevel("./levels/original/lvl-" + i + ".txt"), 200, 0, false);
        }
    }
}
