package org.mrlem.siage3d.sample

import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.scene.dsl.*
import org.mrlem.siage3d.core.scene.graph.resources.shapes.BoxShape
import org.mrlem.siage3d.core.view.SceneAdapter

/**
 * This is a simple sample: just a rotating cube in front of us.
 */
class SimpleSceneAdapter : SceneAdapter() {

    private val cube get() = scene.get<ObjectNode>("my-cube")!!
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

        objectNode("my-cube", BoxShape()) {
            material { texture(R.drawable.crate1_diffuse) }
            position(0f, 1f, -2f)
        }
    }

    override fun onUpdate(delta: Float) {
        time += delta
        cube.rotate(0f, time * 50f, 0f)
    }

}
