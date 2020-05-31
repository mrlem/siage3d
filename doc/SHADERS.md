# A new approach to handling shaders

Handling shaders in a game engine is essential.
Shaders are at the center what gets rendered: each material will use a shader, though the same shader might be used in several materials. But everytime you want to add a visual effect (lighting, fog, etc), you need to alter your shader. Even post-processing effects rely on some shaders. And when you design a 3D-engine, you want developers to be able to create their own visual effects (for be it for the materials or for post-processing).

## Why?

Shaders:

- are often repetitive, in terms of code
- fail at runtime
- need inputs that might come from various elements (i.e. geometry, camera, material, environment, etc)
- need to be as simple as possible
- should be easy to create *without* altering the engine

## What others do?

### Approach 1: pre-processor

A simple low-level approach to solve some of those issues is to pre-process the shader source before sending it to the GPU.

- easy to implement
- allows basic code-reuse

### Approach 2: shader generation

A approach where you use another language to describe your shader, and generate your GLSL source from this description.

- brings compile-time checks
- may allow some code-reuse (depending on the description language)

e.g. [Danil Salakheev](https://github.com/dananas/kotlin-glsl)'s implementation

### Approach 3: nodes

A more advanced form of shader generation is to consider that a shader is composed of nodes, each performing a simple task, and to allow combining these nodes in a graph. The GLSL source is then generated from this graph.

- allows excellent code-reuse
- makes it easier to create

e.g. blender node editor, [JME](https://wiki.jmonkeyengine.org/jme3/advanced/jme3_shadernodes.html)'s thoughts

## What's the plan?

About the other approaches:

- includes are not enough
- shader generation is complex, shader nodes even more, but they are the right approach

My approach:

- step 1: add a notion of shader part, and add a notion of shader interface:
  * a part will take parameters, and may return something
  * a shader then takes inputs (based on an interface) and plugs them to some interconnected parts, and plugs their outputs to do the rendering
  * the engine will then examine the interface of the shader to determine which data it needs (and when)
- step 2: add a compiled languagee, as close as possible to create those parts

The whole thing would bring:

- code re-use
- runtime safety
- easy creation custom shaders
- less coupling between shaders & engine

## Other links of interest

- tutorials:
  * https://github.com/lettier/3d-game-shaders-for-beginners
  * https://thebookofshaders.com/
- online editors:
  * https://www.shadertoy.com/
  * https://thebookofshaders.com/edit.php
  * ... plenty others :)
