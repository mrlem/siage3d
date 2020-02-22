package org.mrlem.siage3d.core.scene.lights

import org.joml.Vector3f

class PointLight(
    name: String,
    ambient: Vector3f = Vector3f(0f),
    diffuse: Vector3f = Vector3f(1f, 1f, 1f),
    val constant: Float = 1f,
    val linear: Float = 0.09f,
    val quadratic: Float = 0.032f
) : LightNode(name, ambient, diffuse)
