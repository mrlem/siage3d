package org.mrlem.siage3d.core.scene.shapes

class Grid(size: Float, vertexCount: Int) : Shape(generateGrid(size, vertexCount)) {

    companion object {

        fun generateGrid(size: Float, vertexCount: Int): Data {
            val count: Int = vertexCount * vertexCount

            val vertices = FloatArray(count * 3)
            val normals = FloatArray(count * 3)
            val textureCoords = FloatArray(count * 2)
            val indices = ShortArray(6 * (vertexCount - 1) * (vertexCount - 1))

            var vertexPointer = 0
            for (i in 0 until vertexCount) {
                for (j in 0 until vertexCount) {
                    vertices[vertexPointer * 3] = j.toFloat() / (vertexCount.toFloat() - 1) * size
                    vertices[vertexPointer * 3 + 1] = 0f
                    vertices[vertexPointer * 3 + 2] = i.toFloat() / (vertexCount.toFloat() - 1) * size
                    normals[vertexPointer * 3] = 0f
                    normals[vertexPointer * 3 + 1] = 1f
                    normals[vertexPointer * 3 + 2] = 0f
                    textureCoords[vertexPointer * 2] =
                        j.toFloat() / (vertexCount.toFloat() - 1)
                    textureCoords[vertexPointer * 2 + 1] =
                        i.toFloat() / (vertexCount.toFloat() - 1)
                    vertexPointer++
                }
            }
            var pointer = 0
            for (gz in 0 until vertexCount - 1) {
                for (gx in 0 until vertexCount - 1) {
                    val topLeft: Int = gz * vertexCount + gx
                    val topRight = topLeft + 1
                    val bottomLeft: Int = (gz + 1) * vertexCount + gx
                    val bottomRight = bottomLeft + 1
                    indices[pointer++] = topLeft.toShort()
                    indices[pointer++] = bottomLeft.toShort()
                    indices[pointer++] = topRight.toShort()
                    indices[pointer++] = topRight.toShort()
                    indices[pointer++] = bottomLeft.toShort()
                    indices[pointer++] = bottomRight.toShort()
                }
            }

            return Data(vertices, textureCoords, indices, normals)
        }

    }

}
