package org.mrlem.siage3d.sample.advanced.behaviours

import org.joml.Vector3f
import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.scene.graph.nodes.SpatialNode
import org.mrlem.siage3d.core.state.Behaviour
import org.mrlem.siage3d.sample.advanced.GameState
import kotlin.math.cos
import kotlin.math.sin

class PuppetBehaviour(
    private val state: GameState,
    private val angularVelocity: Float,
    private val linearVelocity: Float
) : Behaviour() {

    private val spatialNode get() = node as? SpatialNode

    private var orientation = 0f
    private val position = Vector3f()

    private var currentAngularVelocity = 0f
    private var currentLinearVelocity = 0f

    override fun update(delta: Float) {
        spatialNode?.apply {
            // determine velocities
            currentAngularVelocity = state.leftRight * angularVelocity
            currentLinearVelocity = state.upDown * linearVelocity

            // update orientation
            if (currentAngularVelocity != 0f) {
                orientation += currentAngularVelocity * delta
                setRotation(0f, orientation, 0f)
            }

            // update position
            if (currentLinearVelocity != 0f) {
                position.set(translation)
                    .apply { x += sin(orientation.toRadians()) * currentLinearVelocity * delta }
                    .apply { z -= cos(orientation.toRadians()) * currentLinearVelocity * delta }

                setTranslation(position)
            }
        }
    }

}
