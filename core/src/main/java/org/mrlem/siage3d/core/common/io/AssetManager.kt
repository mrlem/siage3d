package org.mrlem.siage3d.core.common.io

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.graphics.BitmapFactory
import org.mrlem.siage3d.core.common.io.caches.VaoCache
import org.mrlem.siage3d.core.common.io.caches.Texture2DCache
import org.mrlem.siage3d.core.common.io.caches.TextureCubemapCache
import org.mrlem.siage3d.core.scene.graph.resources.shapes.Shape

object AssetManager {

    private lateinit var assets: AssetManager
    private lateinit var res: Resources

    fun init(context: Context) {
        if (!::assets.isInitialized) {
            assets = context.applicationContext.resources.assets
            res = context.applicationContext.resources
        }
    }

    fun bitmap(resId: Int) = BitmapFactory.decodeResource(res, resId, BitmapFactory.Options().apply {
        inScaled = false
    })

    fun shape(resId: Int) = Shape(VaoCache.ref(res, resId))

    fun text(resId: Int) = res.openRawResource(resId).bufferedReader().use { it.readText() }

    fun texture2D(resId: Int) = Texture2DCache.ref(res, resId)

    fun textureCubemap(resId: Int) = TextureCubemapCache.ref(res, resId)

}
