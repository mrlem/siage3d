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
        vao = VaoCache.get(identifier, Mesh(positions, texCoords, indices, normals))
    }

    constructor(resources: Resources, resId: Int) {
        vao = VaoCache.get(resources, resId)
    }

    fun draw() {
        vao.use {
            glDrawElements(GL_TRIANGLES, indicesCount, GL_UNSIGNED_SHORT, 0)
        }
    }

}
