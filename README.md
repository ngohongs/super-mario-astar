### Introduction
This project is based on the framework created by [Ahmed Khalifa](https://scholar.google.com/citations?user=DRcyg5kAAAAJ&hl=en), which can be found [here](https://github.com/amidos2006/Mario-AI-Framework). As a part of my bachelor thesis, I created a better forward model for this framework, and to prove its funtionality, I built a few intelligent agents on top of it.

### Requirements
I tried to modify the framework to work both and Windows and Linux and to support more working directory settings of various IDEs. It is guaranteed to work on Windows 10 with working directory set in the `Mario-AI-Framework` folder, which contains folders such as `src` and `levels`.  Java SDK version 13 might be required.

### Project overview

- `agent-benchmark` - an output folder for agents benchmark results
- `img` - graphical assets of the game
- `levels` - original and generated levels
- `src` - source files of the framework
  - `agents` - agents from the framework mostly created during competitions
  - `engine` - original game implementation and forward model
  - `levelGenerators`
    -  generators from the framework mostly created during competitions
    - the `krys` and `noiseBased` generators were created by MFF UK students Jan Holan and Mikuláš Hrdlička as a part of the Procedural Content Generation course
  - `mff` - the source code of my thesis
    - `agents` - all the agents created for the thesis + a benchmark environment for them
    - `forwardmodel` - contains the two new forward models
      - `slim` - which is an improved version of the original forward model
      - `bin` - which is an experimental model that isn't finished

### Interesting entry points of the framework
- `src/mff/agents/benchmark/AgentBenchmark`, which runs a benchmark of selected agents and outputs the results to `agent-benchmark` folder in CSV format
- `src/mff/agents/common/AgentMain`, which can be used to test agents from the `mff` package on different levels
- `src/mff/forwardmodel/common/PerformanceTests`, which contains various tests of the forward model components
- `src/mff/forwardmodel/slim/core/SlimTest`, which allows a few different things:
	- `humanTest` method allows manual playing of levels
	- `correctnessTest` method will compare world simulation of the original and slim forward models on the 15 original levels
	- `advanceSpeedTest` method will compare the performance of the `advance` method of original and slim forward models

### Copyrights
This framework is not endorsed by Nintendo and is only intended for research purposes. Mario is a Nintendo character which the authors don't own any rights to. Nintendo is also the sole owner of all the graphical assets in the game. Any use of this framework is expected to be on a non-commercial basis. This framework update was created by David Šosvald as a bachelor thesis at the Faculty of Mathematics and Physics of Charles University. The framework was created by [Ahmed Khalifa](https://scholar.google.com/citations?user=DRcyg5kAAAAJ&hl=en), based on the original Mario AI Framework by [Sergey Karakovskiy](https://scholar.google.se/citations?user=6cEAqn8AAAAJ&hl=en), [Noor Shaker](https://scholar.google.com/citations?user=OK9tw1AAAAAJ&hl=en), and [Julian Togelius](https://scholar.google.com/citations?user=lr4I9BwAAAAJ&hl=en), which in turn was based on [Infinite Mario Bros](https://fantendo.fandom.com/wiki/Infinite_Mario_Bros.) by Markus Persson.
