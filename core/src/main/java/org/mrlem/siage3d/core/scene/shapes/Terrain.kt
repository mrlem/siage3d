package org.mrlem.siage3d.core.scene.shapes

import org.mrlem.siage3d.core.common.math.randomFloat

/**
 * Note: make sure heights as consistent sizes on both dimensions.
 */
class Terrain(
    size: Float,
    heights: Array<Array<Float>> = generateHeights(32)
) : Shape(Grid.generateGrid(size, heights.size).applyHeights(heights)) {

    companion object {

        fun generateHeights(size: Int): Array<Array<Float>> = Array(size) { Array(size) { randomFloat() } }

        fun Data.applyHeights(heights: Array<Array<Float>>): Data {
            for (i in 1 .. positions.size step 3) {
                val vertexIndex = i / 3
                val row = vertexIndex / heights.size
                val col = vertexIndex % heights.size
                positions[i] = heights[row][col]
            }
            return this
        }

    }

}
