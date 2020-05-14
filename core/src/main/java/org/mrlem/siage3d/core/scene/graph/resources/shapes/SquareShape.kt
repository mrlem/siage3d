package org.mrlem.siage3d.core.scene.graph.resources.shapes

import org.mrlem.siage3d.core.common.io.caches.VaoCache

class SquareShape : Shape(VaoCache.ref("square") { data }) {

    companion object {

        private val positions = floatArrayOf(       // counterclockwise:
            -0.5f,  0.5f, 0.0f,                     // top left
            -0.5f, -0.5f, 0.0f,                     // bottom left
            0.5f, -0.5f, 0.0f,                      // bottom right
            0.5f,  0.5f, 0.0f                       // top right
        )

        private val texCoords = floatArrayOf(
            0f, 1f,
            0f, 0f,
            1f, 0f,
            1f, 1f
        )

        private val indices = shortArrayOf(0, 1, 2, 0, 2, 3)

        private val normals = floatArrayOf(
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f
        )

        private val data = Shape.Data(positions, texCoords, indices, normals)

    }

}
