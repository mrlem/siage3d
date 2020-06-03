package org.mrlem.siage3d.core.scene.graph.nodes

import androidx.annotation.CallSuper
import org.joml.Matrix4f
import org.joml.Vector3f

/**
 * A scene graph node that is spatially positioned.
 *
 * @param name see [Node.name].
 */
abstract class SpatialNode(name: String) : Node(name) {

    internal val localTransform: Matrix4f = Matrix4f()
    internal val globalTransform = Matrix4f()

    val translation: Vector3f get() = localTransform.getTranslation(Vector3f())

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

    fun setTranslation(x: Float, y: Float, z: Float) { localTransform.setTranslation(x, y, z) }

    fun setTranslation(position: Vector3f) { localTransform.setTranslation(position) }

    fun setRotation(x: Float, y: Float, z: Float) {
        localTransform.setRotationXYZ(
            Math.toRadians(x.toDouble()).toFloat(),
            Math.toRadians(y.toDouble()).toFloat(),
            Math.toRadians(z.toDouble()).toFloat()
        )
    }

    fun scale(scale: Float) { localTransform.scale(scale) }

    fun scale(scaleX: Float, scaleY: Float, scaleZ: Float) { localTransform.scale(scaleX, scaleY, scaleZ) }

}
