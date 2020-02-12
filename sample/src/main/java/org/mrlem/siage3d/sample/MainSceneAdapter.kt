package org.mrlem.siage3d.sample

import org.mrlem.siage3d.R
import org.mrlem.siage3d.core.common.io.AssetManager.shape
import org.mrlem.siage3d.core.common.math.randomFloat
import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.scene.Node
import org.mrlem.siage3d.core.scene.dsl.*
import org.mrlem.siage3d.core.scene.shapes.Terrain
import org.mrlem.siage3d.core.view.SceneAdapter
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

class MainSceneAdapter : SceneAdapter() {

    var linearVelocity = 0f
    var angularVelocity = 0f

    lateinit var groundNode: Node
    lateinit var terrain: Terrain

    override fun onInit() = scene {
        light {
            position(0f, 25f, 0f)
            ambient(0.2f, 0.2f, 0.2f)
            diffuse(1f, 1f, .8f)
        }
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
                shape = terrain(500f, heightMap(R.raw.heightmap), 50f).also { terrain = it },
                material = multiTextureMaterial(
                    R.drawable.texture_blend_map,
                    R.drawable.texture_grassy2,
                    R.drawable.texture_mud,
                    R.drawable.texture_grass_flowers,
                    R.drawable.texture_path,
                    0.02f
                )
            )
                .also { groundNode = it }

            ///////////////////////////////////////////////////////////////////////////
            // Objects
            ///////////////////////////////////////////////////////////////////////////

            for (i in 0 .. 200) {
                objectNode(
                    "tree1",
                    shape = shape(R.raw.model_tree_lowpoly_mesh),
                    material = textureMaterial(R.raw.model_tree_lowpoly_texture)
                )
                    .apply {
                        val x = randomFloat() * 150f - 75f
                        val z = randomFloat() * 150f - 75f
                        position(x, terrain.heightAt(x, z), z)
                    }
                    .rotate(0f, (randomFloat() * 2 * PI).toFloat(), 0f)
                    .scale(.1f)
            }
            for (i in 0 .. 200) {
                objectNode(
                    "crate1",
                    shape = box(),
                    material = textureMaterial(R.raw.crate1_diffuse, 0.5f)
                )
                    .apply {
                        val x = randomFloat() * 150f - 75f
                        val z = randomFloat() * 150f - 75f
                        position(x, terrain.heightAt(x, z) + 0.5f, z)
                    }
                    .rotate(0f, (randomFloat() * 2 * PI).toFloat(), 0f)
                    .scale(1f)
            }
            objectNode(
                "crate2",
                shape = box(),
                material = textureMaterial(R.drawable.texture_mud)
            )
                .position(-2f, 0.5f, 0f)
                .scale(1f)
            objectNode(
                "lightcube",
                shape = box(),
                material = textureMaterial(R.drawable.white)
            )
                .position(0f, 25f, 0f)
                .scale(0.5f)
                .also { println(terrain.heightAt(0f, 0f)) }
        }
    }

    override fun onUpdate(delta: Float) {
        // animate camera
        scene.camera.apply {
            yaw += angularVelocity * delta
            position.x += sin(yaw.toRadians()) * linearVelocity * delta
            position.z -= cos(yaw.toRadians()) * linearVelocity * delta
            position.y = 40f
        }
    }

}
