package org.mrlem.siage3d.core.common.io.caches

import org.mrlem.siage3d.core.common.gl.shaders.CubemapTextureShader
import org.mrlem.siage3d.core.common.gl.shaders.MultiTextureShader
import org.mrlem.siage3d.core.common.gl.shaders.Shader
import org.mrlem.siage3d.core.common.gl.shaders.TextureShader

object ShaderCache {

    private val currentShaders = mutableListOf<Shader>()
    private var currentDefaultShader: TextureShader? = null
    private var currentMultiTextureShader: MultiTextureShader? = null
    private var currentSkyboxShader: CubemapTextureShader? = null

    val shaders: List<Shader> = currentShaders
    val textureShader get() = currentDefaultShader ?: TextureShader().also { currentShaders.add(it) }
    val multiTextureShader get() = currentMultiTextureShader ?: MultiTextureShader().also { currentShaders.add(it) }
    val skyboxShader get() = currentSkyboxShader ?: CubemapTextureShader().also { currentShaders.add(it) }

    fun clear() {
        currentDefaultShader?.destroy()
        currentDefaultShader = null
        currentMultiTextureShader?.destroy()
        currentMultiTextureShader = null
        currentSkyboxShader?.destroy()
        currentSkyboxShader = null

        currentShaders.clear()
    }

}
