package org.mrlem.siage3d.core.scene.graph.resources.shapes

import org.mrlem.siage3d.core.common.io.caches.VaoCache

class TriangleShape : Shape(VaoCache.get("triangle") { data }) {

    companion object {

        private val positions = floatArrayOf(   // counter-clockwise:
            0.0f, 0.622008459f, 0.0f,           // top
            -0.5f, -0.311004243f, 0.0f,         // bottom left
            0.5f, -0.311004243f, 0.0f           // bottom right
        )

        private val texCoords = floatArrayOf(
            0.0f, 0.622008459f,
            -0.5f, -0.311004243f,
            0.5f, -0.311004243f
        )

        private val indices = shortArrayOf(0, 1, 2)

        private val normals = floatArrayOf(
            0f, 0f, -1f,
            0f, 0f, -1f,
            0f, 0f, -1f
        )

        private val data = Data(positions, texCoords, indices, normals)

    }

}
