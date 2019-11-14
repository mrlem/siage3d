package org.mrlem.siage3d.core.render

import org.mrlem.siage3d.core.scene.GroupNode
import org.mrlem.siage3d.core.scene.ObjectNode
import org.mrlem.siage3d.core.scene.Scene
import org.mrlem.siage3d.core.scene.shaders.Shader

class BasicSceneRenderer(scene: Scene) : SceneRenderer(scene) {

    override fun render() {
        // apply camera
        scene.camera.use()

        // render sky
        scene.sky.render()

        Shader.defaultShader.use {
            // apply light
            Shader.defaultShader.loadLight(scene.light)

            // apply sky
            Shader.defaultShader.loadSkyColor(scene.sky.color)

            // draw scene
            val objectNodes = scene.collectObjectNodes(mutableListOf())
            objectNodes.forEach { node ->
                node.material.use {
                    node.render()
                }
            }
        }
    }

    private fun GroupNode.collectObjectNodes(objectNodes: MutableList<ObjectNode>): List<ObjectNode> {
        children.forEach {
            when (it) {
                is GroupNode -> it.collectObjectNodes(objectNodes)
                is ObjectNode -> objectNodes.add(it)
            }
        }
        return objectNodes
    }

}