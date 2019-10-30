package org.mrlem.k3d.core.common.io

import android.content.res.Resources
import android.graphics.BitmapFactory

fun Resources.readText(resId: Int) = openRawResource(resId).bufferedReader().use { it.readText() }

fun Resources.readBitmap(resId: Int) = BitmapFactory.decodeResource(this, resId)
