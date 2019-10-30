package org.mrlem.k3d.core.scene.shapes

class Square : Shape(
    positions,
    texCoords,
    indices,
    normals
) {

    companion object {

        private val positions = floatArrayOf(       // counterclockwise:
            -0.5f,  0.5f, 0.0f,                     // top left
            -0.5f, -0.5f, 0.0f,                     // bottom left
            0.5f, -0.5f, 0.0f,                      // bottom right
            0.5f,  0.5f, 0.0f                       // top right
        )

        private val texCoords = floatArrayOf(
            -0.5f,  0.5f,
            -0.5f, -0.5f,
            0.5f, -0.5f,
            0.5f,  0.5f
        )

        private val indices = shortArrayOf(0, 1, 2, 0, 2, 3)

        private val normals = floatArrayOf(
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f,
            0f, 0f, 1f
        )

    }

}
