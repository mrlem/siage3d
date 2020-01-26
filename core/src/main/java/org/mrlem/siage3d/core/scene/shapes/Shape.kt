package org.mrlem.siage3d.core.scene.shapes

import android.content.res.Resources
import android.opengl.GLES30.*
import org.mrlem.siage3d.core.common.gl.Vao
import org.mrlem.siage3d.core.common.io.VaoCache

open class Shape {

    private val vao: Vao

    constructor(
        positions: FloatArray,
        texCoords: FloatArray,
        indices: ShortArray,
        normals: FloatArray
    ) {
        val identifier = javaClass.simpleName
        vao = VaoCache.get(identifier, Data(positions, texCoords, indices, normals))
    }

    constructor(resources: Resources, resId: Int) {
        vao = VaoCache.get(resources, resId)
    }

    fun draw() {
        vao.use {
            glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_SHORT, 0)
        }
    }

    data class Data(
        val positions: FloatArray,
        val texCoords: FloatArray,
        val indices: ShortArray,
        val normals: FloatArray
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Data

            if (!positions.contentEquals(other.positions)) return false
            if (!texCoords.contentEquals(other.texCoords)) return false
            if (!indices.contentEquals(other.indices)) return false
            if (!normals.contentEquals(other.normals)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = positions.contentHashCode()
            result = 31 * result + texCoords.contentHashCode()
            result = 31 * result + indices.contentHashCode()
            result = 31 * result + normals.contentHashCode()
            return result
        }

    }

}
