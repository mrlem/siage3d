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
class Scene(name: String?) {

    val root = GroupNode(name)
    val materials: MutableList<Material> = mutableListOf()

    private val _states: MutableList<State> = mutableListOf()
    val states: List<State> get() = _states

    /**
     * Add a state to the scene.
     *
     * @param state state to add.
     */
    fun add(state: State) {
        _states
            .takeUnless { _states.any { it::class.java == state::class.java } }
            ?.add(state)
    }

    /**
     * Remove a state from the scene.
     *
     * @param state state to remove.
     */
    fun remove(state: State) {
        _states.remove(state)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Node accessors, for convenience.
    ///////////////////////////////////////////////////////////////////////////

    val camera: CameraNode get() = root.children
        .filterIsInstance(CameraNode::class.java)
        .firstOrNull()
        ?: CameraNode("camera")
    val sky: SkyNode get() = root.children
        .filterIsInstance(SkyNode::class.java)
        .firstOrNull()
        ?: ColorSkyNode()
    val lights get() = root.children
        .filterIsInstance(LightNode::class.java)

}
