package org.mrlem.siage3d.core.render

import org.mrlem.siage3d.core.scene.GroupNode
import org.mrlem.siage3d.core.scene.Node
import org.mrlem.siage3d.core.scene.ObjectNode
import org.mrlem.siage3d.core.scene.Scene
import org.mrlem.siage3d.core.scene.shaders.Shader

class SortedSceneRenderer(scene: Scene) : SceneRenderer(scene) {

    override fun render() {
        // apply camera
        scene.camera.use()

        // render sky
        scene.sky.render()

        // draw scene
        Shader.notifyLight(scene.light, scene.sky.color)
        val objects = gatherObjects(scene, mutableListOf())
        objects.sortWith(compareBy<ObjectNode> { it.material.shader }.thenBy { it.material })

        objects.forEach {
            it.material.use()
            it.render()
        }
    }

    private fun gatherObjects(rootNode: Node, objectNodes: MutableList<ObjectNode>): MutableList<ObjectNode> {
        when (rootNode) {
            is GroupNode -> rootNode.children.forEach { child -> gatherObjects(child, objectNodes) }
            is ObjectNode -> objectNodes.add(rootNode)
        }
        return objectNodes
    }

}