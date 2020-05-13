package org.mrlem.siage3d.core.common.gl.texture

import android.opengl.GLES30.*
import org.mrlem.siage3d.core.common.gl.texture.Texture

class Texture2D(id: Int) : Texture(id) {

    fun use(texture: Int = GL_TEXTURE0) {
        glActiveTexture(texture)
        glBindTexture(GL_TEXTURE_2D, id)
    }

}
