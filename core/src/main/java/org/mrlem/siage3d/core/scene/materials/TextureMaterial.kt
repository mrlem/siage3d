package org.mrlem.siage3d.core.scene.materials

import org.mrlem.siage3d.core.common.gl.Texture2D
import org.mrlem.siage3d.core.scene.shaders.DefaultShader
import org.mrlem.siage3d.core.scene.shaders.Shader

class TextureMaterial(
    name: String,
    private val texture: Texture2D,
    private val scale: Float = 1f,
    private val ambient: Float = 1f,
    private var shineDamper: Float = 1f,
    private var reflectvity: Float = 0f,
    private val hasTransparency: Boolean = false,
    private val fakeLighting: Boolean = false
) : Material(name) {

    override val shader: DefaultShader = Shader.defaultShader

    override fun setup() {
        if (hasTransparency) disableCulling() else enableCulling()

        shader.loadAmbient(ambient)
        shader.loadFakeLighting(fakeLighting)
        shader.loadShine(shineDamper, reflectvity)
        shader.loadScale(scale)

        texture.use()
    }

}
