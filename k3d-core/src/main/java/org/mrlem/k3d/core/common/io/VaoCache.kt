package org.mrlem.k3d.core.common.io

import android.content.res.Resources
import org.mrlem.k3d.core.common.gl.Vao
import org.mrlem.k3d.core.common.io.loaders.ObjLoader
import org.mrlem.k3d.core.scene.shapes.Mesh

object VaoCache {

    private const val RESOURCE_CACHE_SCHEME = "res"

    private val vaos = mutableMapOf<String, Vao>()

    fun get(resources: Resources, resId: Int): Vao {
        val key = "$RESOURCE_CACHE_SCHEME:$resId"

        // in cache?
        vaos[key]?.also { cache -> return cache }

        // or create it
        return Vao.load(ObjLoader().load(resources.readText(resId))).also {
            vaos[key] = it
        }
    }

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
