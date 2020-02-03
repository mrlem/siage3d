package org.mrlem.siage3d.sample

import org.mrlem.siage3d.R
import org.mrlem.siage3d.core.common.io.AssetManager.shape
import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.scene.Node
import org.mrlem.siage3d.core.scene.dsl.*
import org.mrlem.siage3d.core.scene.shapes.Terrain
import org.mrlem.siage3d.core.view.SceneAdapter
import kotlin.math.cos
import kotlin.math.sin

class MainSceneAdapter : SceneAdapter() {

    var linearVelocity = 0f
    var angularVelocity = 0f

    lateinit var groundNode: Node
    lateinit var heightMap: Terrain.HeightMap

    override fun onInit() = scene {
        camera {
            position(0f, 1.75f, 5f)
        }
        sky(
            color = color(.6f, .8f, 1f),
            cubemap = R.array.skybox_daylight
        )
        groupNode {

            ///////////////////////////////////////////////////////////////////////////
            // Ground
            ///////////////////////////////////////////////////////////////////////////

            objectNode(
                "ground",
                shape = terrain(1f, heightMap(R.raw.heightmap).also { heightMap = it }),
                material = multiTextureMaterial(
                    R.drawable.texture_blend_map,
                    R.drawable.texture_grassy2,
                    R.drawable.texture_mud,
                    R.drawable.texture_grass_flowers,
                    R.drawable.texture_path,
                    200f
                )
            )
                .position(-250f, -10f, -250f)
                .scale(500f, 20f, 500f)
                .also { groundNode = it }

            ///////////////////////////////////////////////////////////////////////////
            // Objects
            ///////////////////////////////////////////////////////////////////////////

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
                material = textureMaterial(R.raw.crate1_diffuse, 2f)
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
