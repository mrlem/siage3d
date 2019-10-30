package org.mrlem.k3d.core.view

import org.mrlem.k3d.core.scene.GroupNode
import org.mrlem.k3d.core.scene.Node
import org.mrlem.k3d.core.scene.ObjectNode
import org.mrlem.k3d.core.scene.Scene
import org.mrlem.k3d.core.scene.materials.Material
import org.mrlem.k3d.core.scene.shaders.Shader

abstract class SceneAdapter {

    abstract val scene: Scene

    internal fun init() {
        onInit()
    }

    internal fun resize(width: Int, height: Int) {
        scene.camera.update(width, height)
        onResize(width, height)
    }

    internal fun update(delta: Float) {
        onUpdate(delta)
        scene.update()

        // TODO - optional - have a scene renderer interface, with a standard impl & a sorted impl?

        // sort object nodes by material so as to optimize texture changes & such
        val sortedObjects = sortObjects()

        // render
        Shader.defaultShader.use {
            sortedObjects.keys.forEach { material ->
                sortedObjects[material]?.forEach(ObjectNode::render)
            }
        }
    }

    internal fun destroy() {
        onDestroy()
    }

    private fun sortObjects(): Map<Material, List<ObjectNode>> {
        val map = mutableMapOf<Material, MutableList<ObjectNode>>()
        addObjects(map, scene)
        return map
    }

    private fun addObjects(map: MutableMap<Material, MutableList<ObjectNode>>, node: Node) {
        // TODO - critical - test this
        when (node) {
            is GroupNode -> node.children.forEach { child -> addObjects(map, child) }
            is ObjectNode -> map[node.material]?.add(node) ?: mutableListOf(node).also {
                map[node.material] = it
            }
        }
    }

    open fun onInit() {}
    open fun onResize(width: Int, height: Int) {}
    open fun onUpdate(delta: Float) {}
    open fun onDestroy() {}

}
