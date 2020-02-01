package org.mrlem.siage3d.sample

import org.mrlem.siage3d.R
import org.mrlem.siage3d.core.common.io.AssetManager.shape
import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.scene.dsl.*
import org.mrlem.siage3d.core.view.SceneAdapter
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// TODO - critical - mipmap for terrain textures
// TODO - major - load assets from assets (not resources, but keep this option)
// TODO - major - camera controller to provide logic
// TODO - major - postprocessing: bloom
// TODO - major - postprocessing: anti-aliasing
// TODO - major - postprocessing: kernel convolution matrix
// TODO - major - ambient occlusion
// TODO - medium - shadows
// TODO - medium - normal maps / specular maps
// TODO - medium - bone animation
// TODO - medium - physics engine integration
// TODO - medium - SceneView transparency
// TODO - medium - improve sample app
// TODO - medium - blender models loader
// TODO - optional - instanced rendering when > 40 instances

class MainSceneAdapter : SceneAdapter() {

    var linearVelocity = 0f
    var angularVelocity = 0f

    override fun onInit() = scene {
        camera {
            position(0f, 1.75f, 5f)
        }
        sky(
            color = color(.6f, .8f, 1f),
            cubemap = R.array.skybox_daylight
        )
        groupNode {
            objectNode(
                "tree1",
                shape = shape(R.raw.model_tree_lowpoly_mesh),
                material = textureMaterial(R.raw.model_tree_lowpoly_texture)
            )
                .position(-1f, 0f, 0f)
                .scale(.1f)
            objectNode(
                "crate1",
                shape = box(),
                material = textureMaterial(R.raw.crate1_diffuse)
            )
                .position(1f, 0.5f, 0f)
                .scale(1f)
            objectNode(
                "crate2",
                shape = box(),
                material = textureMaterial(R.drawable.texture_mud)
            )
                .position(-2f, 0.5f, 0f)
                .scale(1f)
            objectNode(
                "ground",
                shape = square(),
                material = multiTextureMaterial(
                    R.drawable.texture_blend_map,
                    R.drawable.texture_grassy2,
                    R.drawable.texture_mud,
                    R.drawable.texture_grass_flowers,
                    R.drawable.texture_path
                )
            )
                .rotate(-PI.toFloat() / 2, 0f, 0f)
                .scale(100f)
        }
    }

    override fun onUpdate(delta: Float) {
        // animate camera
        scene.camera.apply {
            yaw += angularVelocity * delta
            position.x += sin(yaw.toRadians()) * linearVelocity * delta
            position.z -= cos(yaw.toRadians()) * linearVelocity * delta
        }
    }

}
