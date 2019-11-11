package org.mrlem.k3d.core.common.gl

abstract class Texture(
    protected val id: Int
) {

    fun destroy() {
        TextureFactory.destroyTexture(id)
    }

}
