package org.mrlem.siage3d.core.scene.materials

import android.opengl.GLES30.*
import org.mrlem.siage3d.core.common.gl.Texture2D
import org.mrlem.siage3d.core.scene.shaders.DefaultShader
import org.mrlem.siage3d.core.scene.shaders.Shader

class TextureMaterial(
    private val texture: Texture2D,
    private var shineDamper: Float = 1f,
    private var reflectvity: Float = 0f,
    private val hasTransparency: Boolean = false,
    private val fakeLighting: Boolean = false
) : Material() {

    override val shader: DefaultShader = Shader.defaultShader

    override fun use(block: Material.() -> Unit) {
        if (texture == activeTexture) return
        if (hasTransparency) disableCulling() else enableCulling()

        shader.loadFakeLighting(fakeLighting)
        shader.loadShine(shineDamper, reflectvity)

        texture.use {
            super.use(block)
        }
    }

    private fun enableCulling() {
        glEnable(GL_CULL_FACE)
        glCullFace(GL_BACK)
    }

    private fun disableCulling() {
        glDisable(GL_CULL_FACE)
    }

    companion object {
        private val activeTexture: Texture2D? = null
    }

}
