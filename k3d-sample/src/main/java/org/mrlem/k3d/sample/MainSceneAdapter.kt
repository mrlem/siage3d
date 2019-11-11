package org.mrlem.k3d.sample

import android.content.res.Resources
import org.joml.Vector3f
import org.mrlem.k3d.R
import org.mrlem.k3d.core.common.io.readTexture2D
import org.mrlem.k3d.core.scene.GroupNode
import org.mrlem.k3d.core.scene.ObjectNode
import org.mrlem.k3d.core.scene.Scene
import org.mrlem.k3d.core.scene.materials.TextureMaterial
import org.mrlem.k3d.core.scene.shapes.Box
import org.mrlem.k3d.core.scene.shapes.Shape
import org.mrlem.k3d.core.scene.sky.Sky
import org.mrlem.k3d.core.view.SceneAdapter
import kotlin.math.PI

// TODO - medium - skybox
// TODO - minor - shader should be attached to material
// TODO - minor - where is the light defined?
// TODO - optional - kotlin dsl for scene / subgraph init

class MainSceneAdapter(
    private val resources: Resources
) : SceneAdapter() {

    override var scene = Scene()

    var motion = Vector3f()

    private lateinit var rootNode: GroupNode
    private var time = 0f

    override fun onInit() {
        val tree = Shape(resources, R.raw.model_tree_lowpoly_mesh)
        val treeMaterial = TextureMaterial(resources.readTexture2D(R.raw.model_tree_lowpoly_texture))

        val box = Box()
        val crateMaterial = TextureMaterial(resources.readTexture2D(R.raw.crate1_diffuse))
        val whiteMaterial = TextureMaterial(resources.readTexture2D(R.drawable.white))

        // create scene
        scene.apply {
            camera.position(Vector3f(0f, 1.75f, 5f))
            sky = Sky.SkyColor(Vector3f(.6f, .8f, 1f))
            clear()
            add(GroupNode().also { rootNode = it })
        }

        // populate scene
        // .. ground
        rootNode.add(ObjectNode(box, whiteMaterial).apply {
            localTransform
                .setTranslation(0f, -100f, 0f)
                .scale(200f)
        })
        // .. random objects: forest of trees & crates
        val angle360: Float = PI.toFloat() * 2
        val spacing = 3f
        rootNode.localTransform.setTranslation(0f, 0f, -10f)
        (-10 .. 10).forEach { x ->
            (-10 .. 10).forEach { z ->
                (-0 .. 0).forEach { y ->
                    // randomized grid position
                    val randomizedPosition = Vector3f(
                        x * spacing + (Math.random().toFloat() * spacing / 2 - spacing / 4),
                        y * spacing,
                        z * spacing + (Math.random().toFloat() * spacing / 2 - spacing / 4)
                    )

                    // tree or crate? + custom transformations
                    val typeRoll = Math.random()
                    val node = when {
                        // crate
                        typeRoll > 0.8f -> ObjectNode(box, crateMaterial).apply {
                            localTransform
                                .setTranslation(randomizedPosition.x, randomizedPosition.y + 0.5f, randomizedPosition.z)
                                .rotateAffineXYZ(
                                    0f,
                                    Math.random().toFloat() * angle360,
                                    0f
                                )
                                .scale(1f)
                        }
                        // tree
                        else -> ObjectNode(tree, treeMaterial).apply {
                            localTransform
                                .setTranslation(randomizedPosition.x, randomizedPosition.y, randomizedPosition.z)
                                .rotateAffineXYZ(
                                    Math.random().toFloat() * angle360 / 8 - angle360 / 16,
                                    Math.random().toFloat() * angle360,
                                    0f
                                )
                                .scale(0.12f + Math.random().toFloat() * 0.08f)
                        }
                    }

                    // add to scene
                    rootNode.add(node)
                }
            }
        }
    }

    override fun onUpdate(delta: Float) {
        time += delta

        // animate camera
        scene.camera.position.add(motion.x * delta, motion.y * delta, motion.z * delta)

        // animate the scene
//        val value = sin(time) * 0.5f + .5f
//        rootNode.localTransform.setRotationXYZ(0f, value, 0f)
    }

}
