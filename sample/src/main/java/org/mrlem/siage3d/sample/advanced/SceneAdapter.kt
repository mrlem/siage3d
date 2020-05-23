package org.mrlem.siage3d.sample.advanced

import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.graph.Scene
import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.scene.graph.nodes.lights.PointLightNode
import org.mrlem.siage3d.core.view.SceneAdapter
import org.mrlem.siage3d.sample.advanced.behaviours.CircleFlyBehaviour
import org.mrlem.siage3d.sample.advanced.behaviours.PuppetBehaviour

/**
 * This is a slightly more advanced sample, with terrain, skybox & multiple animated light sources & camera control.
 */
class SceneAdapter(scene: Scene) : SceneAdapter(scene) {

    // scene objects to manipulate
    private val light0 = scene.get<PointLightNode>("light0")!!
    private val light1 = scene.get<PointLightNode>("light1")!!
    private val lightCube0 = scene.get<ObjectNode>("light-cube0")!!
    private val lightCube1 = scene.get<ObjectNode>("light-cube1")!!

    init {
        scene.add(state)

        CircleFlyBehaviour(state, Vector3f(5f, 25f, 0f), 14f, 1.7f).let { fastCircling ->
            light0.add(fastCircling)
            lightCube0.add(fastCircling.copy())
        }

        CircleFlyBehaviour(state, Vector3f(5f, 25f, 0f), 10f, 1f).let { slowCircling ->
            light1.add(slowCircling)
            lightCube1.add(slowCircling.copy())
        }

        scene.camera.add(PuppetBehaviour(state, 40f, 20f))
    }

}
