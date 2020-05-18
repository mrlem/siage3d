package org.mrlem.siage3d.sample.simple

import org.mrlem.siage3d.core.world.World

class World : World {

    var time = 0f

    override fun update(delta: Float) {
        time += delta
    }

}

val world = World()
