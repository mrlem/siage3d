package org.mrlem.siage3d.core.scene.materials

import android.opengl.GLES30.*
import org.mrlem.siage3d.core.common.gl.Texture2D
import org.mrlem.siage3d.core.scene.shaders.MultiTextureShader
import org.mrlem.siage3d.core.scene.shaders.Shader

class MultiTextureMaterial(
    private val blendMap: Texture2D,
    private val backgroundTexture: Texture2D,
    private val redTexture: Texture2D,
    private val greenTexture: Texture2D,
    private val blueTexture: Texture2D,
    private val tileSize: Float = 40f,
    private var shineDamper: Float = 1f,
    private var reflectvity: Float = 0f,
    private val hasTransparency: Boolean = false,
    private val fakeLighting: Boolean = false
) : Material() {

    override val shader: MultiTextureShader = Shader.multiTextureShader

    override fun setup() {
        if (hasTransparency) disableCulling() else enableCulling()

        shader.loadFakeLighting(fakeLighting)
        shader.loadShine(shineDamper, reflectvity)
        shader.loadTileSize(tileSize)
        shader.loadSamplers()

        blendMap.use(GL_TEXTURE0)
        backgroundTexture.use(GL_TEXTURE1)
        redTexture.use(GL_TEXTURE2)
        greenTexture.use(GL_TEXTURE3)
        blueTexture.use(GL_TEXTURE4)
    }

}
