package org.mrlem.siage3d.core.scene.graph.resources.shapes

import org.mrlem.siage3d.core.common.io.caches.VaoCache

class BoxShape : Shape(VaoCache.get("box") { data }) {

    companion object {

        private val positions = floatArrayOf(       // counterclockwise:

            // front
            -0.5f,  0.5f, 0.5f,                     // top left
            -0.5f, -0.5f, 0.5f,                     // bottom left
             0.5f, -0.5f, 0.5f,                     // bottom right
             0.5f,  0.5f, 0.5f,                     // top right
            // back
             0.5f,  0.5f, -0.5f,
             0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,
            // top
            -0.5f, 0.5f, -0.5f,
            -0.5f, 0.5f,  0.5f,
             0.5f, 0.5f,  0.5f,
             0.5f, 0.5f, -0.5f,
            // bottom
            -0.5f, -0.5f,  0.5f,
            -0.5f, -0.5f, -0.5f,
             0.5f, -0.5f, -0.5f,
             0.5f, -0.5f,  0.5f,
            // left
            -0.5f,  0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            -0.5f, -0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f,
            // right
            0.5f,  0.5f,  0.5f,
            0.5f, -0.5f,  0.5f,
            0.5f, -0.5f, -0.5f,
            0.5f,  0.5f, -0.5f
        )

        private val texCoords = floatArrayOf(
            // front
            0f, 1f,
            0f, 0f,
            1f, 0f,
            1f, 1f,
            // back
            0f, 1f,
            0f, 0f,
            1f, 0f,
            1f, 1f,
            // top
            0f, 1f,
            0f, 0f,
            1f, 0f,
            1f, 1f,
            // bottom
            0f, 1f,
            0f, 0f,
            1f, 0f,
            1f, 1f,
            // left
            0f, 1f,
            0f, 0f,
            1f, 0f,
            1f, 1f,
            // right
            0f, 1f,
            0f, 0f,
            1f, 0f,
            1f, 1f
        )

        private val indices = shortArrayOf(
            // front
            0, 1, 2,
            0, 2, 3,
            // back
            4, 5, 6,
            4, 6, 7,
            // top
            8, 9, 10,
            8, 10, 11,
            // bottom
            12, 13, 14,
            12, 14, 15,
            // left
            16, 17, 18,
            16, 18, 19,
            // right
            20, 21, 22,
            20, 22, 23
        )

        private val normals = floatArrayOf(
            // front
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f,
            // back
            0f, 0f, -1f,
            0f, 0f, -1f,
            0f, 0f, -1f,
            0f, 0f, -1f,
            // top
            0f, 1f, 0f,
            0f, 1f, 0f,
            0f, 1f, 0f,
            0f, 1f, 0f,
            // bottom
            0f, -1f, 0f,
            0f, -1f, 0f,
            0f, -1f, 0f,
            0f, -1f, 0f,
            // left
            -1f, 0f, 0f,
            -1f, 0f, 0f,
            -1f, 0f, 0f,
            -1f, 0f, 0f,
            // right
            1f, 0f, 0f,
            1f, 0f, 0f,
            1f, 0f, 0f,
            1f, 0f, 0f
        )

        private val data = Shape.Data(positions, texCoords, indices, normals)

    }

}
