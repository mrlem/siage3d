package org.mrlem.siage3d.sample

import org.mrlem.siage3d.core.scene.dsl.*
import org.mrlem.siage3d.core.scene.shapes.Box
import org.mrlem.siage3d.core.view.SceneAdapter

/**
 * This is a simple sample: just a cube in front of us.
 */
class SimpleSceneAdapter : SceneAdapter() {

    override fun onCreateScene() = scene {
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
            textureMaterial(R.drawable.crate1_diffuse, 1f, reflectivity = 0.1f)
            position(0f, 1.5f, -2f)
        }
    }

}
