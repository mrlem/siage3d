package org.mrlem.k3d.sample

import android.content.res.Resources
import org.joml.Vector3f
import org.mrlem.k3d.R
import org.mrlem.k3d.core.common.io.TextureCache
import org.mrlem.k3d.core.scene.ObjectNode
import org.mrlem.k3d.core.scene.Scene
import org.mrlem.k3d.core.scene.materials.TextureMaterial
import org.mrlem.k3d.core.scene.shapes.Box
import org.mrlem.k3d.core.scene.shapes.Shape
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
        val tree = Shape(resources, R.raw.model_tree_lowpoly_mesh)
        val treeMaterial = TextureMaterial(TextureCache.get(resources, R.raw.model_tree_lowpoly_texture))

        val ground = Box()
        val groundMaterial = TextureMaterial(TextureCache.get(resources, R.drawable.white))

        scene.apply {
            camera.position(Vector3f(0f, 6f, 5f))
            skyColor.set(.6f, .8f, 1f)
            clear()
            add(
                ObjectNode(tree, treeMaterial).apply { localTransform.setTranslation(0f, 0f, -30f) },
                ObjectNode(ground, groundMaterial).apply {
                    localTransform.scale(30f)
                    localTransform.setTranslation(Vector3f(0f, -15f, -30f))
                }
            )
        }
    }

    override fun onUpdate(delta: Float) {
        time += delta

        // animate camera
        val value = sin(time * 2) * 0.5f + .5f

        // animate the scene
        scene.children.first().localTransform.setRotationXYZ(0f, value * 6f, 0f)
    }

}
