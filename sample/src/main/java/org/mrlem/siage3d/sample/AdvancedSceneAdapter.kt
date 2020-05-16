package org.mrlem.siage3d.sample

import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.scene.graph.nodes.lights.PointLightNode
import org.mrlem.siage3d.core.view.SceneAdapter
import kotlin.math.cos
import kotlin.math.sin

/**
 * This is a slightly more advanced sample, with terrain, skybox & multiple animated light sources & camera control.
 */
class AdvancedSceneAdapter : SceneAdapter() {

    // scene objects refs
    private val light0 get() = scene.get<PointLightNode>("light0")!!
    private val light1 get() = scene.get<PointLightNode>("light1")!!
    private val lightCube0 get() = scene.get<ObjectNode>("light-cube0")!!
    private val lightCube1 get() = scene.get<ObjectNode>("light-cube1")!!

    // time-bound variables
    private var time = 0f
    var linearVelocity = 0f
    var angularVelocity = 0f

    override fun onSceneCreate() = advancedScene

    override fun onUpdate(delta: Float) {
        time += delta

        // animate lights
        light0
            .apply { position(sin(time) * 10f, position().y, cos(time) * 10f) }
            .also { lightCube0.position(it.position()) }
        light1
            .apply { position(5 + sin(time * 1.7f) * 14f, position().y, cos(time * 1.7f) * 14f) }
            .also { lightCube1.position(it.position()) }

        // animate camera
        scene.camera.apply {
            yaw += angularVelocity * delta
            position(position()
                .apply { x += sin(yaw.toRadians()) * linearVelocity * delta }
                .apply { z -= cos(yaw.toRadians()) * linearVelocity * delta }
                .apply { y = 40f }
            )
        }
    }
}
