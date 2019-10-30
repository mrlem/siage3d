package org.mrlem.k3d.core.common.gl

import android.opengl.GLES30.*
import org.mrlem.k3d.core.common.io.toBuffer
import java.nio.IntBuffer

class Vao(val vertexCount: Int) {

    private val id: Int = create()

    private val vbos: MutableList<Vbo> = mutableListOf()

    private fun declare(block: Vao.() -> Unit): Vao {
        glBindVertexArray(id)
            block.invoke(this)
        glBindVertexArray(0)
        return this
    }

    fun use(block: Vao.() -> Unit): Vao {
        glBindVertexArray(id)
            glEnableVertexAttribArray(0)
            glEnableVertexAttribArray(1)
            glEnableVertexAttribArray(2)
            block.invoke(this)
        glBindVertexArray(0)
        return this
    }

    private fun create(): Int {
        glGenVertexArrays(1, arrays)
        return arrays[0]
    }

    fun destroy() {
        vbos.forEach(Vbo::destroy)

        arrays.put(0, id)
        glDeleteVertexArrays(1, arrays)
    }

    private fun addVbo(block: Vbo.() -> Unit) = Vbo().also {
        block.invoke(it)
        vbos.add(it)
    }

    companion object {
        private val arrays = IntBuffer.allocate(1)

        fun load(
            positions: FloatArray,
            textureCoords: FloatArray,
            normals: FloatArray,
            indices: ShortArray
        ) = Vao(positions.size).declare {
            addVbo { toIndexBuffer(indices.toBuffer()) }
            addVbo { toAttribute(0, 3, positions.toBuffer()) }
            addVbo { toAttribute(1, 2, textureCoords.toBuffer()) }
            addVbo { toAttribute(2, 3, normals.toBuffer()) }
        }
    }

}
