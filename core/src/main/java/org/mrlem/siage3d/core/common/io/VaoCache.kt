package org.mrlem.siage3d.core.common.io

import android.content.res.Resources
import org.mrlem.siage3d.core.common.gl.Vao
import org.mrlem.siage3d.core.common.io.loaders.ObjLoader
import org.mrlem.siage3d.core.scene.shapes.Shape

object VaoCache : AbstractCache<Vao>() {

    override fun create(resources: Resources, resId: Int): Vao {
        return Vao.load(ObjLoader().load(resources.readText(resId)))
    }

    fun get(id: String, data: Shape.Data) = getOrCreate("custom:$id") { Vao.load(data) }

    fun clear(destroy: Boolean = false) {
        super.clear()
        if (destroy) {
            objects.values.forEach(Vao::destroy)
        }
    }

}
