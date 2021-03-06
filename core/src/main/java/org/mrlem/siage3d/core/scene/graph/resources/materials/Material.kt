package org.mrlem.siage3d.core.scene.graph.resources.materials

import android.opengl.GLES30.*
import org.mrlem.siage3d.core.common.gl.Program
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader

/**
 * Material to apply to a surface when drawing it.
 *
 * @property name the name used to identify this material.
 */
abstract class Material(val name: String) : Comparable<Material> {

    abstract val shader: Shader

    /**
     * Use this material for all subsequent draw calls (until a new material is being used).
     */
    open fun use() {
        if (activeMaterial == this) return
        activeMaterial = this

        shader.use()
        setup()
    }

    protected abstract fun setup()

    override fun compareTo(other: Material) = hashCode().compareTo(other.hashCode())

    protected fun enableCulling() {
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)
    }

    protected fun disableCulling() {
        glDisable(GL_CULL_FACE)
    }

    companion object {
        internal var activeMaterial: Material? = null
    }

}