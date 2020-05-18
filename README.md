# Siage3D

**Siage3D** stands for **SI**mple **A**ndroid **G**ame **E**ngine.

![Screenshot](doc/readme-screenshot.png)

So it's a game engine that is:

* **Easy** to use
* **Lightweight**
* Dedicated to **Android**
* Coded in **Kotlin** using OpenGL ES

_Please note that this engine is a **work in progress**. Though feedback is always welcome, it is neither feature-complete nor production ready yet (and it might never be)._

## Getting started

### Setup

In your `build.gradle`, add the following dependency:

```groovy
implementation "org.mrlem.siage3d:core:1.0.0"
```

(once it gets published, until then: use the source :)

### Usage

Create an activity for your game:

```Kotlin
class Game : SceneActivity<World>() {

    override fun createSceneAdapter() = SceneAdapter(initialScene)

}
```

Declare it in your manifest:

```xml
        <activity android:name=".Game">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
```

Create the scene:
```kotlin
val initialScene = scene {
    camera {
        position(0f, 1.75f, 5f)
    }

    sky {
        color(.6f, .8f, 1f)
    }

    directionLight("sun") {
        diffuse(1f, 1f, 1f)
        rotation(0f, 60f, 0f)
    }

    objectNode("my-cube", BoxShape()) {
        material { texture(R.drawable.crate1_diffuse) }
        position(0f, 1f, -2f)
    }
}
```

Create the world representation:
```kotlin
class World : World {

    var time = 0f

    override fun update(delta: Float) {
        time += delta
    }

}

val world = World()
```

And finally: create the scene adapter, that's where you adapt the scene based on the world:

```kotlin
class SceneAdapter(scene: Scene) : SceneAdapter<World>(scene, world) {

    private val cube by lazy{ scene.get<ObjectNode>("my-cube")!! }

    override fun onSceneUpdate() {
        cube.rotate(0f, world.time * 50f, 0f)
    }

}
```

And voilà!

Want to know more? checkout the slightly more [advanced sample](sample/src/main/java/org/mrlem/siage3d/sample/advanced/SceneAdapter.kt)!

## Features

* Scene definition
  - scene-graph API
  - kotlin DSL
  - height-maps
* Object loading
  - OBJ files (partial)
* Rendering
  - Skybox
  - Distance fog
  - Lighting: directional light, up to 3 point-lights
  - Multi-texturing

## About

### Author

* *Sébastien Guillemin*

### License

GPLv3 see [LICENSE.md](LICENSE.md)

### Acknowlegments

Built with:

* [JOML](https://github.com/JOML-CI/JOML): Java OpenGL Math Library

Special thanks to:

* [ThinMatrix](https://www.youtube.com/user/ThinMatrix): awesome video tutorials on game programming
* [Learn OpenGL](https://learnopengl.com): excellent articles on OpenGL
