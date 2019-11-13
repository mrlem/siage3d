package org.mrlem.k3d.sample

import android.content.res.Resources
import org.joml.Vector3f
import org.mrlem.k3d.R
import org.mrlem.k3d.core.common.io.readTexture2D
import org.mrlem.k3d.core.common.io.readTextureCubemap
import org.mrlem.k3d.core.common.math.toRadians
import org.mrlem.k3d.core.scene.GroupNode
import org.mrlem.k3d.core.scene.ObjectNode
import org.mrlem.k3d.core.scene.Scene
import org.mrlem.k3d.core.scene.materials.TextureMaterial
import org.mrlem.k3d.core.scene.shapes.Box
import org.mrlem.k3d.core.scene.shapes.Shape
import org.mrlem.k3d.core.scene.sky.Sky
import org.mrlem.k3d.core.view.SceneAdapter
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// TODO - critical - rename engine & add to github
// TODO - critical - camera controller to provide logic
// TODO - major - postprocessing: bloom
// TODO - major - postprocessing: anti-aliasing
// TODO - major - postprocessing: kernel convolution matrix
// TODO - medium - shadows
// TODO - medium - normal maps / specular maps
// TODO - medium - bone animation
// TODO - medium - physics engine integration
// TODO - medium - SceneView transparency
// TODO - medium - improve sample app
// TODO - medium - blender models loader
// TODO - major - multi-texturing
// TODO - major - instanced rendering when > 40 instances
// TODO - minor - shader should be attached to material
// TODO - optional - kotlin dsl for scene / subgraph init

class MainSceneAdapter(
    private val resources: Resources
) : SceneAdapter() {

    override var scene = Scene()

    var linearVelocity = 0f
    var angularVelocity = 0f

    private lateinit var rootNode: GroupNode
    private var time = 0f

    override fun onInit() {
        val tree = Shape(resources, R.raw.model_tree_lowpoly_mesh)
        val treeMaterial = TextureMaterial(resources.readTexture2D(R.raw.model_tree_lowpoly_texture))

        val box = Box()
        val crateMaterial = TextureMaterial(resources.readTexture2D(R.raw.crate1_diffuse))

        // create scene
        scene.apply {
            camera.position(Vector3f(0f, 1.75f, 5f))
            sky = Sky.Skybox(resources.readTextureCubemap(R.array.skybox_daylight), Vector3f(.6f, .8f, 1f))
            clear()
            add(GroupNode().also { rootNode = it })
        }

        // populate scene
        // .. random objects: forest of trees & crates
        val angle360: Float = PI.toFloat() * 2
        val spacing = 4f
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
        scene.camera.apply {
            yaw += angularVelocity * delta
            position.x += sin(yaw.toRadians()) * linearVelocity * delta
            position.z -= cos(yaw.toRadians()) * linearVelocity * delta
        }

        // animate the scene
//        val value = sin(time) * 0.5f + .5f
//        rootNode.localTransform.setRotationXYZ(0f, value, 0f)
    }

}
