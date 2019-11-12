package org.mrlem.k3d.core.common.io

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.annotation.ArrayRes
import org.mrlem.k3d.core.common.gl.TextureCubemap
import org.mrlem.k3d.core.common.gl.TextureFactory

object TextureCubemapCache : AbstractCache<TextureCubemap>() {

    @SuppressLint("ResourceType")
    override fun create(resources: Resources, @ArrayRes resId: Int): TextureCubemap {
        val faceResIds = resources.obtainTypedArray(resId)
        val bitmaps = mutableListOf<Bitmap>()
        return TextureFactory.load(
            resources.readBitmap(faceResIds.getResourceId(0, 0)).also { bitmaps.add(it) },
            resources.readBitmap(faceResIds.getResourceId(1, 0)).also { bitmaps.add(it) },
            resources.readBitmap(faceResIds.getResourceId(2, 0)).also { bitmaps.add(it) },
            resources.readBitmap(faceResIds.getResourceId(3, 0)).also { bitmaps.add(it) },
            resources.readBitmap(faceResIds.getResourceId(4, 0)).also { bitmaps.add(it) },
            resources.readBitmap(faceResIds.getResourceId(5, 0)).also { bitmaps.add(it) }
        ).also {
            bitmaps.forEach(Bitmap::recycle)
            faceResIds.recycle()
        }
    }

    fun clear(destroy: Boolean = false) {
        if (destroy) {
            objects.values.forEach(TextureCubemap::destroy)
        }
        super.clear()
    }

}
