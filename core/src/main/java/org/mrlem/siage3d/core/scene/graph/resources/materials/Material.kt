package org.mrlem.siage3d.core.scene.graph.resources.materials

import android.opengl.GLES30.*
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader

abstract class Material(val name: String) : Comparable<Material> {

    abstract val shader: Shader

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