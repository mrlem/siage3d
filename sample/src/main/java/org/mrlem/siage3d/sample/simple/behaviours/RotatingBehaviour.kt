package org.mrlem.siage3d.sample.simple.behaviours

import org.mrlem.siage3d.core.scene.graph.nodes.SpatialNode
import org.mrlem.siage3d.core.state.Behaviour

class RotatingBehaviour : Behaviour() {

    private val spatialNode get() = node as? SpatialNode
    private var time = 0f

    override fun update(delta: Float) {
        time += delta
        spatialNode?.setRotation(0f, time * 50f, 0f)
    }

}
