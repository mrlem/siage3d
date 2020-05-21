package org.mrlem.siage3d.core.scene.graph.nodes

/**
 * A scene-graph node.
 *
 * @property name name used to identify this node in the scene.
 */
abstract class Node(val name: String) {

    /**
     * Parent of this node.
     */
    var parent: GroupNode? = null

}
