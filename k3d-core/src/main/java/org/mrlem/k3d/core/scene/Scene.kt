package org.mrlem.k3d.core.scene

import android.opengl.GLES30.*
import org.joml.Vector3f
import org.mrlem.k3d.core.scene.lights.PointLight
import org.mrlem.k3d.core.scene.materials.Material
import org.mrlem.k3d.core.scene.shaders.Shader

class Scene : GroupNode("Scene") {

    val skyColor: Vector3f = Vector3f(0f, 0f, 0f)
    val light: PointLight = PointLight(Vector3f(10f, 10f, 10f), Vector3f(1f, 1f, .8f))
    val camera: Camera = Camera()

    internal fun render() {
        // draw sky
        glClearColor(skyColor.x, skyColor.y, skyColor.z, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        Shader.defaultShader.use {
            // apply light
            Shader.defaultShader.loadLight(light)

            // apply sky
            Shader.defaultShader.loadSkyColor(skyColor)

            // apply camera
            camera.use()

            // draw scene
            // .. sort object nodes by material so as to optimize texture changes & such
            val sortedObjects = sortObjects()
            sortedObjects.forEach { (material, nodes) ->
                material.use {
                    nodes.forEach(ObjectNode::render)
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
