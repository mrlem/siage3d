package org.mrlem.siage3d.core.scene

import androidx.annotation.CallSuper
import org.joml.Matrix4f
import org.mrlem.siage3d.core.scene.materials.Material
import org.mrlem.siage3d.core.scene.shaders.Shader
import org.mrlem.siage3d.core.scene.shapes.Shape

abstract class Node(
    val name: String
) {

    var parent: GroupNode? = null

    val localTransform: Matrix4f = Matrix4f()
    protected val globalTransform = Matrix4f()

    @CallSuper
    internal open fun update() {
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

open class ObjectNode(
    val shape: Shape,
    val material: Material,
    name: String = "Object #${counter++}"
) : Node(name) {

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

open class GroupNode(
    name: String = "Group #${counter++}"
) : Node(name) {

    private val _children: MutableList<Node> = mutableListOf()
    val children: List<Node> get() = _children

    override fun update() {
        super.update()
        children.forEach(Node::update)
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

    companion object {
        private var counter = 0
    }

}
