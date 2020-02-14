package org.mrlem.siage3d.core.render

import org.mrlem.siage3d.core.scene.GroupNode
import org.mrlem.siage3d.core.scene.Node
import org.mrlem.siage3d.core.scene.ObjectNode
import org.mrlem.siage3d.core.scene.Scene

abstract class SceneRenderer(protected val scene: Scene) {

    abstract fun render()

    protected fun gatherObjects(rootNode: Node, objectNodes: MutableList<ObjectNode>): MutableList<ObjectNode> {
        when (rootNode) {
            is GroupNode -> rootNode.children.forEach { child -> gatherObjects(child, objectNodes) }
            is ObjectNode -> objectNodes.add(rootNode)
        }
        return objectNodes
    }

}