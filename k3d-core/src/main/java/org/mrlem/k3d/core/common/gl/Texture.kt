package org.mrlem.k3d.core.common.gl

import android.graphics.Bitmap
import android.opengl.GLES30.*
import android.opengl.GLUtils

class Texture {

    private val id: Int

    init {
        glGenTextures(1, arrays, 0)
        id = arrays[0]
    }

    fun use(block: Texture.() -> Unit) {
        // TODO - optional - LRU cache of textures to avoid rebinding every time
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, id)
            this.block()
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    fun destroy() {
        arrays[0] = id
        glDeleteTextures(1, arrays, 0)
    }

    companion object {
        private val arrays = intArrayOf(0)

        fun load(
            bitmap: Bitmap
        ): Texture = Texture().apply {
            use {
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
            }
        }
    }

}
