### Introduction
This project is based on the framework created by [Ahmed Khalifa](https://scholar.google.com/citations?user=DRcyg5kAAAAJ&hl=en), which can be found [here](https://github.com/amidos2006/Mario-AI-Framework). As a part of my bachelor thesis, I created a better forward model for this framework, and to prove its funtionality, I built a few intelligent agents on top of it.

### Requirements
I tried to modify the framework to work both and Windows and Linux and to support more working directory settings of various IDEs. It is guaranteed to work on Windows 10 with working directory set in the `Mario-AI-Framework` folder, which contains folders such as `src` and `levels`.  Java SDK version 13 might be required.

### Interesting entry points of the framework
- `AgentBenchmark`, which runs a benchmark of selected agents and outputs the results to `agent-benchmark` folder in CSV format
- `AgentMain`, which can be used to test agents from the `mff` package on different levels
- `PerformanceTests`, which contains various tests of the forward model components
- `SlimTest`, which allows a few different things:
	- `humanTest` method allows manual playing of levels
	- `correctnessTest` method will compare world simulation of the original and slim forward models on the 15 original levels
	- `advanceSpeedTest` method will compare the performance of the `advance` method of original and slim forward models

### Copyrights
This framework is not endorsed by Nintendo and is only intended for research purposes. Mario is a Nintendo character which the authors don't own any rights to. Nintendo is also the sole owner of all the graphical assets in the game. Any use of this framework is expected to be on a non-commercial basis. This framework update was created by David Šosvald as a bachelor thesis at the Faculty of Mathematics and Physics of Charles University. The framework was created by [Ahmed Khalifa](https://scholar.google.com/citations?user=DRcyg5kAAAAJ&hl=en), based on the original Mario AI Framework by [Sergey Karakovskiy](https://scholar.google.se/citations?user=6cEAqn8AAAAJ&hl=en), [Noor Shaker](https://scholar.google.com/citations?user=OK9tw1AAAAAJ&hl=en), and [Julian Togelius](https://scholar.google.com/citations?user=lr4I9BwAAAAJ&hl=en), which in turn was based on [Infinite Mario Bros](https://fantendo.fandom.com/wiki/Infinite_Mario_Bros.) by Markus Persson.
