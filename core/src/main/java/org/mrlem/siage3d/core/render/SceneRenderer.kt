package org.mrlem.siage3d.core.render

import org.mrlem.siage3d.core.scene.graph.nodes.GroupNode
import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.scene.graph.Scene

abstract class SceneRenderer(
    protected val scene: Scene
) {

    abstract fun render()

    protected fun gatherObjects(rootNode: GroupNode): MutableList<ObjectNode> {
        val objectNodes = mutableListOf<ObjectNode>()
        rootNode.browse {
            if (it is ObjectNode) objectNodes.add(it)
        }
        return objectNodes
    }

}