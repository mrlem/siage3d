package org.mrlem.siage3d.core.scene.lights

import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.Node

class PointLight(
    name: String,
    val ambient: Vector3f = Vector3f(0f),
    val diffuse: Vector3f = Vector3f(1f, 1f, 1f)
) : Node(name)
