package org.mrlem.siage3d.sample.advanced

import org.joml.Vector3f
import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.state.State
import kotlin.math.cos
import kotlin.math.sin

class GameState : State {

    var time = 0f
        private set

    // inputs
    var linearVelocity = 0f
    var angularVelocity = 0f

    // outputs
    // .. camera
    val position = Vector3f(0f, 40f, 5f)
    var orientation = 0f

    override fun update(delta: Float) {
        time += delta

        // update camera
        orientation += angularVelocity * delta
        position
            .apply { x += sin(orientation.toRadians()) * linearVelocity * delta }
            .apply { z -= cos(orientation.toRadians()) * linearVelocity * delta }
            .apply { y = 40f }
    }

}

val state = GameState()
