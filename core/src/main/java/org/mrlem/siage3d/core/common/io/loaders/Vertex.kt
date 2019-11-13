package org.mrlem.siage3d.core.common.io.loaders

import org.joml.Vector3f

internal class Vertex(val index: Short, val position: Vector3f) {

    companion object {
        val NO_INDEX = -1
    }

	val length: Float = position.length()
	var textureIndex = NO_INDEX
	var normalIndex = NO_INDEX
	var duplicateVertex: Vertex? = null

	fun isSet() = textureIndex != NO_INDEX && normalIndex != NO_INDEX
	fun hasSameTextureAndNormal(textureIndexOther: Int, normalIndexOther: Int) = textureIndexOther == textureIndex && normalIndexOther == normalIndex

}
