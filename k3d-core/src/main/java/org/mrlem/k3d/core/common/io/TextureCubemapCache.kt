package org.mrlem.k3d.core.common.io

import android.content.res.Resources
import android.graphics.Bitmap
import androidx.annotation.ArrayRes
import org.mrlem.k3d.core.common.gl.TextureCubemap
import org.mrlem.k3d.core.common.gl.TextureFactory

object TextureCubemapCache : AbstractCache<TextureCubemap>() {

    override fun create(resources: Resources, @ArrayRes resId: Int): TextureCubemap {
        val faceResIds = resources.getIntArray(resId)
        val bitmaps = mutableListOf<Bitmap>()
        return TextureFactory.load(
            resources.readBitmap(faceResIds[0]).also { bitmaps.add(it) },
            resources.readBitmap(faceResIds[1]).also { bitmaps.add(it) },
            resources.readBitmap(faceResIds[2]).also { bitmaps.add(it) },
            resources.readBitmap(faceResIds[3]).also { bitmaps.add(it) },
            resources.readBitmap(faceResIds[4]).also { bitmaps.add(it) },
            resources.readBitmap(faceResIds[5]).also { bitmaps.add(it) }
        ).also {
            bitmaps.forEach(Bitmap::recycle)
        }
    }

    fun clear(destroy: Boolean = false) {
        if (destroy) {
            objects.values.forEach(TextureCubemap::destroy)
        }
        super.clear()
    }

}
