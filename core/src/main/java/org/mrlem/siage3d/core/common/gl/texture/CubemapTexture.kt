package org.mrlem.siage3d.core.common.gl.texture

import android.opengl.GLES30.*

class CubemapTexture(id: Int) : Texture(id) {

    fun use() {
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_CUBE_MAP, id)
    }

}
