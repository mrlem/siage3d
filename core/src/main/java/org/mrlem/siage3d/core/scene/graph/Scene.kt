package org.mrlem.siage3d.core.scene.graph

import org.mrlem.siage3d.core.scene.graph.resources.materials.Material
import org.mrlem.siage3d.core.scene.graph.nodes.cameras.CameraNode
import org.mrlem.siage3d.core.scene.graph.nodes.GroupNode
import org.mrlem.siage3d.core.scene.graph.nodes.Node
import org.mrlem.siage3d.core.scene.graph.nodes.lights.LightNode
import org.mrlem.siage3d.core.scene.graph.nodes.skies.ColorSkyNode
import org.mrlem.siage3d.core.scene.graph.nodes.skies.SkyNode
import org.mrlem.siage3d.core.state.State

/**
 * The 3D scene to be rendered on screen. It consists in a graph of interconnected nodes describing the objects to draw
 * and their positions. The Scene is effectively this graph's root node.
 *
 * @param name see [Node.name].
 */
class Scene(name: String?) : GroupNode(name) {

    val materials: MutableList<Material> = mutableListOf()

    val states: MutableList<State> = mutableListOf()

    ///////////////////////////////////////////////////////////////////////////
    // Node accessors, for convenience.
    ///////////////////////////////////////////////////////////////////////////

    val camera: CameraNode get() = children.filterIsInstance(CameraNode::class.java).firstOrNull() ?: CameraNode("camera")
    val sky: SkyNode get() = children.filterIsInstance(SkyNode::class.java).firstOrNull() ?: ColorSkyNode()
    val lights get() = children.filterIsInstance(LightNode::class.java)

}
