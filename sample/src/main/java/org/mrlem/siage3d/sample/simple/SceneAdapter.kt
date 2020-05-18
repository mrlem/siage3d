package org.mrlem.siage3d.sample.simple

import org.mrlem.siage3d.core.scene.graph.Scene
import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.view.SceneAdapter

/**
 * This is a simple sample: just a rotating cube in front of us.
 */
class SceneAdapter(scene: Scene) : SceneAdapter<World>(scene, world) {

    private val cube by lazy{ scene.get<ObjectNode>("my-cube")!! }

    override fun onSceneUpdate() {
        cube.rotate(0f, world.time * 50f, 0f)
    }

}
