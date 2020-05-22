package org.mrlem.siage3d.sample.simple

import org.mrlem.siage3d.core.scene.graph.Scene
import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.view.SceneAdapter
import org.mrlem.siage3d.sample.simple.behaviours.RotatingBehaviour

/**
 * This is a simple sample: just a rotating cube in front of us.
 */
class SceneAdapter(scene: Scene) : SceneAdapter(scene) {

    private val cube = scene.get<ObjectNode>("my-cube")!!

    init {
        cube.add(RotatingBehaviour())
    }

}
