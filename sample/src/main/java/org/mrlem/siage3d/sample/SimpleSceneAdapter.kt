package org.mrlem.siage3d.sample

import org.mrlem.siage3d.core.scene.ObjectNode
import org.mrlem.siage3d.core.scene.dsl.*
import org.mrlem.siage3d.core.scene.lights.DirectionLight
import org.mrlem.siage3d.core.scene.materials.TextureMaterial
import org.mrlem.siage3d.core.scene.rotate
import org.mrlem.siage3d.core.scene.shapes.Box
import org.mrlem.siage3d.core.scene.shapes.Square
import org.mrlem.siage3d.core.view.SceneAdapter

/**
 * This is a simple sample: just a rotating cube in front of us.
 */
class SimpleSceneAdapter : SceneAdapter() {

    private val cube by lazy { scene.get<ObjectNode>("my-cube")!! }
    private val sun by lazy { scene.get<DirectionLight>("sun")!! }
    private var time = 0f

    override fun onSceneCreate() = scene {
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

        objectNode("my-cube", Box()) {
            material { texture(R.drawable.crate1_diffuse) }
            position(0f, 1f, -2f)
        }

        material("shadowMap") {}

        objectNode("preview", Square()) {
            material("shadowMap")
            position(-.3f, 2.7f, 2f)
            scale(0.5f)
        }
    }

    override fun onSceneCreated() {
        // preview shadow-map
        scene.materials
            .filterIsInstance(TextureMaterial::class.java)
            .first { it.name == "shadowMap" }
            .apply { texture = sun.shadowMap.texture }
    }

    override fun onUpdate(delta: Float) {
        time += delta
        cube.rotate(0f, time * 50f, 0f)
    }

}
