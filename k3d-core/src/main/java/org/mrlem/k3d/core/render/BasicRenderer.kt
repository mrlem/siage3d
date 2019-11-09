package org.mrlem.k3d.core.render

import android.opengl.GLES30.*
import org.mrlem.k3d.core.scene.GroupNode
import org.mrlem.k3d.core.scene.ObjectNode
import org.mrlem.k3d.core.scene.Scene
import org.mrlem.k3d.core.scene.shaders.Shader

class BasicSceneRenderer(scene: Scene) : SceneRenderer(scene) {

    override fun render() {
        // draw sky
        glClearColor(scene.skyColor.x, scene.skyColor.y, scene.skyColor.z, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        Shader.defaultShader.use {
            // apply light
            Shader.defaultShader.loadLight(scene.light)

            // apply sky
            Shader.defaultShader.loadSkyColor(scene.skyColor)

            // apply camera
            scene.camera.use()

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
