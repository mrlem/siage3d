package org.mrlem.siage3d.core.common.io.caches

import android.content.res.Resources
import org.mrlem.siage3d.core.common.gl.texture.Texture2D
import org.mrlem.siage3d.core.common.gl.texture.TextureFactory
import org.mrlem.siage3d.core.common.io.AssetManager.bitmap

object Texture2DCache : AbstractCache<Texture2D>() {

    override fun create(resources: Resources, resId: Int): Texture2D {
        val bitmap = bitmap(resId)
        return TextureFactory.createTexture2D(bitmap).also {
            bitmap.recycle()
        }
    }

    fun clear(destroy: Boolean = false) {
        if (destroy) {
            objects.values.forEach(TextureFactory::destroyTexture)
        }
        super.clear()
    }

}
