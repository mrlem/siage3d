package org.mrlem.k3d.core.common.gl

import android.graphics.Bitmap
import android.opengl.GLES30.*
import android.opengl.GLUtils.*

object TextureFactory {

    private val arrays = intArrayOf(0)

    fun load(
        bitmap: Bitmap
    ): Texture2D = Texture2D(createTexture()).apply {
        use {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
            texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
        }
    }

    fun load(
        leftBitmap: Bitmap,
        rightBitmap: Bitmap,
        bottomBitmap: Bitmap,
        topBitmap: Bitmap,
        backBitmap: Bitmap,
        frontBitmap: Bitmap
    ): TextureCubemap = TextureCubemap(createTexture()).apply {
        use {
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

            texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_X, 0, leftBitmap, 0)
            texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X, 0, rightBitmap, 0)
            texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, 0, bottomBitmap, 0)
            texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Y, 0, topBitmap, 0)
            texImage2D(GL_TEXTURE_CUBE_MAP_NEGATIVE_Z, 0, backBitmap, 0)
            texImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_Z, 0, frontBitmap, 0)
        }
    }

    private fun createTexture(): Int {
        glGenTextures(1, arrays, 0)
        return arrays[0]
    }

    internal fun destroyTexture(id: Int) {
        arrays[0] = id
        glDeleteTextures(1, arrays, 0)
    }

}
