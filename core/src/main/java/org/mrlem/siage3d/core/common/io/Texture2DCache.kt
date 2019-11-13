package org.mrlem.siage3d.core.common.io

import android.content.res.Resources
import org.mrlem.siage3d.core.common.gl.Texture2D
import org.mrlem.siage3d.core.common.gl.TextureFactory

object Texture2DCache : AbstractCache<Texture2D>() {

    override fun create(resources: Resources, resId: Int): Texture2D {
        val bitmap = resources.readBitmap(resId)
        return TextureFactory.load(bitmap).also {
            bitmap.recycle()
        }
    }

    fun clear(destroy: Boolean = false) {
        if (destroy) {
            objects.values.forEach(Texture2D::destroy)
        }
        super.clear()
    }

}
