package org.mrlem.k3d.core.common.gl

import android.opengl.GLES30.*
import org.mrlem.k3d.core.common.io.toBuffer
import org.mrlem.k3d.core.scene.shaders.DefaultShader
import java.nio.IntBuffer

class Vao(val vertexCount: Int) {

    private val id: Int = create()

    private val vbos: MutableList<Vbo> = mutableListOf()

    fun use(block: Vao.() -> Unit): Vao {
        glBindVertexArray(id)
            this.block()
        glBindVertexArray(0)
        return this
    }

    private fun create(): Int {
        glGenVertexArrays(1, arrays)
        return arrays[0]
    }

    fun destroy() {
        vbos.forEach(Vbo::destroy)
        vbos.clear()

        arrays.put(0, id)
        glDeleteVertexArrays(1, arrays)
    }

    private fun addVbo(block: Vbo.() -> Unit) = Vbo().also {
        it.block()
        vbos.add(it)
    }

    companion object {
        private val arrays = IntBuffer.allocate(1)

        fun load(
            positions: FloatArray,
            textureCoords: FloatArray,
            normals: FloatArray,
            indices: ShortArray
        ) = Vao(indices.size).use {
            addVbo { toIndexBuffer(indices.toBuffer()) }
            addVbo { toAttribute(DefaultShader.ATTR_POSITIONS, 3, positions.toBuffer()) }
            addVbo { toAttribute(DefaultShader.ATTR_TEXCOORDS, 2, textureCoords.toBuffer()) }
            addVbo { toAttribute(DefaultShader.ATTR_NORMALS, 3, normals.toBuffer()) }
        }
    }

}
