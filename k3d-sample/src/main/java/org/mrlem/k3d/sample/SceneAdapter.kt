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

// TODO - critical - rendering works only after rotating the screen
// TODO - major - texture not rendered

// TODO - optional - kotlin dsl for scene / subgraph init

class SceneAdapter(
    private val resources: Resources
) : org.mrlem.k3d.core.view.SceneAdapter() {

    override var scene = Scene()

    private var time = 0f

    override fun onInit() {
        val square = Square()
        val material = TextureMaterial(TextureCache.get(resources, R.drawable.white))

        scene.apply {
            camera
                .position(Vector3f(0f, 0f, 0f))
//                .pitch = 90f
            skyColor.set(.6f, .8f, 1f)
            add(
                ObjectNode(square, material),
                ObjectNode(square, material).apply { position(Vector3f(1f, 1f, 0f)) },
                ObjectNode(square, material).apply { position(Vector3f(1f, -1f, 0f)) },
                ObjectNode(square, material).apply { position(Vector3f(-1f, -1f, 0f)) },
                ObjectNode(square, material).apply { position(Vector3f(-1f, 1f, 0f)) }
            )
        }
    }

    override fun onUpdate(delta: Float) {
        time += delta
        val value = sin(time / 2) * 0.5f + .5f

        // animate camera
        scene.camera.position.z = value * 3f + 1f
    }

}
