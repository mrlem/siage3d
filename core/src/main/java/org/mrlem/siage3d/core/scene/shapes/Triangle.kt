package org.mrlem.siage3d.core.scene.shapes

class Triangle : Shape(
    positions,
    texCoords,
    indices,
    normals
) {

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

    }

}
