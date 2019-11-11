package org.mrlem.k3d.core.common.gl

import android.graphics.Bitmap
import android.opengl.GLES30
import android.opengl.GLUtils

object TextureFactory {

    private val arrays = intArrayOf(0)

    private fun createTexture(): Int {
        GLES30.glGenTextures(1, arrays, 0)
        return arrays[0]
    }

    fun load(
        bitmap: Bitmap
    ): Texture2D = Texture2D(createTexture()).apply {
        use {
            GLES30.glTexParameteri(
                GLES30.GL_TEXTURE_2D,
                GLES30.GL_TEXTURE_MIN_FILTER,
                GLES30.GL_LINEAR
            )
            GLES30.glTexParameteri(
                GLES30.GL_TEXTURE_2D,
                GLES30.GL_TEXTURE_MAG_FILTER,
                GLES30.GL_LINEAR
            )
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bitmap, 0)
        }
    }

    internal fun destroyTexture(id: Int) {
        arrays[0] = id
        GLES30.glDeleteTextures(1, arrays, 0)
    }

}
