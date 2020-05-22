package org.mrlem.siage3d.core.scene.graph.nodes

/**
 * A scene-graph spatial node that just contains other nodes.
 *
 * It renders nothing, but its transform will be applied to all children nodes.
 *
 * @param name see [Node.name].
 */
open class GroupNode(
    name: String? = null
) : SpatialNode(name ?: "Group #${counter++}") {

    private val _children: MutableList<Node> = mutableListOf()
    val children: List<Node> get() = _children

    override fun applyTransforms() {
        super.applyTransforms()
        children
            .filterIsInstance<SpatialNode>()
            .forEach(SpatialNode::applyTransforms)
    }

    fun add(vararg nodes: Node) {
        nodes.forEach { node ->
            node.parent?.remove(node)

            _children.add(node)
            node.parent = this
        }
    }

    fun clear() {
        _children.toList().forEach { remove(it) }
    }

    fun remove(child: Node) {
        if (_children.remove(child)) {
            child.parent = null
        }
    }

    fun browse(depthFirst: Boolean = false, callback: (Node) -> Unit) {
        children.forEach { child ->
            if (depthFirst) callback(child)
            if (child is GroupNode) {
                child.browse(depthFirst, callback)
            }
            if (!depthFirst) callback(child)
        }
    }

    @Suppress("UNCHECKED_CAST")
    open operator fun <T> get(name: String): T? {
        if (this@GroupNode.name == name) return this as T
        for (child in children) {
            child.takeIf { it.name == name }
                ?.let { return it as T }
            (child as? GroupNode)?.get<T>(name)
                ?.let { return it }
        }
        return null
    }

    companion object {
        private var counter = 0
    }

}