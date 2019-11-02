package org.mrlem.k3d.core.scene.shapes

import android.opengl.GLES30.*
import org.mrlem.k3d.core.common.gl.Vao
import org.mrlem.k3d.core.common.io.VaoCache

// TODO - major - add model loader

open class Shape(
    positions: FloatArray,
    texCoords: FloatArray,
    indices: ShortArray,
    normals: FloatArray
) {

    private val vao: Vao

    init {
        val identifier = javaClass.simpleName
        vao = VaoCache.get(identifier, Mesh(positions, texCoords, indices, normals))
    }

    fun draw() {
        vao.use {
            glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_SHORT, 0)
        }
    }

}
