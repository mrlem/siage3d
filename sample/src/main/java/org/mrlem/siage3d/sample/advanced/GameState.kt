package org.mrlem.siage3d.sample.advanced

import org.mrlem.siage3d.core.behaviour.State

class GameState : State {

    var time = 0f
        private set

    // inputs
    var leftRight = 0f
    var upDown = 0f

    override fun update(delta: Float) {
        time += delta
    }

}

val state = GameState()
