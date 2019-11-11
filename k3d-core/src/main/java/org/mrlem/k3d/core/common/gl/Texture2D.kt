package org.mrlem.k3d.core.common.gl

import android.opengl.GLES30.*

class Texture2D(
    id: Int
) : Texture(id) {

    fun use(block: Texture2D.() -> Unit) {
        // TODO - optional - LRU cache of textures to avoid rebinding every time
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, id)
        this.block()
        glBindTexture(GL_TEXTURE_2D, 0)
    }

}
