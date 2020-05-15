package org.mrlem.siage3d.core.common.io.caches

import android.content.res.Resources
import androidx.annotation.DrawableRes
import org.mrlem.siage3d.core.common.gl.texture.Texture2D
import org.mrlem.siage3d.core.common.gl.texture.TextureFactory
import org.mrlem.siage3d.core.common.io.AssetManager.bitmap

object Texture2DCache : AbstractCache<Texture2D>() {

    override fun ref(resources: Resources, resId: Int): Ref<Texture2D> = Texture2DRef(resources, resId)

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

    private class Texture2DRef(
        private val resources: Resources,
        @DrawableRes private val resId: Int
    ) : AbstractCache.Ref<Texture2D>() {

        override var value: Texture2D? = create()

        override fun create(): Texture2D = Texture2DCache.get(resources, resId)

    }

}
