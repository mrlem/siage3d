package org.mrlem.siage3d.core.common.gl

import android.opengl.GLES30.*

/**
 * Frame buffer object: used for off-screen rendering.
 */
class Fbo {

    private val id: Int

    init {
        glGenFramebuffers(1, arrays, 0)
        id = arrays[0]
    }

    fun use(block: () -> Unit) {
        glBindFramebuffer(GL_FRAMEBUFFER, id)
        block()
        glBindFramebuffer(GL_FRAMEBUFFER, 0)
    }

    fun destroy() {
        arrays[0] = id
        glDeleteFramebuffers(1, arrays, 0)
    }

    companion object {

        private val arrays = intArrayOf(0)

    }

}
