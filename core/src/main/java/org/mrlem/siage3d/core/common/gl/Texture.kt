package org.mrlem.siage3d.core.common.gl

abstract class Texture(
    protected val id: Int
) {

    fun destroy() {
        TextureFactory.destroyTexture(id)
    }

}
