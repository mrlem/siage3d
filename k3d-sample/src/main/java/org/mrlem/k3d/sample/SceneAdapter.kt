package org.mrlem.k3d.sample

import android.content.res.Resources
import org.joml.Vector3f
import org.mrlem.k3d.R
import org.mrlem.k3d.core.common.io.TextureCache
import org.mrlem.k3d.core.scene.ObjectNode
import org.mrlem.k3d.core.scene.Scene
import org.mrlem.k3d.core.scene.materials.TextureMaterial
import org.mrlem.k3d.core.scene.shapes.Box
import kotlin.math.sin

// TODO - medium - camera fps controller
// TODO - medium - skybox
// TODO - minor - shader should be attached to material
// TODO - optional - kotlin dsl for scene / subgraph init

class SceneAdapter(
    private val resources: Resources
) : org.mrlem.k3d.core.view.SceneAdapter() {

    override var scene = Scene()

    private var time = 0f

    override fun onInit() {
        // TODO - major - fails with obj loader shapes: why?
        val cube = Box() //Shape(resources, R.raw.model_tree_lowpoly_mesh)
        val material = TextureMaterial(TextureCache.get(resources, R.raw.model_tree_lowpoly_texture))

        scene.apply {
            camera.position(Vector3f(0f, 0f, 1f))
            skyColor.set(.6f, .8f, 1f)
            clear()
            add(
                ObjectNode(cube, material),
                ObjectNode(cube, material).apply { position(Vector3f(1f, 1f, 0f)) },
                ObjectNode(cube, material).apply { position(Vector3f(1f, -1f, 0f)) },
                ObjectNode(cube, material).apply { position(Vector3f(-1f, -1f, 0f)) },
                ObjectNode(cube, material).apply { position(Vector3f(-1f, 1f, 0f)) }
            )
        }
    }

    override fun onUpdate(delta: Float) {
        time += delta

        // animate camera
        val value = sin(time * 2) * 0.5f + .5f
        scene.camera.pitch = value * 5
        scene.camera.position.z = value * 10 + 1f

        // animate the scene
        scene.rotation(Vector3f(0f, value * 6, 0f))
        scene.children[1].rotation(Vector3f(0f, 0f, value))
        scene.children[2].rotation(Vector3f(0f, 0f, value))
        scene.children[3].rotation(Vector3f(0f, 0f, value))
        scene.children[4].rotation(Vector3f(0f, 0f, value))
    }

}
