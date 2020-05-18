# Architecture

This documents provides a brief overview of the engine's architecture. It aims at making it easier to understand how Siage3D works, be it to contribute, or just a get a better understanding of the API.

## Anatomy of a game

A game using Siage3D is structured as follows:

![Anatomy of a game](doc/archi-game-anatomy.png)

The 3D elements you need to display are all present in the **scene**.
All data that is specific to the state of your game constitutes the **world**.

Binding together the scene and the world, is the **scene-adapter**. It cares about how your world affects your scene-graph at every frame.
And finally, the app entry point is an **activity**. For convenience, a base activity named SceneActivity is provided to save your all the details like setting up fullscreen, defining an XML layout & such.

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
cloc $( find -name src )
      84 text files.
      83 unique files.
       2 files ignored.
-------------------------------------------------------------------------------
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Kotlin                          59            585            141           2291
XML                             18             20              3            404
GLSL                             5             85             25            247
-------------------------------------------------------------------------------
SUM:                            82            690            169           2942
-------------------------------------------------------------------------------
```

