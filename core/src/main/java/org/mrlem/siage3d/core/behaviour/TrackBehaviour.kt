package org.mrlem.siage3d.core.behaviour

import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.graph.nodes.SpatialNode

class TrackBehaviour(private val target: SpatialNode) : Behaviour() {

    private val spatialNode get() = node as? SpatialNode
    private val tmp = Vector3f()

    override fun update(delta: Float) {
        spatialNode?.let { node ->
            tmp
                .set(target.translation)
                .sub(node.translation)
            node.lookAt(tmp)
        }
    }

}
