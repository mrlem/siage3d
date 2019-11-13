package org.mrlem.siage3d.core.scene.shapes

data class Mesh(
    val positions: FloatArray,
    val texCoords: FloatArray,
    val indices: ShortArray,
    val normals: FloatArray
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Mesh

        if (!positions.contentEquals(other.positions)) return false
        if (!texCoords.contentEquals(other.texCoords)) return false
        if (!indices.contentEquals(other.indices)) return false
        if (!normals.contentEquals(other.normals)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = positions.contentHashCode()
        result = 31 * result + texCoords.contentHashCode()
        result = 31 * result + indices.contentHashCode()
        result = 31 * result + normals.contentHashCode()
        return result
    }

}
