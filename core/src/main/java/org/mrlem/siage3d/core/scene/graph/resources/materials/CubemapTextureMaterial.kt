package org.mrlem.siage3d.core.scene.graph.resources.materials

import org.mrlem.siage3d.core.common.gl.texture.CubemapTexture
import org.mrlem.siage3d.core.common.gl.shaders.Shader
import org.mrlem.siage3d.core.common.gl.shaders.CubemapTextureShader
import org.mrlem.siage3d.core.common.io.caches.AbstractCache.Ref

class CubemapTextureMaterial(
    name: String,
    private var textureRef: Ref<CubemapTexture>
) : Material(name) {

    override val shader: CubemapTextureShader = Shader.skyboxShader

    override fun setup() {
        disableCulling()

        textureRef.get().use()
    }

}
