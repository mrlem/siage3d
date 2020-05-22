package org.mrlem.siage3d.sample.advanced

import org.mrlem.siage3d.core.scene.graph.Scene
import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.scene.graph.nodes.lights.PointLightNode
import org.mrlem.siage3d.core.view.SceneAdapter

/**
 * This is a slightly more advanced sample, with terrain, skybox & multiple animated light sources & camera control.
 */
class SceneAdapter(scene: Scene) : SceneAdapter(scene) {

    // scene objects to manipulate
    private val light0 by lazy { scene.get<PointLightNode>("light0")!! }
    private val light1 by lazy { scene.get<PointLightNode>("light1")!! }
    private val lightCube0 by lazy { scene.get<ObjectNode>("light-cube0")!! }
    private val lightCube1 by lazy { scene.get<ObjectNode>("light-cube1")!! }

    init {
        scene.states += state
    }

    override fun onSceneUpdate() {
        // animate lights
        light0.position(state.spot1Position)
        lightCube0.position(state.spot1Position)

        light1.position(state.spot2Position)
        lightCube1.position(state.spot2Position)

        // animate camera
        scene.camera.apply {
            yaw = state.orientation
            position(state.position)
        }
    }

}
