package org.mrlem.siage3d.core.scene.shapes

import org.joml.Vector3f
import kotlin.math.max
import kotlin.math.min

class Terrain(
    private val size: Float,
    private val heightMap: HeightMap = HeightMap(32) { 0f },
    private val maxHeight: Float = 1f
) : Shape(Grid.generateGrid(size, heightMap.size).applyHeights(heightMap, maxHeight)) {

    fun heightAt(x: Float, z: Float): Float {
        // TODO - barycenter
        val tileSize = size / heightMap.size
        val zIndex = ((z + size/2) / tileSize).toInt().coerceIn(0 until heightMap.size)
        val xIndex = ((x + size/2) / tileSize).toInt().coerceIn(0 until heightMap.size)
        return heightMap.heights[zIndex][xIndex] * maxHeight
    }

    companion object {

        fun Data.applyHeights(heightMap: HeightMap, maxHeight: Float): Data {
            for (i in 1 .. positions.size step 3) {
                val vertexIndex = i / 3
                val row = vertexIndex / heightMap.size
                val col = vertexIndex % heightMap.size

                // height
                positions[i] = heightMap.heights[row][col] * maxHeight

                // normal
                val normal = calculateNormal(row, col, heightMap.heights, maxHeight)
                normals[i - 1] = normal.x
                normals[i] = normal.y
                normals[i + 1] = normal.z
            }

            return this
        }

        private fun calculateNormal(x: Int, z: Int, heights: Array<Array<Float>>, maxHeight: Float): Vector3f {
            val max = heights.size - 1
            val heightLeft = heights[max(0, x-1)][z] * maxHeight
            val heightRight = heights[min(max, x+1)][z] * maxHeight
            val heightUp = heights[x][min(max, z+1)] * maxHeight
            val heightDown = heights[x][max(0, z-1)] * maxHeight
            return Vector3f(heightLeft - heightRight, 2f, heightDown - heightUp).normalize()
        }

    }

    class HeightMap(val size: Int, init: (Int) -> Float = { 0f }) {

        val heights: Array<Array<Float>> = Array(size) { Array(size, init) }

    }

}
