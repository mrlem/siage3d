package org.mrlem.siage3d.core.common.gl

abstract class Texture(
    val id: Int
) {

    fun destroy() {
        TextureFactory.destroyTexture(id)
    }

}
