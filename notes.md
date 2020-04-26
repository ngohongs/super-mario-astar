## Bumping
### MarioWorld 
- metody **bump** a **bumpInto**
	- checkuje se, jestli je blok breakable, bumpable nebo pickable + collision check
## advance()
- volá **MarioWorld.update()** a pak zpracuje eventy
  - zpracování eventů lze odstranit - statistiky aktuálně žádný agent nevyužívá
    - nám by se mohli hodit pro metriky

- asi přepsat, aby dělala jen nezbytné věci

## update()
- **MarioWorld**
  - timery, kamera, eventy, ...
- **MarioLevel**
	- prázdná metoda
- **MarioSprite**
  - BulletBill, Fireball, FireFlower, LifeMushroom, Mario, Mushroom, Shell
  - Enemy - zahrnuje skupinu nepřátel, kteří mají stejné chování, všichni jsou tohoto typu, nemají vlastní třídu (red koopa, red koopa winged, spiky, ...)
    - FlowerEnemy extends Enemy
## clone()
### MarioForwardModel
- private int fallKill;
- private int stompKill;
- private int fireKill;
- private int shellKill;
- private int mushrooms;
- private int flowers;
- private int breakBlock;
	- zjistil jsem, že těchto 7 intů žádný agent aktuálně nepoužívá
	  - lze je úplně odstranit, nám by se ale mohli hodit pro metriky
### MarioWorld

- konstruktor
  - dostanou default hodnotu v konstruktoru, ale ta se pak přepíše:
    - pauseTimer
    - gameStatus
  - shellsToCheck, fireballsToCheck, addedSprites, removedSprites
    - ArrayListy, které se používají v update() - v clone jen vytvořené
  - effects
    - vizuální efekty
  - lastFrameEvents, killEvents
    - ?
    - killEvents možná pokud by se forward model měl úplně vyhýbat nějaké akci
- boolean visuals
  - zbytečné pro model
- float cameraX, cameraY
  - používá metoda MarioWorld.update() aby zabila sprity co jsou moc daleko mimo obrazovku
- int fireballsOnScreen
  - spíš potřeba,kontroluje se, že na obrazovce jsou max. 2
- enum gameStatus
  - potřeba
- int pauseTimer
  - zastavení hry - při sebrání power-upu nebo zranění Maria
- int currenTimer
  - čas do *time out* konce hry
- int currentTick
  - aktuální tick hry
- int coins, lives
  - počet sebraných coinů a počet životů
    - asi se hodí, pokud podle toho budeme dělat metriku

### Mario Sprites

- clone() na každém spritu - voláno z MarioWorld clone()

### MarioLevel

- int width, tileWidth, height, tileHeight
  - rozměry levelu

- int totalCoins
  - celkový počet mincí v levelu
- int marioTileX, marioTileY
  - startovní pozice Maria
- int exitTileX, exitTileY
  - pozice konce levelu
- int\[]\[] lastSpawnTime
  - pro každé políčko levelu, kdy se tam naposledy něco spawnulo
  - možná bude potřeba, aktuálně se ručně kopíruje ve for cyklu
- int\[]\[] levelTiles
  - políčka levelu - většina bude static, ale některá se za hry mění (rozbitelné bloky, ...)
  - aktuálně se ručně kopíruje ve for cyklu
- SpriteType\[]\[] spriteTemplates
  - vypadá to, že se jen předá reference, nejspíš může být static a sdílené ve všech kopiích

## MarioLevel clone()

- zkoušel jsem, jak se změní výkon, pokud by se level neclonoval vůbec
- je to zlepšení z cca 50 000 klonů/s na nějakých 1 100 000 klonů/s
- ale některá políčka nejspíš budou potřeba