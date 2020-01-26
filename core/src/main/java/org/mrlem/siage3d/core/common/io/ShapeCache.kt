package org.mrlem.siage3d.core.common.io

import android.content.res.Resources
import org.mrlem.siage3d.core.common.io.AssetManager.text
import org.mrlem.siage3d.core.common.io.loaders.ObjLoader
import org.mrlem.siage3d.core.scene.shapes.Shape

object ShapeCache : AbstractCache<Shape>() {

    override fun create(resources: Resources, resId: Int): Shape {
        return Shape(ObjLoader().load(text(resId)))
    }

    fun clear(destroy: Boolean = false) {
        super.clear()
        if (destroy) {
            objects.values.forEach(Shape::destroy)
        }
    }

}
