package org.mrlem.k3d.sample

import android.content.res.Resources
import org.joml.Vector3f
import org.mrlem.k3d.R
import org.mrlem.k3d.core.common.io.TextureCache
import org.mrlem.k3d.core.scene.ObjectNode
import org.mrlem.k3d.core.scene.Scene
import org.mrlem.k3d.core.scene.materials.TextureMaterial
import org.mrlem.k3d.core.scene.shapes.Triangle
import org.mrlem.k3d.core.view.K3DView
import kotlin.math.sin

// TODO - critical - nothing rendered :p

class SceneAdapter(
    private val resources: Resources
) : K3DView.Adapter() {

    override var scene = Scene()

    private lateinit var triangle: ObjectNode
    private var time = 0f

    override fun onInit() {
        triangle = ObjectNode(
            Triangle(),
            TextureMaterial(TextureCache.get(resources, R.drawable.white))
        )
        scene.apply {
            camera
                .position(Vector3f(0f, 0f, -5f))
                .lookAt(Vector3f(0f, 0f, 0f))
                .pitch = 90f
            skyColor.set(0f, 0f, 0f)
            add(triangle)
        }
    }

    override fun onUpdate(delta: Float) {
        time += delta

        // animate camera
        scene.camera.yaw += 1f

        // animate background
        val value = sin(time) * .5f + .5f
        scene.skyColor.set(value, value, value)

        // animate node
        triangle.position(Vector3f(value, value, value))
    }

}
