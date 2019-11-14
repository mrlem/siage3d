# Siage3D

**Siage3D** stands for **SI**mple **A**ndroid **G**ame **E**ngine.

So it's a game engine that is:

* **Easy** to use
* **Lightweight**
* Dedicated to **Android**
* Coded in **Kotlin** using OpenGL ES

## Getting started

### Setup

In your `build.gradle`, add the following dependency:

```groovy
implementation "org.mrlem.siage3d:core:1.0.0"
```

### Usage

Create an activity for you game:

```Kotlin
class MainActivity : SceneActivity() {

    override val sceneAdapter by lazy { MainSceneAdapter(resources) }

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
class MainSceneAdapter(
    private val resources: Resources
) : SceneAdapter() {

    override var scene = Scene()

    var linearVelocity = 0f
    var angularVelocity = 0f

    private lateinit var rootNode: GroupNode
    private var time = 0f

    override fun onInit() {
        val box = Box()
        val whiteMaterial = TextureMaterial(resources.readTexture2D(R.raw.white))

        // create scene
        scene.apply {
            camera.position(Vector3f(0f, 1.75f, 5f))
            sky = Sky.Skybox(resources.readTextureCubemap(R.array.skybox_daylight), Vector3f(.6f, .8f, 1f))
            clear()
            add(ObjectNode(box, whiteMaterial))
        }
    }

    override fun onUpdate(delta: Float) {
        time += delta

        // animate the scene
        val value = sin(time) * 0.5f + .5f
        rootNode.children.first().localTransform.setRotationXYZ(0f, value, 0f)
    }

}
```

## About

### Author

* *Sébastien Guillemin*

### License

TODO

### Acknowlegments

Built with:

* [JOML](https://github.com/JOML-CI/JOML): Java OpenGL Math Library

Special thanks to:

* [ThinMatrix](https://www.youtube.com/user/ThinMatrix): awesome video tutorials on game programming
* [Learn OpenGL](https://learnopengl.com): excellent articles on OpenGL
