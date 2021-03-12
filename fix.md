## Temp fix for Windows and Idea:

### Assets.java:

```
imageName = img + imageName;
imageName = imageName.replace('/', '\\');
imageName = imageName.replaceAll("\\\\\\.\\.", "");
File file = new File(imageName);
```

### PlayLevel.java:

```
printResults(game.playGame(getLevel("./levels/original/lvl-1.txt"), 200, 0));
```