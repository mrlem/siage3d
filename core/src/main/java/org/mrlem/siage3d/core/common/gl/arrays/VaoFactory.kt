package org.mrlem.siage3d.core.common.gl.arrays

import android.opengl.GLES30.*
import org.mrlem.siage3d.core.common.gl.shaders.Shader
import org.mrlem.siage3d.core.common.io.toBuffer
import org.mrlem.siage3d.core.scene.graph.resources.shapes.Shape
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.ShortBuffer

object VaoFactory {

    private const val BYTES_PER_FLOAT = 4
    private const val BYTES_PER_SHORT = 2

    private val buffers = IntBuffer.allocate(1)
    private val arrays = intArrayOf(0)

    fun createVao(data: Shape.Data) = Vao(createVao(), data.indices.size).apply {
        use {
            createAttribVbo(Shader.Attribute.POSITIONS.index, 3, data.positions.toBuffer())
            createAttribVbo(Shader.Attribute.TEXCOORDS.index, 2, data.texCoords.toBuffer())
            createAttribVbo(Shader.Attribute.NORMALS.index, 3, data.normals.toBuffer())
            createIndexBugfferVbo(data.indices.toBuffer())
        }
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
    }

    fun destroyVao(vao: Vao) {
        vao.vbos.map { it.id }.forEach(::destroyVbo)
        vao.vbos.clear()

        destroyVao(vao.id)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun Vao.createIndexBugfferVbo(buffer: ShortBuffer) = Vbo(createVbo()).apply {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer.capacity() * BYTES_PER_SHORT, buffer, GL_STATIC_DRAW)
    }
        .also { vbos.add(it) }

    private fun Vao.createAttribVbo(attribNumber: Int, components: Int, buffer: FloatBuffer) = Vbo(createVbo()).apply {
        glBindBuffer(GL_ARRAY_BUFFER, id)
        glBufferData(GL_ARRAY_BUFFER, buffer.capacity() * BYTES_PER_FLOAT, buffer, GL_STATIC_DRAW)
        glEnableVertexAttribArray(attribNumber)
        glVertexAttribPointer(attribNumber, components, GL_FLOAT, false, components * BYTES_PER_FLOAT, 0)
    }
        .also { vbos.add(it) }

    private fun createVao(): Int {
        glGenVertexArrays(1, buffers)
        return buffers[0]
    }

    private fun createVbo(): Int {
        glGenBuffers(1, arrays, 0)
        return arrays[0]
    }

    private fun destroyVao(id: Int) {
        buffers.put(0, id)
        glDeleteVertexArrays(1, buffers)
    }

    private fun destroyVbo(id: Int) {
        arrays[0] = id
        glDeleteBuffers(1, arrays, 0)
    }

}
