package org.mrlem.k3d.core.common.gl

import android.opengl.GLES30.*
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.ShortBuffer

class Vbo {

    private val id: Int = create()

    fun toAttribute(attributeNumber: Int, components: Int, buffer: FloatBuffer) {
        glBindBuffer(GL_ARRAY_BUFFER, id)
            glBufferData(GL_ARRAY_BUFFER, buffer.capacity() * BYTES_PER_FLOAT, buffer, GL_STATIC_DRAW)
            glVertexAttribPointer(attributeNumber, components, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
    }

    fun toIndexBuffer(buffer: ShortBuffer) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id)
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer.capacity() * BYTES_PER_SHORT, buffer, GL_STATIC_DRAW)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    private fun create(): Int {
        glGenBuffers(1, arrays)
        return arrays[0]
    }

    fun destroy() {
        arrays.put(0, id)
        glDeleteBuffers(1, arrays)
    }

    companion object {
        private const val BYTES_PER_FLOAT = 4
        private const val BYTES_PER_SHORT = 2
        private val arrays = IntBuffer.allocate(1)
    }

}
