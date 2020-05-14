package org.mrlem.siage3d.core.common.gl.arrays

import android.opengl.GLES30.*
import java.nio.FloatBuffer
import java.nio.ShortBuffer

class Vbo {

    private val id: Int

    init {
        glGenBuffers(1, arrays, 0)
        id = arrays[0]
    }

    fun toAttribute(attributeNumber: Int, components: Int, buffer: FloatBuffer) {
        glBindBuffer(GL_ARRAY_BUFFER, id)
        glBufferData(GL_ARRAY_BUFFER, buffer.capacity() * BYTES_PER_FLOAT, buffer, GL_STATIC_DRAW)
        glEnableVertexAttribArray(attributeNumber)
        glVertexAttribPointer(attributeNumber, components, GL_FLOAT, false, components * BYTES_PER_FLOAT, 0)
    }

    fun toIndexBuffer(buffer: ShortBuffer) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer.capacity() * BYTES_PER_SHORT, buffer, GL_STATIC_DRAW)
    }

    fun destroy() {
        arrays[0] = id
        glDeleteBuffers(1, arrays, 0)
    }

    companion object {
        private const val BYTES_PER_FLOAT = 4
        private const val BYTES_PER_SHORT = 2

        private val arrays = intArrayOf(0)
    }

}
