package org.mrlem.siage3d.core.common.io

import android.content.res.Resources
import android.graphics.BitmapFactory

fun Resources.readText(resId: Int) = openRawResource(resId).bufferedReader().use { it.readText() }

fun Resources.readBitmap(resId: Int) = BitmapFactory.decodeResource(this, resId)

fun Resources.readTexture2D(resId: Int) = Texture2DCache.get(this, resId)

fun Resources.readTextureCubemap(resId: Int) = TextureCubemapCache.get(this, resId)

fun Resources.readShape(resId: Int) = ShapeCache.get(this, resId)
