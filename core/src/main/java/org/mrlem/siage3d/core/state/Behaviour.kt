package org.mrlem.siage3d.core.state

import org.mrlem.siage3d.core.scene.graph.nodes.Node

abstract class Behaviour {

    var node: Node? = null

    abstract fun update(delta: Float)

}
