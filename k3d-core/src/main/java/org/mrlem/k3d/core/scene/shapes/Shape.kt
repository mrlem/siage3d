package org.mrlem.k3d.core.scene.shapes

import android.opengl.GLES30.*
import org.mrlem.k3d.core.common.gl.Vao

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
        vao = vaos[javaClass.simpleName] ?: Vao.load(
            positions,
            texCoords,
            normals,
            indices
        ).also {
            vaos[identifier] = it
        }
    }

    fun draw() {
        vao.use {
            glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_SHORT, 0)
        }
    }

    companion object {
        private val vaos: MutableMap<String, Vao> = mutableMapOf()
    }

}
