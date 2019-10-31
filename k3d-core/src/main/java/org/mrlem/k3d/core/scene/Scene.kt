package org.mrlem.k3d.core.scene

import android.opengl.GLES30
import org.mrlem.k3d.core.common.gl.Color
import org.mrlem.k3d.core.scene.materials.Material
import org.mrlem.k3d.core.scene.shaders.Shader
import javax.microedition.khronos.opengles.GL10

class Scene : GroupNode("Scene") {

    val skyColor: Color = Color(0f, 0f, 0f)
    val camera: Camera = Camera()

    fun render() {
        // draw sky
        skyColor.components.let { components ->
            GLES30.glClearColor(components[0], components[1], components[2], 1f)
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        }

        // apply camera
        camera.use()

        // draw scene
        // .. sort object nodes by material so as to optimize texture changes & such
        val sortedObjects = sortObjects()
        Shader.defaultShader.use {
            sortedObjects.keys.forEach { material ->
                material.use {
                    sortedObjects[material]?.forEach(ObjectNode::render)
                }
            }
        }
    }

    private fun sortObjects(): Map<Material, List<ObjectNode>> {
        val map = mutableMapOf<Material, MutableList<ObjectNode>>()
        addObjects(map, this)
        return map
    }

    private fun addObjects(map: MutableMap<Material, MutableList<ObjectNode>>, node: Node) {
        when (node) {
            is GroupNode -> node.children.forEach { child -> addObjects(map, child) }
            is ObjectNode -> map[node.material]?.add(node) ?: mutableListOf(node).also {
                map[node.material] = it
            }
        }
    }

}
