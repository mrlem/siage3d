package org.mrlem.siage3d.core.scene.graph.nodes.lights

import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.graph.nodes.SpatialNode

abstract class LightNode(
    name: String,
    val ambient: Vector3f,
    val diffuse: Vector3f
) : SpatialNode(name)
