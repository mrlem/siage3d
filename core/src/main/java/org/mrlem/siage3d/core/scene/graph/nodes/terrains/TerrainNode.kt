package org.mrlem.siage3d.core.scene.graph.nodes.terrains

import org.joml.Matrix4f
import org.joml.Vector4f
import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.scene.graph.resources.materials.Material
import org.mrlem.siage3d.core.scene.graph.resources.shapes.TerrainShape

class TerrainNode(
    private val terrain: TerrainShape,
    material: Material,
    name: String?
) : ObjectNode(terrain, material, name) {

    private val tmp = Vector4f()
    private val invLocalTransform = Matrix4f()

    fun heightAt(x: Float, z: Float): Float {
        // transform input xz coordinates
        tmp.set(x, 0f, z, 0f)
        localTransform.invert(invLocalTransform)
        invLocalTransform.transform(tmp)

        // transform output y coordinate
        tmp.set(0f, terrain.heightAt(tmp.x, tmp.z), 0f, 0f)
        localTransform.transform(tmp)
        return tmp.y
    }

}