package org.mrlem.siage3d.core.scene.graph.resources.materials

import org.mrlem.siage3d.core.common.gl.texture.CubemapTexture
import org.mrlem.siage3d.core.scene.graph.resources.shaders.CubemapTextureShader
import org.mrlem.siage3d.core.common.io.caches.AbstractCache.Ref
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader

/**
 * Texture cubemap based material consisting in a set of 6 textures used to render an unshaded cube. Especially useful
 * for skybox rendering.
 *
 * @property name see [Material.name].
 * @property textureRef reference to the cubemap texture to be drawn.
 */
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
