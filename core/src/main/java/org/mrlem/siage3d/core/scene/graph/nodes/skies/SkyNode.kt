package org.mrlem.siage3d.core.scene.graph.nodes.skies

import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.scene.graph.resources.materials.Material
import org.mrlem.siage3d.core.scene.graph.resources.shapes.Shape

open class SkyNode(
    shape: Shape?,
    material: Material?,
    val color: Vector3f,
    name: String? = null
) : ObjectNode(shape, material, name)
