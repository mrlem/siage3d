package org.mrlem.siage3d.core.scene.graph.nodes

import androidx.annotation.CallSuper
import org.joml.Matrix4f
import org.joml.Vector3f

/**
 * A scene graph node that is spatially positioned.
 *
 * @property name see [Node.name].
 */
abstract class SpatialNode(name: String) : Node(name) {

    val localTransform: Matrix4f = Matrix4f()
    protected val globalTransform = Matrix4f()

    @CallSuper
    open fun applyTransforms() {
        val parent = parent
        if (parent != null) {
            globalTransform
                .set(parent.globalTransform)
                .mul(localTransform)
        } else {
            globalTransform.set(localTransform)
        }
    }

    fun translate(x: Float, y: Float, z: Float) { localTransform.setTranslation(x, y, z) }

    fun translate(position: Vector3f) { localTransform.setTranslation(position) }

    fun scale(scale: Float) { localTransform.scale(scale) }

    fun scale(scaleX: Float, scaleY: Float, scaleZ: Float) { localTransform.scale(scaleX, scaleY, scaleZ) }

    fun rotate(x: Float, y: Float, z: Float) {
        localTransform.setRotationXYZ(
            Math.toRadians(x.toDouble()).toFloat(),
            Math.toRadians(y.toDouble()).toFloat(),
            Math.toRadians(z.toDouble()).toFloat()
        )
    }

    fun position(): Vector3f = localTransform.getTranslation(Vector3f())

    fun position(x: Float, y: Float, z: Float) { localTransform.setTranslation(x, y, z) }

    fun position(position: Vector3f) { localTransform.setTranslation(position) }
}