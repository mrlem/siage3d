package org.mrlem.siage3d.core.scene.graph.nodes

open class GroupNode(
    name: String? = null
) : SpatialNode(name ?: "Group #${counter++}") {

    private val _children: MutableList<Node> = mutableListOf()
    val children: List<Node> get() = _children

    override fun update() {
        super.update()
        children
            .filterIsInstance<SpatialNode>()
            .forEach(SpatialNode::update)
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