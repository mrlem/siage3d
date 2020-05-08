package org.mrlem.siage3d.core.common.math

import org.joml.Vector3f

class BoundingBox {

    var minX: Float = 0f
    var maxX: Float = 0f
    var minY: Float = 0f
    var maxY: Float = 0f
    var minZ: Float = 0f
    var maxZ: Float = 0f

    fun reset() {
        minX = 0f
        maxX = 0f
        minY = 0f
        maxY = 0f
        minZ = 0f
        maxZ = 0f
    }

    fun update(points: List<Vector3f>) {
        points.forEachIndexed { index, point ->
            if (index == 0) {
                minX = point.x
                maxX = point.x
                minY = point.y
                maxY = point.y
                minZ = point.z
                maxZ = point.z
            } else {
                if (point.x < minX) minX = point.x
                else if (point.x > maxX) maxX = point.x

                if (point.y < minY) minY = point.y
                else if (point.y > maxY) maxY = point.y

                if (point.z < minZ) minZ = point.z
                else if (point.z > maxZ) maxZ = point.z
            }
        }
    }

}
