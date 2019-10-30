package org.mrlem.k3d.core.scene.materials

import android.opengl.GLES30.*
import org.mrlem.k3d.core.scene.shaders.Shader

open class Material(
    private var shineDamper: Float = 1f,
    private var reflectvity: Float = 0f,
    private val hasTransparency: Boolean = false,
    private val fakeLighting: Boolean = false
) {

    open fun use(block: Material.() -> Unit) {
        if (this == active) return

        if (hasTransparency) disableCulling() else enableCulling()

        Shader.defaultShader.loadFakeLighting(fakeLighting)
        Shader.defaultShader.loadShine(shineDamper, reflectvity)

        this.block()
    }

    private fun enableCulling() {
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)
    }

    private fun disableCulling() {
        glDisable(GL_CULL_FACE)
    }

    companion object {
        var active: Material? = null
    }

}