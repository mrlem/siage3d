package org.mrlem.siage3d.core.scene.materials

import android.opengl.GLES30.*
import org.mrlem.siage3d.core.scene.shaders.Shader

abstract class Material : Comparable<Material> {

    abstract val shader: Shader

    abstract fun use()

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