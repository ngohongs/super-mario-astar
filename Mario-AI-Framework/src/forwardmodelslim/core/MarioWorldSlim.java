package forwardmodelslim.core;

//TODO: there is a bullet bill spawner - needs attention

import engine.core.MarioSprite;
import engine.core.MarioWorld;
import engine.helper.GameStatus;
import forwardmodelslim.level.SpriteTypeSlim;
import engine.sprites.*;
import forwardmodelslim.level.LevelPart;
import forwardmodelslim.level.MarioLevelSlim;
import forwardmodelslim.level.TileFeaturesSlim;
import forwardmodelslim.sprites.*;

import java.util.ArrayList;

public class MarioWorldSlim {
    private GameStatus gameStatus;
    public int pauseTimer;
    private int currentTimer;
    public float cameraX;
    public float cameraY;
    public MarioSlim mario;
    public MarioLevelSlim level;
    private int currentTick;
    public int coins, lives;

    private ArrayList<MarioSpriteSlim> sprites;

    private MarioWorldSlim() { }

    MarioWorldSlim(MarioWorld originalWorld, int levelCutoutTileWidth) {
        this.gameStatus = originalWorld.gameStatus;
        this.pauseTimer = originalWorld.pauseTimer;
        this.currentTimer = originalWorld.currentTimer;
        this.cameraX = originalWorld.cameraX;
        this.cameraY = originalWorld.cameraY;
        this.currentTick = originalWorld.currentTick;
        this.coins = originalWorld.coins;
        this.lives = originalWorld.lives;

        sprites = new ArrayList<>();

        for (MarioSprite originalSprite : originalWorld.sprites) {
            if (originalSprite instanceof BulletBill)
                this.sprites.add(new BulletBillSlim((BulletBill) originalSprite));
            else if (originalSprite instanceof FlowerEnemy)
                this.sprites.add(new FlowerEnemySlim((FlowerEnemy) originalSprite));
            else if (originalSprite instanceof Enemy)
                this.sprites.add(new EnemySlim((Enemy) originalSprite));
            else if (originalSprite instanceof Fireball)
                this.sprites.add(new FireballSlim((Fireball) originalSprite));
            else if (originalSprite instanceof FireFlower)
                this.sprites.add(new FireFlowerSlim((FireFlower) originalSprite));
            else if (originalSprite instanceof LifeMushroom)
                this.sprites.add(new LifeMushroomSlim((LifeMushroom) originalSprite));
            else if (originalSprite instanceof Mario) {
                mario = new MarioSlim((Mario) originalSprite);
                this.sprites.add(mario);
            }
            else if (originalSprite instanceof Mushroom)
                this.sprites.add(new MushroomSlim((Mushroom) originalSprite));
            else if (originalSprite instanceof Shell)
                this.sprites.add(new ShellSlim((Shell) originalSprite));
            else
                throw new IllegalArgumentException();
        }

        // minimum width because world.update method might look this far
        // TODO: is this large enough?
        if (levelCutoutTileWidth < 27)
            levelCutoutTileWidth = 27;

        if (this.mario == null) {
            this.mario = new MarioSlim(originalWorld.mario);
            this.sprites.add(mario);
        }

        this.level = new MarioLevelSlim(originalWorld.level, levelCutoutTileWidth, (int) mario.x / 16);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarioWorldSlim that = (MarioWorldSlim) o;
        boolean worldProperties = pauseTimer == that.pauseTimer &&
                currentTimer == that.currentTimer &&
                Float.compare(that.cameraX, cameraX) == 0 &&
                Float.compare(that.cameraY, cameraY) == 0 &&
                currentTick == that.currentTick &&
                coins == that.coins &&
                lives == that.lives &&
                gameStatus == that.gameStatus;
        if (worldProperties)
            System.out.println("WORLD PROPERTIES EQUAL");
        else
            System.out.println("WORLD PROPERTIES NOT EQUAL");

        // align level cutouts if game is not running
        if (this.gameStatus != GameStatus.RUNNING)
            this.level.update((int) this.mario.x / 16);
        if (that.gameStatus != GameStatus.RUNNING)
            that.level.update((int) that.mario.x / 16);

        return worldProperties & level.equals(that.level) &
                areSpritesEqual(this.sprites, that.sprites);
    }

    private boolean areSpritesEqual(ArrayList<MarioSpriteSlim> sprites1, ArrayList<MarioSpriteSlim> sprites2) {
        for (MarioSpriteSlim sprite1 : sprites1) {
            boolean found = false;
            System.out.println("  Comparing sprite " + sprite1);
            for (MarioSpriteSlim sprite2 : sprites2) {
                if (sprite1.equals(sprite2)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("SPRITES NOT EQUAL");
                System.out.println("    Sprite not equal: " + sprite1);
                return false;
            }
        }
        System.out.println("SPRITES EQUAL");
        return true;
    }

    public MarioWorldSlim clone() {
        MarioWorldSlim clone = new MarioWorldSlim();
        clone.gameStatus = this.gameStatus;
        clone.pauseTimer = this.pauseTimer;
        clone.currentTimer = this.currentTimer;
        clone.cameraX = this.cameraX;
        clone.cameraY = this.cameraY;
        clone.currentTick = this.currentTick;
        clone.coins = this.coins;
        clone.lives = this.lives;

        clone.level = this.level.clone();

        clone.sprites = new ArrayList<>();

        for (MarioSpriteSlim sprite : this.sprites) {
            MarioSpriteSlim spriteClone = sprite.clone();
            clone.sprites.add(spriteClone);
            if (spriteClone.getType() == SpriteTypeSlim.MARIO)
                clone.mario = (MarioSlim) spriteClone;
        }

        if (clone.mario == null)
            clone.mario = (MarioSlim) this.mario.clone();

        return clone;
    }

    public ArrayList<MarioSpriteSlim> getEnemies() {
        ArrayList<MarioSpriteSlim> enemies = new ArrayList<>();
        for (MarioSpriteSlim sprite : sprites) {
                if (this.isEnemy(sprite)) {
                    enemies.add(sprite);
                }
        }
        return enemies;
    }

    public void addSprite(MarioSpriteSlim sprite, MarioUpdateContext updateContext) {
        updateContext.addedSprites.add(sprite);
        sprite.alive = true;
        sprite.update(updateContext);
    }

    public void removeSprite(MarioSpriteSlim sprite, MarioUpdateContext updateContext) {
        updateContext.removedSprites.add(sprite);
        sprite.alive = false;
    }

    public void win() {
        this.gameStatus = GameStatus.WIN;
    }

    public void lose() {
        this.gameStatus = GameStatus.LOSE;
        this.mario.alive = false;
    }

    private void timeout() {
        this.gameStatus = GameStatus.TIME_OUT;
        this.mario.alive = false;
    }

    private boolean isEnemy(MarioSpriteSlim sprite) {
        return sprite instanceof EnemySlim || sprite instanceof FlowerEnemySlim || sprite instanceof BulletBillSlim;
    }

    public void update(boolean[] actions) {
        if (this.gameStatus != GameStatus.RUNNING) {
            return;
        }
        if (this.pauseTimer > 0) {
            this.pauseTimer -= 1;
            return;
        }

        if (this.currentTimer > 0) {
            this.currentTimer -= 30;
            if (this.currentTimer <= 0) {
                this.currentTimer = 0;
                this.timeout();
                return;
            }
        }

        MarioUpdateContext updateContext = MarioUpdateContext.get();
        updateContext.world = this;

        // workaround the nonexistence of MarioGame here
        int marioGameWidth = 256;
        int marioGameHeight = 256;

        this.currentTick += 1;
        this.cameraX = this.mario.x - marioGameWidth / 2;
        if (this.cameraX + marioGameWidth > this.level.width) {
            this.cameraX = this.level.width - marioGameWidth;
        }
        if (this.cameraX < 0) {
            this.cameraX = 0;
        }
        this.cameraY = this.mario.y - marioGameHeight / 2;
        if (this.cameraY + marioGameHeight > this.level.height) {
            this.cameraY = this.level.height - marioGameHeight;
        }
        if (this.cameraY < 0) {
            this.cameraY = 0;
        }

        updateContext.fireballsOnScreen = 0;
        for (MarioSpriteSlim sprite : sprites) {
            if (sprite.x < cameraX - 64 || sprite.x > cameraX + marioGameWidth + 64 || sprite.y > this.level.height + 32) {
                if (sprite.getType() == SpriteTypeSlim.MARIO) {
                    this.lose();
                }
                this.removeSprite(sprite, updateContext);
                continue;
            }
            if (sprite.getType() == SpriteTypeSlim.FIREBALL) {
                updateContext.fireballsOnScreen += 1;
            }
        }

        for (int x = (int) cameraX / 16 - 1; x <= (int) (cameraX + marioGameWidth) / 16 + 1; x++) {
            for (int y = (int) cameraY / 16 - 1; y <= (int) (cameraY + marioGameHeight) / 16 + 1; y++) {
                int dir = 0;
                if (x * 16 + 8 > mario.x + 16)
                    dir = -1;
                if (x * 16 + 8 < mario.x - 16)
                    dir = 1;

                SpriteTypeSlim spriteType = level.getSpriteType(x, y);
                if (spriteType != SpriteTypeSlim.NONE) {
                    MarioSpriteSlim newSprite = this.spawnEnemy(spriteType, x, y, dir);
                    this.addSprite(newSprite, updateContext);
                    level.setBlock(x, y, 0); // remove sprite when it is spawned
                }

                if (dir != 0) {
                    if (this.level.getBlockValue(x, y) == LevelPart.BULLET_BILL_CANNON.getValue()) {
                        if (this.currentTick % 100 == 0) {
                            addSprite(new BulletBillSlim(x * 16 + 8 + dir * 8, y * 16 + 15, dir), updateContext);
                        }
                    }
                }
            }
        }

        updateContext.actions = actions;

        for (MarioSpriteSlim sprite : sprites) {
            if (!sprite.alive) {
                continue;
            }
            sprite.update(updateContext);
        }
        for (MarioSpriteSlim sprite : sprites) {
            if (!sprite.alive) {
                continue;
            }
            sprite.collideCheck(updateContext);
        }

        for (ShellSlim shell : updateContext.shellsToCheck) {
            for (MarioSpriteSlim sprite : sprites) {
                if (sprite != shell && shell.alive && sprite.alive) {
                    if (sprite.shellCollideCheck(shell, updateContext)) {
                        this.removeSprite(sprite, updateContext);
                    }
                }
            }
        }
        updateContext.shellsToCheck.clear();

        for (FireballSlim fireball : updateContext.fireballsToCheck) {
            for (MarioSpriteSlim sprite : sprites) {
                if (sprite != fireball && fireball.alive && sprite.alive) {
                    if (sprite.fireballCollideCheck(fireball, updateContext)) {
                        this.removeSprite(fireball, updateContext);
                    }
                }
            }
        }
        updateContext.fireballsToCheck.clear();

        this.level.update((int) mario.x / 16);

        sprites.addAll(0, updateContext.addedSprites);
        sprites.removeAll(updateContext.removedSprites);
        updateContext.addedSprites.clear();
        updateContext.removedSprites.clear();

        updateContext.world = null;
        updateContext.actions = null;
        updateContext.fireballsOnScreen = 0;
        MarioUpdateContext.back(updateContext);
    }

    private MarioSpriteSlim spawnEnemy(SpriteTypeSlim type, int x, int y, int dir) {
        if (type == SpriteTypeSlim.ENEMY_FLOWER) {
            // flower enemy constructor needs to call update - which uses world
            MarioUpdateContext updateContext = MarioUpdateContext.get();
            updateContext.world = this;

            FlowerEnemySlim flowerEnemy = new FlowerEnemySlim(x * 16 + 17, y * 16 + 18, updateContext);

            updateContext.world = null;
            MarioUpdateContext.back(updateContext);

            return flowerEnemy;
        }
        else
            return new EnemySlim(x * 16 + 8, y * 16 + 15, dir, type);
    }

    public void bump(int xTile, int yTile, boolean canBreakBricks, MarioUpdateContext updateContext) {
        byte blockValue = this.level.getBlockValue(xTile, yTile);
        ArrayList<TileFeaturesSlim> features = TileFeaturesSlim.getTileFeatures(blockValue);

        if (features.contains(TileFeaturesSlim.BUMPABLE)) {
            bumpInto(xTile, yTile - 1, updateContext);
            level.setBlock(xTile, yTile, 14);

            if (features.contains(TileFeaturesSlim.SPECIAL)) {
                if (!this.mario.isLarge) {
                    addSprite(new MushroomSlim(xTile * 16 + 9, yTile * 16 + 8), updateContext);
                } else {
                    addSprite(new FireFlowerSlim(xTile * 16 + 9, yTile * 16 + 8), updateContext);
                }
            } else if (features.contains(TileFeaturesSlim.LIFE)) {
                addSprite(new LifeMushroomSlim(xTile * 16 + 9, yTile * 16 + 8), updateContext);
            } else {
                mario.collectCoin(updateContext);
            }
        }

        if (features.contains(TileFeaturesSlim.BREAKABLE)) {
            bumpInto(xTile, yTile - 1, updateContext);
            if (canBreakBricks)
                level.setBlock(xTile, yTile, 0);
        }
    }

    private void bumpInto(int xTile, int yTile, MarioUpdateContext updateContext) {
        byte blockValue = level.getBlockValue(xTile, yTile);
        if (blockValue == LevelPart.COIN.getValue()) {
            this.mario.collectCoin(updateContext);
            level.setBlock(xTile, yTile, 0);
        }

        for (MarioSpriteSlim sprite : sprites) {
            sprite.bumpCheck(xTile, yTile, updateContext);
        }
    }
}
