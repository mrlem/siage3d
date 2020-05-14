package org.mrlem.siage3d.core.common.gl.arrays

import android.opengl.GLES30.*

class Vao(
    val id: Int,
    val indicesCount: Int
) {

    internal val vbos: MutableList<Vbo> = mutableListOf()

    fun use(block: Vao.() -> Unit) {
        glBindVertexArray(id)
            this.block()
        glBindVertexArray(0)
    }

}
