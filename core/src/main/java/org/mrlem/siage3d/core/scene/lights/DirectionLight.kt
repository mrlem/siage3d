package org.mrlem.siage3d.core.scene.lights

import org.joml.Vector3f

class DirectionLight(
    name: String? = null,
    ambient: Vector3f = Vector3f(0f),
    diffuse: Vector3f = Vector3f(1f, 1f, 1f)
) : LightNode(name ?: "direction-light", ambient, diffuse)
