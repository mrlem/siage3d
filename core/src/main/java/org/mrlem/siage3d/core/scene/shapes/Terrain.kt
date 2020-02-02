package org.mrlem.siage3d.core.scene.shapes

import org.mrlem.siage3d.core.common.math.randomFloat

class Terrain(
    size: Float,
    heightMap: HeightMap = HeightMap(32) { randomFloat() }
) : Shape(Grid.generateGrid(size, heightMap.size).applyHeights(heightMap)) {

    companion object {

        fun Data.applyHeights(heightMap: HeightMap): Data {
            for (i in 1 .. positions.size step 3) {
                val vertexIndex = i / 3
                val row = vertexIndex / heightMap.size
                val col = vertexIndex % heightMap.size
                positions[i] = heightMap.heights[row][col]
            }
            return this
        }

    }

    class HeightMap(val size: Int, init: (Int) -> Float = { 0f }) {

        val heights: Array<Array<Float>> = Array(size) { Array(size, init) }

    }

}
