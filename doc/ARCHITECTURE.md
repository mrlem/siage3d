# Architecture

This documents provides a brief overview of the engine's architecture. It aims at making it easier to understand how Siage3D works, be it to contribute, or just a get a better understanding of the API.

## Anatomy of a game

A game using Siage3D is structured as follows:

![Anatomy of a game](doc/archi-game-anatomy.png)

The 3D elements you need to display are all present in the **scene**.
Your game data can be stored in one or more global **state** instances, which will impact the scene via a mechanism of **behaviour**
(note: you can have behaviours without a global state, though).

The **scene-adapter** is where you will attach the states to the scene, and behaviours to objects in your scene.
And finally, the app entry point is an **activity**. For convenience, a base activity named SceneActivity is provided to
save your all the pain like setting up fullscreen, defining an XML layout & such.

## Scene

Siage3D scenes are a tree of **nodes**:

![Scene-graph](doc/archi-scenegraph.png)

The scene **API** comprises:

* a core API for manipulating nodes & resources
* a scene DSL to describe scenes easiy
* internals to deal with representation of resources in storage & GPU

A picture (huhu) apparently being worth a thousand words, which I'd rather not write:

```
                                                                 Storage

                                                                    ^
                                                                    |
                    +-----------------------------------+     +-----------+
                    |       +-----------+-------------+ |     | IO        |
                    |       | Nodes     | Resources   | |     |           |
                    |       |           |             | | --> | . caches  |
                    |       | . object  | . materials | |     | . loaders |
  ----------------> |       | . group   | . shapes    | |     +-----------+ 
                    | Scene | . light   | . shaders   | |     | GL        |
      +-------+     |  API  | . camera  |             | |     |           |
  --> | Scene | --> |       | . sky     |             | |     | . program |
      |  DSL  |     |       | . terrain |             | |     | . vao/vbo |
      +-------+     |       |           |             | |     | . texture |
                    |       +-----------+-------------+ |     | . fbo     |
                    +-----------------------------------+     +-----------+
                                                                    |
                                                                    v

                                                                   GPU

-----------------+-----------------------------------------+--------------------
     Description |                  API                    |   Internals
-----------------+-----------------------------------------+--------------------
```

## Rendering

TODO

## Lifecycle management

TODO

## Code

Some stats:

```
seb@nimrodel:~/Projects/siage3d$ cloc $( find -name src )
      93 text files.
      92 unique files.                              
       2 files ignored.

github.com/AlDanial/cloc v 1.82  T=0.02 s (3992.3 files/s, 182197.0 lines/s)
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Kotlin                          68            670            261           2438
XML                             18             20              3            404
GLSL                             5             85             25            247
-------------------------------------------------------------------------------
SUM:                            91            775            289           3089
-------------------------------------------------------------------------------
```

