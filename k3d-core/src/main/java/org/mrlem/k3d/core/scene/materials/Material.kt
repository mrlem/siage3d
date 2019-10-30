package org.mrlem.k3d.core.scene.materials

import android.opengl.GLES30
import org.mrlem.k3d.core.scene.shaders.Shader

open class Material(
    var shineDamper: Float = 1f,
    var reflectvity: Float = 0f,
    val hasTransparency: Boolean = false,
    val fakeLighting: Boolean = false
) {

    open fun use(block: Material.() -> Unit) {
        if (this == active) return

        if (hasTransparency) disableCulling() else enableCulling()

        Shader.defaultShader.apply {
            loadFakeLighting(fakeLighting)
            loadShine(shineDamper, reflectvity)
        }
        this.block()
    }

    private fun enableCulling() {
        GLES30.glEnable(GLES30.GL_CULL_FACE)
        GLES30.glCullFace(GLES30.GL_BACK)
    }

    private fun disableCulling() {
        GLES30.glDisable(GLES30.GL_CULL_FACE)
    }

    companion object {
        var active: Material? = null
    }

}