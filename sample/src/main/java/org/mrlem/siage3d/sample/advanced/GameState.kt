package org.mrlem.siage3d.sample.advanced

import org.joml.Vector3f
import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.state.State
import kotlin.math.cos
import kotlin.math.sin

class GameState : State {

    private var time = 0f

    // inputs
    var linearVelocity = 0f
    var angularVelocity = 0f

    // outputs
    // .. camera
    val position = Vector3f(0f, 40f, 5f)
    var orientation = 0f
    // .. spots
    val spot1Position = Vector3f(0f, 25f, 0f)
    val spot2Position = Vector3f(0f, 25f, 0f)

    override fun update(delta: Float) {
        time += delta

        // update lights
        spot1Position.set(sin(time) * 10f, spot1Position.y, cos(time) * 10f)
        spot2Position.set(5 + sin(time * 1.7f) * 14f, spot2Position.y, cos(time * 1.7f) * 14f)

        // update camera
        orientation += angularVelocity * delta
        position
            .apply { x += sin(orientation.toRadians()) * linearVelocity * delta }
            .apply { z -= cos(orientation.toRadians()) * linearVelocity * delta }
            .apply { y = 40f }
    }

}

val state = GameState()
