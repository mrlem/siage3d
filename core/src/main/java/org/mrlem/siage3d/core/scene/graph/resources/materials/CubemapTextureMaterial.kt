package org.mrlem.siage3d.core.scene.graph.resources.materials

import org.mrlem.siage3d.core.common.gl.texture.CubemapTexture
import org.mrlem.siage3d.core.scene.graph.resources.shaders.CubemapTextureShader
import org.mrlem.siage3d.core.common.io.caches.AbstractCache.Ref
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader

class CubemapTextureMaterial(
    name: String,
    private var textureRef: Ref<CubemapTexture>
) : Material(name) {

    override val shader: CubemapTextureShader = Shader.cubemapTextureShader

    override fun setup() {
        disableCulling()

        textureRef.value.use()
    }

}
