package org.mrlem.siage3d.core.scene.graph.nodes

import org.mrlem.siage3d.core.state.Behaviour

/**
 * A scene-graph node.
 *
 * @property name name used to identify this node in the scene.
 */
abstract class Node(val name: String) {

    private val _behaviours: MutableList<Behaviour> = mutableListOf()

    /**
     * Parent of this node.
     */
    var parent: GroupNode? = null

    /**
     * Behaviours to be performed by this node.
     */
    val behaviours: List<Behaviour> get() = _behaviours

    /**
     * Add a behaviour to the node.
     *
     * @param behaviour behaviour to add.
     */
    fun add(behaviour: Behaviour) {
        behaviour.node = this
        _behaviours
            .takeUnless { _behaviours.any { it::class.java == behaviour::class.java } }
            ?.add(behaviour)
    }

    /**
     * Remove a behaviour from the node.
     *
     * @param behaviour behaviour to remove.
     */
    fun remove(behaviour: Behaviour) {
        behaviour.node = null
        _behaviours.remove(behaviour)
    }

}
