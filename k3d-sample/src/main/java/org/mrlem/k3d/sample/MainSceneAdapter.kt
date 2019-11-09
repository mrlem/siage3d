package org.mrlem.k3d.sample

import android.content.res.Resources
import org.joml.Vector3f
import org.mrlem.k3d.R
import org.mrlem.k3d.core.common.io.TextureCache
import org.mrlem.k3d.core.scene.GroupNode
import org.mrlem.k3d.core.scene.ObjectNode
import org.mrlem.k3d.core.scene.Scene
import org.mrlem.k3d.core.scene.materials.TextureMaterial
import org.mrlem.k3d.core.scene.shapes.Box
import org.mrlem.k3d.core.scene.shapes.Shape
import org.mrlem.k3d.core.view.SceneAdapter
import kotlin.math.sin

// TODO - medium - skybox
// TODO - minor - shader should be attached to material
// TODO - optional - kotlin dsl for scene / subgraph init

class MainSceneAdapter(
    private val resources: Resources
) : SceneAdapter() {

    override var scene = Scene()
    var motion = Vector3f()

    private var time = 0f

    override fun onInit() {
        val tree = Shape(resources, R.raw.model_tree_lowpoly_mesh)
        val treeMaterial = TextureMaterial(TextureCache.get(resources, R.raw.model_tree_lowpoly_texture))

        val ground = Box()
        val groundMaterial = TextureMaterial(TextureCache.get(resources, R.raw.crate1_diffuse))

        scene.apply {
            camera.position(Vector3f(0f, 6f, 5f))
            skyColor.set(.6f, .8f, 1f)
            localTransform.setTranslation(0f, 0f, -30f)
            clear()
            add(
                GroupNode().apply {
/*                    add(
                        ObjectNode(tree, treeMaterial),
                        ObjectNode(ground, groundMaterial).apply {
                            localTransform.scale(30f)
                            localTransform.setTranslation(Vector3f(0f, -15f, 0f))
                        }
                    )*/
                }
            )
        }

        (scene.children.first() as GroupNode).apply {
            (-10 .. 10).forEach { x ->
                (-10 .. 10).forEach { y ->
                    (-0 .. 0).forEach { z ->
                        add(ObjectNode(tree, treeMaterial).apply {
                            val spacing = 1.4f
                            localTransform.setTranslation(Vector3f(x * spacing, y * spacing, z * spacing))
                            localTransform.scale(0.05f)
                        })
                    }
                }
            }
        }
    }

    override fun onUpdate(delta: Float) {
        time += delta

        // animate camera
        scene.camera.position.add(motion.x * delta, motion.y * delta, motion.z * delta)

        // animate the scene
        val value = sin(time) * 0.5f + .5f
        scene.children.first().localTransform
            .setRotationXYZ(0f, value * 6f, 0f)
    }

}
