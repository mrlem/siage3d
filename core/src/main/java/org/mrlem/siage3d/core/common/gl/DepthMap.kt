package org.mrlem.siage3d.core.common.gl

import android.opengl.GLES30.*

class DepthMap(private val size: Int) {

    private val fbo = Fbo()
    val texture = TextureFactory.createDepthMap(fbo, size, size)

    fun use(block: () -> Unit) {
        glViewport(0, 0, size, size)
        glClear(GL_DEPTH_BUFFER_BIT)

        fbo.use {
            block()
        }
    }

    fun destroy() {
        fbo.destroy()
        texture.destroy()
    }

}
