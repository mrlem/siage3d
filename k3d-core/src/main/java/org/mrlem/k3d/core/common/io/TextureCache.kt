package org.mrlem.k3d.core.common.io

import android.content.res.Resources
import org.mrlem.k3d.core.common.gl.Texture

// TODO - optional - cache abstract class
// TODO - optional - cubemaps, see https://stackoverflow.com/questions/35565251/memory-violation-in-android-opengl-gles20-with-glteximage2d

object TextureCache {

    private const val RESOURCE_CACHE_SCHEME = "res"

    private val textures = mutableMapOf<String, Texture>()

    // TODO - optional - resources extension method
    fun get(resources: Resources, resId: Int): Texture {
        val key = "$RESOURCE_CACHE_SCHEME:$resId"

        // in cache?
        textures[key]?.also { cache -> return cache }

        // or create it
        val bitmap = resources.readBitmap(resId)
        return Texture.load(bitmap).also {
            textures[key] = it
            bitmap.recycle()
        }
    }

    fun clear(destroy: Boolean = false) {
        if (destroy) {
            textures.values.forEach(Texture::destroy)
        }
        textures.clear()
    }

}
