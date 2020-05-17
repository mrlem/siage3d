# Architecture

This documents provides a brief overview of the engine's architecture. It aims at making it easier to understand how Siage3D works, be it to contribute, or just a get a better understanding of the API.

## Scene

Siage3D scenes are constituted by:

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
                    | Scene | . light   |             | |     | GL        |
      +-------+     |  API  | . camera  |             | |     |           |
  --> | Scene | --> |       | . sky     |             | |     | . shader  |
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
