package org.mrlem.siage3d.core.scene.shapes

import org.joml.Vector3f
import kotlin.math.max
import kotlin.math.min

class Terrain(
    private val heightMap: HeightMap = HeightMap(32) { 0f },
    private val maxHeight: Float = 0.1f
) : Shape(Grid.generateGrid(1f, heightMap.size).applyHeights(heightMap, maxHeight)) {

    fun heightAt(x: Float, z: Float): Float {
        // TODO - optional - barycenter
        val tileSize = 1f / heightMap.size
        val zIndex = ((z + 0.5f) / tileSize).toInt().coerceIn(0 until heightMap.size)
        val xIndex = ((x + 0.5f) / tileSize).toInt().coerceIn(0 until heightMap.size)
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
                val normal = heightMap.heights.calculateNormal(row, col, maxHeight)
                normals[i - 1] = normal.x
                normals[i] = normal.y
                normals[i + 1] = normal.z
            }

            return this
        }

        private fun Array<Array<Float>>.calculateNormal(x: Int, z: Int, maxHeight: Float): Vector3f {
            // TODO - optional - could base calculation on 4 surrounding quads, rather than 4 surrounding points
            val max = size - 1

            // surrounding indices
            val xLeft = max(0, x-1)
            val xRight = min(max, x+1)
            val zUp = min(max, z+1)
            val zDown = max(0, z-1)

            return calculateQuadNormal(
                pointAt(xLeft, z, maxHeight),
                pointAt(x, zUp, maxHeight),
                pointAt(xRight, z, maxHeight),
                pointAt(x, zDown, maxHeight)
            )
        }

        private fun Array<Array<Float>>.pointAt(x: Int, z: Int, maxHeight: Float): Vector3f {
            val max = size - 1
            return Vector3f(x.toFloat() / max, this[x][z] * maxHeight, z.toFloat() / max)
        }

        /**
         * Points must be provided clockwise for correct calculation.
         */
        private fun calculateQuadNormal(p1: Vector3f, p2: Vector3f, p3: Vector3f, p4: Vector3f): Vector3f {
            // resulting quad: normals for both triangles
            val normal1 = p2.sub(p1, Vector3f())
                .cross(p3.sub(p2, Vector3f())).normalize()
            val normal2 = p4.sub(p3, Vector3f())
                .cross(p1.sub(p4, Vector3f())).normalize()

            // quad normal: average of triangles normals
            return normal1.add(normal2).normalize()
        }

    }

    class HeightMap(val size: Int, init: (Int) -> Float = { 0f }) {

        val heights: Array<Array<Float>> = Array(size) { Array(size, init) }

    }

}
