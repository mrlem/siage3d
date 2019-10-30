package org.mrlem.k3d.core.scene

import androidx.annotation.CallSuper
import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.k3d.core.scene.materials.Material
import org.mrlem.k3d.core.scene.shaders.Shader
import org.mrlem.k3d.core.scene.shapes.Shape

open class Node(
    val name: String
) {

    var parent: GroupNode? = null

    protected val localTransform: Matrix4f = Matrix4f()
    protected val globalTransform = Matrix4f()

    @CallSuper
    open fun update() {
        val parent = parent
        if (parent != null) {
            globalTransform.set(parent.globalTransform)
            globalTransform.mul(localTransform)
        } else {
            globalTransform.set(localTransform)
        }
    }

    @CallSuper
    open fun render() {
        Shader.defaultShader.loadTransformationMatrix(globalTransform)
    }

    fun position(position: Vector3f) {
        localTransform.setTranslation(position)
    }

}

open class ObjectNode(
    val shape: Shape,
    val material: Material,
    name: String = "Object #${counter++}"
) : Node(name) {

    override fun render() {
        super.render()

        // draw shape with the material
        material.use {
            shape.draw()
        }
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

    override fun render() {
        super.render()
        children.forEach(Node::render)
    }

    fun add(vararg nodes: Node) {
        nodes.forEach { node ->
            node.parent?.remove(node)

            _children.add(node)
            node.parent = this
        }
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
