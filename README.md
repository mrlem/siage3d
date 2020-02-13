# Siage3D

![Screenshot](doc/screenshot.png)

**Siage3D** stands for **SI**mple **A**ndroid **G**ame **E**ngine.

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

Create an activity for you game:

```Kotlin
class MainActivity : SceneActivity() {

    override val sceneAdapter by lazy { MainSceneAdapter() }

}
```

Declare it in your manifest:

```xml
        <activity android:name=".sample.MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
```

Create the scene adapter:

```kotlin
class MainSceneAdapter : SceneAdapter() {

    private lateinit var sampleNode: ObjectNode

    override fun onInit() = scene {
        light(
            position = position(0f, 25f, 0f),
            ambient = color(0f, 0f, 0f),
            diffuse = color(1f, 1f, .8f)
        )
        camera(
            position = position(0f, 1.75f, 5f)
        )
        sky(
            color = color(.6f, .8f, 1f),
            cubemap = textureCubemap(R.array.skybox_daylight)
        )
        objectNode(
            "My Box",
            shape = box(),
            material = textureMaterial(R.drawable.white)
        )
            .also { sampleNode = it }
    }

    override fun onUpdate(delta: Float) {
        // animate the scene
        sampleNode.localTransform.rotateY(delta)
    }

}
```

## Features

* Scene definition
  - scene-graph API
  - kotlin DSL
  - height-maps
* Object loading
  - OBJ files
* Rendering
  - Skybox
  - Distance fog
  - Lighting: multiple point lights
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
