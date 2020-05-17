package org.mrlem.siage3d.sample.simple

import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.view.SceneAdapter

/**
 * This is a simple sample: just a rotating cube in front of us.
 */
class SceneAdapter : SceneAdapter() {

    private val cube by lazy{ scene.get<ObjectNode>("my-cube")!! }
    private var time = 0f

    override fun onSceneCreate() = initialScene

    override fun onUpdate(delta: Float) {
        time += delta
        cube.rotate(0f, time * 50f, 0f)
    }

}
