package org.mrlem.siage3d.core.scene.graph.nodes

import androidx.annotation.CallSuper
import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.mrlem.siage3d.core.common.math.toRadians

/**
 * A scene graph node that is spatially positioned.
 *
 * @param name see [Node.name].
 */
abstract class SpatialNode(name: String) : Node(name) {

    internal val globalTransform = Matrix4f()
    internal val localTransform = Matrix4f()
    private var localTransformDirty = false

    val translation = Vector3f()
    val scaling = Vector3f(1f)
    val rotation = Quaternionf()

    @CallSuper
    open fun applyTransforms() {
        // local transform
        if (localTransformDirty) {
            localTransform
                .translation(translation)
                .scale(scaling.x, scaling.y, scaling.z)
                .rotate(rotation)

            localTransformDirty = false
        }

        // global transform
        val parent = parent
        if (parent != null) {
            globalTransform
                .set(parent.globalTransform)
                .mul(localTransform)
        } else {
            globalTransform.set(localTransform)
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Translation modifiers
    ///////////////////////////////////////////////////////////////////////////

    fun setTranslation(x: Float, y: Float, z: Float) {
        translation.set(x, y, z)
        localTransformDirty = true
    }

    fun setTranslation(translation: Vector3f) {
        this.translation.set(translation)
        localTransformDirty = true
    }

    ///////////////////////////////////////////////////////////////////////////
    // Rotation modifiers
    ///////////////////////////////////////////////////////////////////////////

    fun setRotation(x: Float, y: Float, z: Float) {
        rotation.rotationXYZ(x.toRadians(), y.toRadians(), z.toRadians())
        localTransformDirty = true
    }

    fun setRotation(rotation: Vector3f) {
        this.rotation.rotationXYZ(
            rotation.x.toRadians(),
            rotation.y.toRadians(),
            rotation.z.toRadians()
        )
        localTransformDirty = true
    }

    ///////////////////////////////////////////////////////////////////////////
    // Scaling modifiers
    ///////////////////////////////////////////////////////////////////////////

    fun scale(scale: Float) {
        scaling.set(scale)
        localTransformDirty = true
    }

    fun setScaling(scaling: Float) {
        this.scaling.set(scaling)
        localTransformDirty = true
    }

    fun setScaling(scalingX: Float, scalingY: Float, scalingZ: Float) {
        this.scaling.set(scalingX, scalingY, scalingZ)
        localTransformDirty = true
    }

    fun setScaling(scaling: Vector3f) {
        this.scaling.set(scaling)
        localTransformDirty = true
    }

}
