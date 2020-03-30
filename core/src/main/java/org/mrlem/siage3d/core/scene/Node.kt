package org.mrlem.siage3d.core.scene

import androidx.annotation.CallSuper
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.mrlem.siage3d.core.scene.materials.Material
import org.mrlem.siage3d.core.scene.shaders.Shader
import org.mrlem.siage3d.core.scene.shapes.Shape
import org.mrlem.siage3d.core.scene.shapes.Terrain

abstract class Node(val name: String) {

    var parent: GroupNode? = null

}

abstract class SpatialNode(name: String) : Node(name) {

    val localTransform: Matrix4f = Matrix4f()
    protected val globalTransform = Matrix4f()

    @CallSuper
    open fun update() {
        val parent = parent
        if (parent != null) {
            globalTransform
                .set(parent.globalTransform)
                .mul(localTransform)
        } else {
            globalTransform.set(localTransform)
        }
    }

}

open class GroupNode(
    name: String? = null
) : SpatialNode(name ?: "Group #${counter++}") {

    private val _children: MutableList<Node> = mutableListOf()
    val children: List<Node> get() = _children

    override fun update() {
        super.update()
        children
            .filterIsInstance<SpatialNode>()
            .forEach(SpatialNode::update)
    }

    fun add(vararg nodes: Node) {
        nodes.forEach { node ->
            node.parent?.remove(node)

            _children.add(node)
            node.parent = this
        }
    }

    fun clear() {
        _children.toList().forEach { remove(it) }
    }

    fun remove(child: Node) {
        if (_children.remove(child)) {
            child.parent = null
        }
    }

    @Suppress("UNCHECKED_CAST")
    open operator fun <T> get(name: String): T? {
        if (this@GroupNode.name == name) return this as T
        for (child in children) {
            child.takeIf { it.name == name }
                ?.let { return it as T }
            (child as? GroupNode)?.get<T>(name)
                ?.let { return it }
        }
        return null
    }

    companion object {
        private var counter = 0
    }

}

open class ObjectNode(
    val shape: Shape,
    val material: Material,
    name: String? = null
) : SpatialNode(name ?: "Object #${counter++}") {

    fun render() {
        (material.shader as? Shader.TransformationAware)
            ?.loadTransformationMatrix(globalTransform)

        // draw the shape: material is handled at scene-level
        shape.draw()
    }

    companion object {
        private var counter = 0
    }

}

class TerrainNode(
    private val terrain: Terrain,
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

///////////////////////////////////////////////////////////////////////////
// Extensions
///////////////////////////////////////////////////////////////////////////

val Node.terrain
    get() = (this as? ObjectNode)?.shape as? Terrain

fun <T : SpatialNode> T.translate(x: Float, y: Float, z: Float) = this
    .also { localTransform.setTranslation(x, y, z) }

fun <T : SpatialNode> T.translate(position: Vector3f) = this
    .also { localTransform.setTranslation(position) }

fun <T : SpatialNode> T.scale(scale: Float) = this
    .also { localTransform.scale(scale) }

fun <T : SpatialNode> T.scale(scaleX: Float, scaleY: Float, scaleZ: Float) = this
    .also { localTransform.scale(scaleX, scaleY, scaleZ) }

fun <T : SpatialNode> T.rotate(x: Float, y: Float, z: Float) = this
    .also {
        localTransform.setRotationXYZ(
            Math.toRadians(x.toDouble()).toFloat(),
            Math.toRadians(y.toDouble()).toFloat(),
            Math.toRadians(z.toDouble()).toFloat()
        )
    }

fun <T : SpatialNode> T.position(): Vector3f = localTransform.getTranslation(Vector3f())

fun <T : SpatialNode> T.position(x: Float, y: Float, z: Float) = this
    .apply { localTransform.setTranslation(x, y, z) }

fun <T : SpatialNode> T.position(position: Vector3f) = this
    .apply { localTransform.setTranslation(position) }
