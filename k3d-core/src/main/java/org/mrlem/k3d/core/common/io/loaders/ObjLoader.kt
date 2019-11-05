package org.mrlem.k3d.core.common.io.loaders

import org.joml.Vector2f
import org.joml.Vector3f
import org.mrlem.k3d.core.scene.shapes.Mesh

class ObjLoader {

    fun load(source: String): Mesh {
        val vertices = mutableListOf<Vertex>()
        val textures = mutableListOf<Vector2f>()
        val normals = mutableListOf<Vector3f>()
        val indices = mutableListOf<Short>()

        source.lines().forEach {
            val line = it.split(" ")
            when (line[0]) {
                "v" -> {
                    val position = Vector3f(line[1].toFloat(), line[2].toFloat(), line[3].toFloat())
                    vertices.add(Vertex(vertices.size.toShort(), position))
                }
                "vt" -> {
                    val texture = Vector2f(line[1].toFloat(), line[2].toFloat())
                    textures.add(texture)
                }
                "vn" -> {
                    val normal = Vector3f(line[1].toFloat(), line[2].toFloat(), line[3].toFloat())
                    normals.add(normal)
                }
                "f" -> {
                    parseFaceVertex(line[1], vertices, indices)
                    parseFaceVertex(line[2], vertices, indices)
                    parseFaceVertex(line[3], vertices, indices)
                }
            }
        }

        removeUnusedVertices(vertices)

		val verticesArray = FloatArray(vertices.size * 3)
		val texturesArray = FloatArray(vertices.size * 2)
		val normalsArray = FloatArray(vertices.size * 3)
        convertDataToArrays(
            vertices,
            textures,
            normals,
            verticesArray,
            texturesArray,
            normalsArray
        )
		val indicesArray = convertIndicesListToArray(indices)
        return Mesh(verticesArray, texturesArray, indicesArray, normalsArray)
    }

    private fun parseFaceVertex(vertexString: String, vertices: MutableList<Vertex>, indices: MutableList<Short>) {
        val components = vertexString.split("/")

        val index = components[0].toInt() - 1
        val vertex = vertices[index]

        val textureIndex = components[1].toInt() - 1
        val normalIndex = components[2].toInt() - 1

        if (vertex.isSet()) {
            dealWithAlreadyProcessedVertex(
                vertex,
                textureIndex,
                normalIndex,
                indices,
                vertices
            )
        }
        else {
            vertex.textureIndex = textureIndex
            vertex.normalIndex = normalIndex
            indices.add(index.toShort())
        }
    }

    private fun dealWithAlreadyProcessedVertex(previousVertex: Vertex, newTextureIndex: Int,
                                               newNormalIndex: Int, indices: MutableList<Short>, vertices: MutableList<Vertex>) {
        if (previousVertex.hasSameTextureAndNormal(newTextureIndex, newNormalIndex)) {
            indices.add(previousVertex.index)
        } else {
            val anotherVertex = previousVertex.duplicateVertex
            if (anotherVertex != null) {
                dealWithAlreadyProcessedVertex(
                    anotherVertex,
                    newTextureIndex,
                    newNormalIndex,
                    indices,
                    vertices
                )
            } else {
                val duplicateVertex =
                    Vertex(vertices.size.toShort(), previousVertex.position)
                duplicateVertex.textureIndex = newTextureIndex
                duplicateVertex.normalIndex = newNormalIndex
                previousVertex.duplicateVertex = duplicateVertex
                vertices.add(duplicateVertex)
                indices.add(duplicateVertex.index)
            }

        }
    }

    private fun convertDataToArrays(vertices: List<Vertex>, textures: List<Vector2f>,
                                    normals: List<Vector3f>, verticesArray: FloatArray, texturesArray: FloatArray,
                                    normalsArray: FloatArray): Float {
        var furthestPoint = 0f
        vertices.forEachIndexed { index, value ->
            if (value.length > furthestPoint) {
                furthestPoint = value.length
            }

            val textureCoord = textures[value.textureIndex]
            val normal = normals[value.normalIndex]

            verticesArray[index * 3] = value.position.x
            verticesArray[index * 3 + 1] = value.position.y
            verticesArray[index * 3 + 2] = value.position.z
            texturesArray[index * 2] = textureCoord.x
            texturesArray[index * 2 + 1] = 1 - textureCoord.y
            normalsArray[index * 3] = normal.x
            normalsArray[index * 3 + 1] = normal.y
            normalsArray[index * 3 + 2] = normal.z
        }
        return furthestPoint
    }

	private fun removeUnusedVertices(vertices: List<Vertex>) {
        vertices.forEach { vertex ->
            if(!vertex.isSet()){
                vertex.textureIndex = 0
                vertex.normalIndex = 0
            }
        }
	}

	private fun convertIndicesListToArray(indices: List<Short>): ShortArray {
        val array = ShortArray(indices.size)
        indices.forEachIndexed { index, value -> array[index] = value }
        return array
	}

}
