package org.mrlem.siage3d.core.common.io.caches

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.annotation.ArrayRes
import org.mrlem.siage3d.core.common.gl.texture.CubemapTexture
import org.mrlem.siage3d.core.common.gl.texture.TextureFactory
import org.mrlem.siage3d.core.common.io.AssetManager.bitmap

object TextureCubemapCache : AbstractCache<CubemapTexture>() {

    override fun ref(resources: Resources, resId: Int): Ref<CubemapTexture> = TextureCubemapRef(resources, resId)

    @SuppressLint("ResourceType")
    override fun create(resources: Resources, @ArrayRes resId: Int): CubemapTexture {
        val faceResIds = resources.obtainTypedArray(resId)
        val bitmaps = mutableListOf<Bitmap>()
        return TextureFactory.createCubemapTexture(
            bitmap(faceResIds.getResourceId(0, 0)).also { bitmaps.add(it) },
            bitmap(faceResIds.getResourceId(1, 0)).also { bitmaps.add(it) },
            bitmap(faceResIds.getResourceId(2, 0)).also { bitmaps.add(it) },
            bitmap(faceResIds.getResourceId(3, 0)).also { bitmaps.add(it) },
            bitmap(faceResIds.getResourceId(4, 0)).also { bitmaps.add(it) },
            bitmap(faceResIds.getResourceId(5, 0)).also { bitmaps.add(it) }
        ).also {
            bitmaps.forEach(Bitmap::recycle)
            faceResIds.recycle()
        }
    }

    fun clear(destroy: Boolean = false) {
        if (destroy) {
            objects.values.forEach(TextureFactory::destroyTexture)
        }
        super.clear()
    }

    private class TextureCubemapRef(
        private val resources: Resources,
        @ArrayRes private val resId: Int
    ) : AbstractCache.Ref<CubemapTexture>() {

        override var value: CubemapTexture? = create()

        override fun create(): CubemapTexture = TextureCubemapCache.get(resources, resId)

    }

}
