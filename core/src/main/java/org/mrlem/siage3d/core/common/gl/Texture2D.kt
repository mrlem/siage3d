package org.mrlem.siage3d.core.common.gl

import android.opengl.GLES30.*

class Texture2D(id: Int) : Texture(id) {

    fun use(block: Texture2D.() -> Unit) {
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, id)
        this.block()
        glBindTexture(GL_TEXTURE_2D, 0)
    }

}
