package forwardmodelslimOOP;

import engine.helper.SpriteType;

public class EnemySlim extends  MarioSpriteSlim {
        private static final float GROUND_INERTIA = 0.89f;
        private static final float AIR_INERTIA = 0.89f;
        static final int width = 4;

        protected SpriteType type;
        protected boolean alive;

        protected float x, y, xa, ya;
        protected byte facing;

        protected int height;

        protected boolean onGround = false;
        protected boolean avoidCliffs;
        protected boolean winged;
        protected boolean noFireballDeath;
        protected MarioWorld world;

        public EnemySlim(float x, float y, int dir, SpriteType type) {
            this.x = x;
            this.y = y;
            this.type = type;
            this.height = 24;
            if (this.type != SpriteType.RED_KOOPA && this.type != SpriteType.GREEN_KOOPA
                    && this.type != SpriteType.RED_KOOPA_WINGED && this.type != SpriteType.GREEN_KOOPA_WINGED) {
                this.height = 12;
            }
            this.winged = this.type.getValue() % 2 == 1;
            this.avoidCliffs = this.type == SpriteType.RED_KOOPA || this.type == SpriteType.RED_KOOPA_WINGED;
            this.noFireballDeath = this.type == SpriteType.SPIKY || this.type == SpriteType.SPIKY_WINGED;
            this.facing = (byte) dir;
            if (this.facing == 0) {
                this.facing = 1;
            }
        }

        @Override
        public MarioSpriteSlim clone() {
            /*Enemy e = new Enemy(false, this.x, this.y, this.facing, this.type);
            e.xa = this.xa;
            e.ya = this.ya;
            e.initialCode = this.initialCode;
            e.width = this.width;
            e.height = this.height;
            e.onGround = this.onGround;
            e.winged = this.winged;
            e.avoidCliffs = this.avoidCliffs;
            e.noFireballDeath = this.noFireballDeath;
            return e;*/
            return null;
        }

        public void collideCheck() {
            if (!this.alive) {
                return;
            }

            float xMarioD = world.mario.x - x;
            float yMarioD = world.mario.y - y;
            if (xMarioD > -width * 2 - 4 && xMarioD < width * 2 + 4) {
                if (yMarioD > -height && yMarioD < world.mario.height) {
                    if (type != SpriteType.SPIKY && type != SpriteType.SPIKY_WINGED && type != SpriteType.ENEMY_FLOWER &&
                            world.mario.ya > 0 && yMarioD <= 0 && (!world.mario.onGround || !world.mario.wasOnGround)) {
                        world.mario.stomp(this);
                        if (winged) {
                            winged = false;
                            ya = 0;
                        } else {
                            if (type == SpriteType.GREEN_KOOPA || type == SpriteType.GREEN_KOOPA_WINGED) {
                                this.world.addSprite(new Shell(this.graphics != null, x, y, 1, this.initialCode));
                            } else if (type == SpriteType.RED_KOOPA || type == SpriteType.RED_KOOPA_WINGED) {
                                this.world.addSprite(new Shell(this.graphics != null, x, y, 0, this.initialCode));
                            } else if (type == SpriteType.GOOMBA || type == SpriteType.GOOMBA_WINGED) {
                                //if (this.graphics != null) {
                                    //this.world.addEffect(new SquishEffect(this.x, this.y - 7));
                                }
                            }
                            this.world.addEvent(EventType.STOMP_KILL, this.type.getValue());
                            this.world.removeSprite(this);
                        }
                } else {
                    this.world.addEvent(EventType.HURT, this.type.getValue());
                    world.mario.getHurt();
                }
            }
        }

        @Override
        public void update() {
            if (!this.alive) {
                return;
            }

            float sideWaysSpeed = 1.75f;

            if (xa > 2) {
                facing = 1;
            }
            if (xa < -2) {
                facing = -1;
            }

            xa = facing * sideWaysSpeed;

            if (!move(xa, 0))
                facing = (byte) -facing;
            onGround = false;
            move(0, ya);

            ya *= winged ? 0.95f : 0.85f;
            if (onGround) {
                xa *= GROUND_INERTIA;
            } else {
                xa *= AIR_INERTIA;
            }

            if (!onGround) {
                if (winged) {
                    ya += 0.6f;
                } else {
                    ya += 2;
                }
            } else if (winged) {
                ya = -10;
            }
        }

        private boolean move(float xa, float ya) {
            while (xa > 8) {
                if (!move(8, 0))
                    return false;
                xa -= 8;
            }
            while (xa < -8) {
                if (!move(-8, 0))
                    return false;
                xa += 8;
            }
            while (ya > 8) {
                if (!move(0, 8))
                    return false;
                ya -= 8;
            }
            while (ya < -8) {
                if (!move(0, -8))
                    return false;
                ya += 8;
            }

            boolean collide = false;
            if (ya > 0) {
                if (isBlocking(x + xa - width, y + ya, xa, 0))
                    collide = true;
                else if (isBlocking(x + xa + width, y + ya, xa, 0))
                    collide = true;
                else if (isBlocking(x + xa - width, y + ya + 1, xa, ya))
                    collide = true;
                else if (isBlocking(x + xa + width, y + ya + 1, xa, ya))
                    collide = true;
            }
            if (ya < 0) {
                if (isBlocking(x + xa, y + ya - height, xa, ya))
                    collide = true;
                else if (collide || isBlocking(x + xa - width, y + ya - height, xa, ya))
                    collide = true;
                else if (collide || isBlocking(x + xa + width, y + ya - height, xa, ya))
                    collide = true;
            }
            if (xa > 0) {
                if (isBlocking(x + xa + width, y + ya - height, xa, ya))
                    collide = true;
                if (isBlocking(x + xa + width, y + ya - height / 2, xa, ya))
                    collide = true;
                if (isBlocking(x + xa + width, y + ya, xa, ya))
                    collide = true;

                if (avoidCliffs && onGround
                        && !world.level.isBlocking((int) ((x + xa + width) / 16), (int) ((y) / 16 + 1), xa, 1))
                    collide = true;
            }
            if (xa < 0) {
                if (isBlocking(x + xa - width, y + ya - height, xa, ya))
                    collide = true;
                if (isBlocking(x + xa - width, y + ya - height / 2, xa, ya))
                    collide = true;
                if (isBlocking(x + xa - width, y + ya, xa, ya))
                    collide = true;

                if (avoidCliffs && onGround
                        && !world.level.isBlocking((int) ((x + xa - width) / 16), (int) ((y) / 16 + 1), xa, 1))
                    collide = true;
            }

            if (collide) {
                if (xa < 0) {
                    x = (int) ((x - width) / 16) * 16 + width;
                    this.xa = 0;
                }
                if (xa > 0) {
                    x = (int) ((x + width) / 16 + 1) * 16 - width - 1;
                    this.xa = 0;
                }
                if (ya < 0) {
                    y = (int) ((y - height) / 16) * 16 + height;
                    this.ya = 0;
                }
                if (ya > 0) {
                    y = (int) (y / 16 + 1) * 16 - 1;
                    onGround = true;
                }
                return false;
            } else {
                x += xa;
                y += ya;
                return true;
            }
        }

        private boolean isBlocking(float _x, float _y, float xa, float ya) {
            int x = (int) (_x / 16);
            int y = (int) (_y / 16);
            if (x == (int) (this.x / 16) && y == (int) (this.y / 16))
                return false;

            boolean blocking = world.level.isBlocking(x, y, xa, ya);

            return blocking;
        }

        public boolean shellCollideCheck(Shell shell) {
            if (!this.alive) {
                return false;
            }

            float xD = shell.x - x;
            float yD = shell.y - y;

            if (xD > -16 && xD < 16) {
                if (yD > -height && yD < shell.height) {
                    xa = shell.facing * 2;
                    ya = -5;
                    //this.world.addEvent(EventType.SHELL_KILL, this.type.getValue());
                    this.world.removeSprite(this);
                    return true;
                }
            }
            return false;
        }

        public boolean fireballCollideCheck(Fireball fireball) {
            if (!this.alive) {
                return false;
            }

            float xD = fireball.x - x;
            float yD = fireball.y - y;

            if (xD > -16 && xD < 16) {
                if (yD > -height && yD < fireball.height) {
                    if (noFireballDeath)
                        return true;

                    xa = fireball.facing * 2;
                    ya = -5;
                    //this.world.addEvent(EventType.FIRE_KILL, this.type.getValue());
                    this.world.removeSprite(this);
                    return true;
                }
            }
            return false;
        }

        public void bumpCheck(int xTile, int yTile) {
            if (!this.alive) {
                return;
            }

            if (x + width > xTile * 16 && x - width < xTile * 16 + 16 && yTile == (int) ((y - 1) / 16)) {
                xa = -world.mario.facing * 2;
                ya = -5;
                this.world.removeSprite(this);
            }
        }
}
