package org.mrlem.siage3d.core.common.io.loaders

import android.graphics.Color
import androidx.annotation.RawRes
import androidx.core.graphics.get
import org.mrlem.siage3d.core.common.io.AssetManager
import org.mrlem.siage3d.core.scene.shapes.Terrain
import kotlin.math.min

class HeightMapLoader {

    fun load(@RawRes resId: Int): Terrain.HeightMap {
        val bitmap = AssetManager.bitmap(resId)
        val size = min(bitmap.width, bitmap.height)
        val maxValue = 255 * 3

        val heightMap = Terrain.HeightMap(size)
        for (x in 0 until size) {
            for (y in 0 until size) {
                val pixel = bitmap[x, y]
                val r = Color.red(pixel)
                val g = Color.green(pixel)
                val b = Color.blue(pixel)
                heightMap.heights[x][y] = (r + g + b).toFloat() / maxValue
            }
        }
        bitmap.recycle()
        return heightMap
    }

}
