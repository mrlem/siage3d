package org.mrlem.k3d.sample

import android.content.res.Resources
import org.joml.Vector3f
import org.mrlem.k3d.R
import org.mrlem.k3d.core.common.io.TextureCache
import org.mrlem.k3d.core.scene.ObjectNode
import org.mrlem.k3d.core.scene.Scene
import org.mrlem.k3d.core.scene.materials.TextureMaterial
import org.mrlem.k3d.core.scene.shapes.Square
import kotlin.math.sin

// TODO - critical - nothing rendered :p
// TODO - optional - kotlin dsl for scene / subgraph init

class SceneAdapter(
    private val resources: Resources
) : org.mrlem.k3d.core.view.SceneAdapter() {

    override var scene = Scene()

    private lateinit var square: ObjectNode
    private var time = 0f

    override fun onInit() {
        square = ObjectNode(
            Square(),
            TextureMaterial(TextureCache.get(resources, R.drawable.white))
        )
        scene.apply {
            camera
                .position(Vector3f(0f, 0f, 0f))
                .lookAt(Vector3f(0f, 0f, 0f))
                .pitch = 90f
            skyColor.set(.6f, .8f, 1f)
            add(square)
        }
    }

    override fun onUpdate(delta: Float) {
        time += delta

        // animate camera
        scene.camera.yaw += .1f

        val value = sin(time) * .5f + .5f

        // animate node
        square.position(Vector3f(0f, 0f, value))
    }

}
