package org.mrlem.siage3d.core.scene.graph.nodes.lights

import org.joml.Vector3f

class PointLightNode(
    name: String? = null,
    ambient: Vector3f = Vector3f(0f),
    diffuse: Vector3f = Vector3f(1f, 1f, 1f),
    var constant: Float = 1f,
    var linear: Float = 0.09f,
    var quadratic: Float = 0.032f
) : LightNode(name ?: "point-light", ambient, diffuse)
