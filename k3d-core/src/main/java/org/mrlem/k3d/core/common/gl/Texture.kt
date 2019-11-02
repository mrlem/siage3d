package org.mrlem.k3d.core.common.gl

import android.graphics.Bitmap
import android.opengl.GLES30.*
import android.opengl.GLUtils
import java.nio.IntBuffer

class Texture {

    private val id: Int = create()

    private fun create(): Int {
        glGenTextures(1, arrays)
        return arrays[0]
    }

    fun use(block: Texture.() -> Unit) {
        glBindTexture(GL_TEXTURE_2D, id)
            this.block()
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    fun destroy() {
        arrays.put(0, id)
        glDeleteTextures(1, arrays)
    }

    companion object {
        private val arrays = IntBuffer.allocate(1)

        fun load(
            bitmap: Bitmap
        ): Texture = Texture().apply {
            use {
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
                glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
                GLUtils.texImage2D(id, 0, bitmap, 0)
            }
        }
    }

}
