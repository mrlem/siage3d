package org.mrlem.siage3d.core.scene.graph.nodes

import org.mrlem.siage3d.core.state.Behaviour

/**
 * A scene-graph node.
 *
 * @property name name used to identify this node in the scene.
 */
abstract class Node(val name: String) {

    private val _behaviours: MutableMap<Class<out Behaviour>, Behaviour> = mutableMapOf()

    /**
     * Parent of this node.
     */
    var parent: GroupNode? = null

    /**
     * Behaviours to be performed by this node.
     */
    val behaviours: Collection<Behaviour> = _behaviours.values

    /**
     * Add a behaviour to the node.
     *
     * @param behaviour behaviour to add.
     */
    fun add(behaviour: Behaviour) {
        _behaviours[behaviour::class.java] = behaviour
        behaviour.node = this
    }

    /**
     * Remove a behaviour from the node.
     *
     * @param behaviour behaviour to remove.
     */
    fun remove(behaviour: Behaviour) {
        _behaviours.remove(behaviour::class.java)
        behaviour.node = null
    }

}
