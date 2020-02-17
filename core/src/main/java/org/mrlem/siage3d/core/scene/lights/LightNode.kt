package org.mrlem.siage3d.core.scene.lights

import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.Node

abstract class LightNode(
    name: String,
    val ambient: Vector3f,
    val diffuse: Vector3f
) : Node(name)
