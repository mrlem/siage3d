package org.mrlem.siage3d.core.scene.graph.nodes.lights

import org.joml.Vector3f
import org.mrlem.siage3d.core.common.gl.DepthMap

class DirectionLightNode(
    name: String? = null,
    ambient: Vector3f = Vector3f(0f),
    diffuse: Vector3f = Vector3f(1f, 1f, 1f)
) : LightNode(name ?: "direction-light", ambient, diffuse) {

    val shadowMap = DepthMap(1024)

}
