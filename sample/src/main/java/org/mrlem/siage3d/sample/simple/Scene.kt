package org.mrlem.siage3d.sample.simple

import org.mrlem.siage3d.core.scene.dsl.scene
import org.mrlem.siage3d.core.scene.graph.resources.shapes.BoxShape
import org.mrlem.siage3d.sample.R

///////////////////////////////////////////////////////////////////////////
// Scene
///////////////////////////////////////////////////////////////////////////

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
