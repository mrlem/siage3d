package org.mrlem.siage3d.core.render

import org.mrlem.siage3d.core.scene.GroupNode
import org.mrlem.siage3d.core.scene.Node
import org.mrlem.siage3d.core.scene.ObjectNode
import org.mrlem.siage3d.core.scene.Scene
import org.mrlem.siage3d.core.scene.materials.Material
import org.mrlem.siage3d.core.scene.shaders.Shader

class SortedMaterialsSceneRenderer(scene: Scene) : SceneRenderer(scene) {

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
        addObjects(map, scene)
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
