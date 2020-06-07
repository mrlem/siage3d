package org.mrlem.siage3d.sample.advanced.behaviours

import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.graph.nodes.SpatialNode
import org.mrlem.siage3d.core.behaviour.Behaviour
import org.mrlem.siage3d.sample.advanced.GameState
import kotlin.math.cos
import kotlin.math.sin

data class CircleFlyBehaviour(
    private val state: GameState,
    private val center: Vector3f,
    private val radius: Float,
    private val speed: Float
) : Behaviour() {

    private val spatialNode get() = node as? SpatialNode

    private val position = Vector3f()

    override fun update(delta: Float) {
        // calculate
        position.set(
            center.x + sin(state.time * speed) * radius,
            center.y,
            center.z + cos(state.time * speed) * radius
        )

        // impact scene graph
        spatialNode?.setTranslation(position)
    }

}
