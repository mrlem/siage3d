package org.mrlem.siage3d.core.common.gl

import android.opengl.GLES30.*
import org.mrlem.siage3d.core.common.io.toBuffer
import org.mrlem.siage3d.core.scene.shaders.DefaultShader
import org.mrlem.siage3d.core.scene.shapes.Mesh
import java.nio.IntBuffer

class Vao private constructor(val indicesCount: Int) {

    private val id: Int
    private val vbos: MutableList<Vbo> = mutableListOf()

    init {
        glGenVertexArrays(1, arrays)
        id = arrays[0]
    }

    fun use(block: Vao.() -> Unit) {
        glBindVertexArray(id)
            this.block()
        glBindVertexArray(0)
    }

    fun destroy() {
        vbos.forEach(Vbo::destroy)
        vbos.clear()

        arrays.put(0, id)
        glDeleteVertexArrays(1, arrays)
    }

    private fun addVbo(block: Vbo.() -> Unit) = Vbo().also {
        vbos.add(it)
        it.block()
    }

    companion object {
        private val arrays = IntBuffer.allocate(1)

        fun load(mesh: Mesh): Vao {
            return Vao(mesh.indices.size).apply {
                use {
                    addVbo { toAttribute(DefaultShader.ATTR_POSITIONS, 3, mesh.positions.toBuffer()) }
                    addVbo { toAttribute(DefaultShader.ATTR_TEXCOORDS, 2, mesh.texCoords.toBuffer()) }
                    addVbo { toAttribute(DefaultShader.ATTR_NORMALS, 3, mesh.normals.toBuffer()) }
                    addVbo { toIndexBuffer(mesh.indices.toBuffer()) }
                }
                glBindBuffer(GL_ARRAY_BUFFER, 0)
                glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0)
            }
        }
    }

}
