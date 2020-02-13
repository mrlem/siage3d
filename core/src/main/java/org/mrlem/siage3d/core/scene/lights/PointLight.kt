package org.mrlem.siage3d.core.scene.lights

import org.joml.Vector3f

class PointLight(
    val position: Vector3f = Vector3f(0f, 1f, 0f),
    val ambient: Vector3f = Vector3f(0f),
    val diffuse: Vector3f = Vector3f(1f, 1f, 1f)
)
