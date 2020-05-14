package org.mrlem.siage3d.core.scene.graph.resources.materials

import android.opengl.GLES30.*
import org.mrlem.siage3d.core.common.gl.texture.Texture2D
import org.mrlem.siage3d.core.common.gl.shaders.MultiTextureShader
import org.mrlem.siage3d.core.common.gl.shaders.Shader
import org.mrlem.siage3d.core.common.io.caches.Ref

class MultiTextureMaterial(
    name: String,
    private val blendMapRef: Ref<Texture2D>,
    private val backgroundTextureRef: Ref<Texture2D>,
    private val redTextureRef: Ref<Texture2D>,
    private val greenTextureRef: Ref<Texture2D>,
    private val blueTextureRef: Ref<Texture2D>,
    private val scale: Float = 1f,
    private val ambient: Float = 1f,
    private var shineDamper: Float = 1f,
    private var reflectvity: Float = 0f,
    private val hasTransparency: Boolean = false,
    private val fakeLighting: Boolean = false
) : Material(name) {

    override val shader: MultiTextureShader = Shader.multiTextureShader

    override fun setup() {
        if (hasTransparency) disableCulling() else enableCulling()

        shader.loadAmbient(ambient)
        shader.loadFakeLighting(fakeLighting)
        shader.loadShine(shineDamper, reflectvity)
        shader.loadScale(scale)
        shader.loadSamplers()

        blendMapRef.get().use(GL_TEXTURE0)
        backgroundTextureRef.get().use(GL_TEXTURE1)
        redTextureRef.get().use(GL_TEXTURE2)
        greenTextureRef.get().use(GL_TEXTURE3)
        blueTextureRef.get().use(GL_TEXTURE4)
    }

}
