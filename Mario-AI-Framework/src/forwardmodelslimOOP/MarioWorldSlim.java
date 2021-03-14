package forwardmodelslimOOP;

//TODO: events?
//TODO: is type even needed with concrete entities? Enemy uses it to know the concrete type, but other than that?
//TODO: there is a bullet bill spawner - needs attention

//TODO: is pauseTimer incremented somewhere?
//TODO: fireballsOnScreen is counted but never used?
//TODO: currentTimer??

import engine.helper.GameStatus;
import engine.helper.SpriteType;
import engine.helper.TileFeature;

import java.util.ArrayList;

public class MarioWorldSlim {
    public GameStatus gameStatus;
    public int pauseTimer = 0;
    public int fireballsOnScreen = 0;
    public int currentTimer = -1;
    public float cameraX;
    public float cameraY;
    public MarioSlim mario;
    public MarioLevelSlim level;
    public int currentTick;
    int coins, lives;

    private ArrayList<MarioSpriteSlim> sprites;
    private ArrayList<ShellSlim> shellsToCheck;
    private ArrayList<FireballSlim> fireballsToCheck;
    private ArrayList<MarioSpriteSlim> addedSprites;
    private ArrayList<MarioSpriteSlim> removedSprites;

    public MarioWorldSlim() {
        this.gameStatus = GameStatus.RUNNING;
        sprites = new ArrayList<>();
        shellsToCheck = new ArrayList<>();
        fireballsToCheck = new ArrayList<>();
        addedSprites = new ArrayList<>();
        removedSprites = new ArrayList<>();
    }

    /*public void initializeLevel(String level, int timer) {
        this.currentTimer = timer;
        this.level = new MarioLevel(level, this.visuals);

        this.mario = new Mario(this.visuals, this.level.marioTileX * 16, this.level.marioTileY * 16);
        this.mario.alive = true;
        this.mario.world = this;
        this.sprites.add(this.mario);
    }*/

    public MarioWorldSlim clone() {
        /*MarioWorld world = new MarioWorld(this.killEvents);
        world.visuals = false;
        world.cameraX = this.cameraX;
        world.cameraY = this.cameraY;
        world.fireballsOnScreen = this.fireballsOnScreen;
        world.gameStatus = this.gameStatus;
        world.pauseTimer = this.pauseTimer;
        world.currentTimer = this.currentTimer;
        world.currentTick = this.currentTick;
        world.level = this.level.clone();
        for (MarioSprite sprite : this.sprites) {
            MarioSprite cloneSprite = sprite.clone();
            cloneSprite.world = world;
            if (cloneSprite.type == SpriteType.MARIO) {
                world.mario = (Mario) cloneSprite;
            }
            world.sprites.add(cloneSprite);
        }
        if (world.mario == null) {
            world.mario = (Mario) this.mario.clone();
        }
        //stats
        world.coins = this.coins;
        world.lives = this.lives;
        return world;*/
        return null;
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

    public void addSprite(MarioSpriteSlim sprite) {
        this.addedSprites.add(sprite);
        sprite.alive = true;
        sprite.world = this;
        }

    public void removeSprite(MarioSpriteSlim sprite) {
        this.removedSprites.add(sprite);
        sprite.alive = false;
        sprite.world = null;
    }

    public void checkShellCollide(ShellSlim shell) {
        shellsToCheck.add(shell);
    }

    public void checkFireballCollide(FireballSlim fireball) {
        fireballsToCheck.add(fireball);
    }

    public void win() {
        this.gameStatus = GameStatus.WIN;
    }

    public void lose() {
        this.gameStatus = GameStatus.LOSE;
        this.mario.alive = false;
    }

    public void timeout() {
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

        this.fireballsOnScreen = 0;
        for (MarioSpriteSlim sprite : sprites) {
            if (sprite.x < cameraX - 64 || sprite.x > cameraX + marioGameWidth + 64 || sprite.y > this.level.height + 32) {
                if (sprite.getType() == SpriteType.MARIO) {
                    this.lose();
                }
                this.removeSprite(sprite);
                continue;
            }
            if (sprite.getType() == SpriteType.FIREBALL) {
                this.fireballsOnScreen += 1;
            }
        }

        for (int x = (int) cameraX / 16 - 1; x <= (int) (cameraX + marioGameWidth) / 16 + 1; x++) {
            for (int y = (int) cameraY / 16 - 1; y <= (int) (cameraY + marioGameHeight) / 16 + 1; y++) {
                int dir = 0;
                if (x * 16 + 8 > mario.x + 16)
                    dir = -1;
                if (x * 16 + 8 < mario.x - 16)
                    dir = 1;

                SpriteType spriteType = level.getSpriteType(x, y);
                if (spriteType != SpriteType.NONE && SpriteType.IsEnemy(spriteType)) {
                    MarioSpriteSlim newSprite = this.spawnEnemy(spriteType, x, y, dir);
                    this.addSprite(newSprite);
                    level.setBlock(x, y, 0); // remove sprite when it is spawned
                }
                //TODO: check helper classes etc., maybe rewrite them?

                if (dir != 0) {
                    ArrayList<TileFeature> features = TileFeature.getTileType(this.level.getBlock(x, y));
                    if (features.contains(TileFeature.SPAWNER)) {
                        if (this.currentTick % 100 == 0) {
                            addSprite(new BulletBillSlim(x * 16 + 8 + dir * 8, y * 16 + 15, dir));
                        }
                    }
                }
            }
        }

        this.mario.actions = actions;
        for (MarioSpriteSlim sprite : sprites) {
            if (!sprite.alive) {
                continue;
            }
            sprite.update();
        }
        for (MarioSpriteSlim sprite : sprites) {
            if (!sprite.alive) {
                continue;
            }
            sprite.collideCheck();
        }

        for (ShellSlim shell : shellsToCheck) {
            for (MarioSpriteSlim sprite : sprites) {
                if (sprite != shell && shell.alive && sprite.alive) {
                    if (sprite.shellCollideCheck(shell)) {
                        this.removeSprite(sprite);
                    }
                }
            }
        }
        shellsToCheck.clear();

        for (FireballSlim fireball : fireballsToCheck) {
            for (MarioSpriteSlim sprite : sprites) {
                if (sprite != fireball && fireball.alive && sprite.alive) {
                    if (sprite.fireballCollideCheck(fireball)) {
                        this.removeSprite(fireball);
                    }
                }
            }
        }
        fireballsToCheck.clear();

        sprites.addAll(0, addedSprites);
        sprites.removeAll(removedSprites);
        addedSprites.clear();
        removedSprites.clear();
    }

    private MarioSpriteSlim spawnEnemy(SpriteType type, int x, int y, int dir) {
        if (type == SpriteType.ENEMY_FLOWER)
            return new FlowerEnemySlim(x * 16 + 17, y * 16 + 18);
        else
            return new EnemySlim(x * 16 + 8, y * 16 + 15, dir, type);
    }

    public void bump(int xTile, int yTile, boolean canBreakBricks) {
        int block = this.level.getBlock(xTile, yTile);
        ArrayList<TileFeature> features = TileFeature.getTileType(block);

        if (features.contains(TileFeature.BUMPABLE)) {
            bumpInto(xTile, yTile - 1);
            level.setBlock(xTile, yTile, 14);

            if (features.contains(TileFeature.SPECIAL)) {
                if (!this.mario.isLarge) {
                    addSprite(new MushroomSlim(xTile * 16 + 9, yTile * 16 + 8));
                } else {
                    addSprite(new FireFlowerSlim(xTile * 16 + 9, yTile * 16 + 8));
                }
            } else if (features.contains(TileFeature.LIFE)) {
                addSprite(new LifeMushroomSlim(xTile * 16 + 9, yTile * 16 + 8));
            } else {
                mario.collectCoin();
            }
        }

        if (features.contains(TileFeature.BREAKABLE)) {
            bumpInto(xTile, yTile - 1);
            if (canBreakBricks)
                level.setBlock(xTile, yTile, 0);
        }
    }

    public void bumpInto(int xTile, int yTile) {
        int block = level.getBlock(xTile, yTile);
        if (TileFeature.getTileType(block).contains(TileFeature.PICKABLE)) {
            this.mario.collectCoin();
            level.setBlock(xTile, yTile, 0);
        }

        for (MarioSpriteSlim sprite : sprites) {
            sprite.bumpCheck(xTile, yTile);
        }
    }
}
