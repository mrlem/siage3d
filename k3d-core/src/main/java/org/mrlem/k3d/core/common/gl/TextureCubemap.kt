package org.mrlem.k3d.core.common.gl

import android.opengl.GLES30.*

class TextureCubemap(
    private val id: Int
) {

    fun use(block: TextureCubemap.() -> Unit) {
        // TODO - optional - LRU cache of textures to avoid rebinding every time
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_CUBE_MAP, id)
        this.block()
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0)
    }

}
