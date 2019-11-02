package org.mrlem.k3d.core.common.io

import org.mrlem.k3d.core.common.gl.Vao
import org.mrlem.k3d.core.scene.shapes.Mesh

object VaoCache {

    private val vaos = mutableMapOf<String, Vao>()

    fun get(id: String, mesh: Mesh): Vao {
        val key = "custom:$id"

        // in cache?
        vaos[key]?.also { cache -> return cache }

        // or create it
        return Vao.load(mesh).also {
            vaos[key] = it
        }
    }

    fun clear(destroy: Boolean = false) {
        if (destroy) {
            vaos.values.forEach(Vao::destroy)
        }
        vaos.clear()
    }

}
