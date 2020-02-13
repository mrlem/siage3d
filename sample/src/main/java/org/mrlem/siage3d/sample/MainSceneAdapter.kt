package org.mrlem.siage3d.sample

import org.mrlem.siage3d.R
import org.mrlem.siage3d.core.common.io.AssetManager.shape
import org.mrlem.siage3d.core.common.math.randomFloat
import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.scene.Node
import org.mrlem.siage3d.core.scene.dsl.*
import org.mrlem.siage3d.core.scene.lights.PointLight
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
    lateinit var light0: PointLight
    lateinit var light1: PointLight

    private var time = 0f

    // TODO - colors from resources

    override fun onInit() = scene {
        light(
            name = "light0",
            ambient = color(0f, 0f, 0f),
            diffuse = color(1f, 1f, .8f)
        )
            .translate(0f, 25f, 0f)
            .also { this@MainSceneAdapter.light0 = it }
        light(
            name = "light1",
            ambient = color(.2f, .2f, .2f),
            diffuse = color(1f, 1f, .8f)
        )
            .translate(0f, 25f, 0f)
            .also { this@MainSceneAdapter.light1 = it }

        camera(
            name = "camera"
        )
            .translate(0f, 1.75f, 5f)

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

            for (i in 0 ..200) {
                val x = randomFloat() * 150f - 75f
                val z = randomFloat() * 150f - 75f
                objectNode(
                    "tree",
                    shape = shape(R.raw.model_tree_lowpoly_mesh),
                    material = textureMaterial(R.raw.model_tree_lowpoly_texture)
                )
                    .translate(x, terrain.heightAt(x, z), z)
                    .rotate(0f, (randomFloat() * 2 * PI).toFloat(), 0f)
                    .scale(.1f)
            }

            for (i in 0 .. 200) {
                val x = randomFloat() * 150f - 75f
                val z = randomFloat() * 150f - 75f
                objectNode(
                    "crate$i",
                    shape = box(),
                    material = textureMaterial(R.raw.crate1_diffuse, 0.5f)
                )
                    .translate(x, terrain.heightAt(x, z) + 0.5f, z)
                    .rotate(0f, (randomFloat() * 2 * PI).toFloat(), 0f)
                    .scale(1f)
            }

            objectNode(
                "lightcube0",
                shape = box(),
                material = textureMaterial(R.drawable.white)
            )
                .translate(light0.position())
                .scale(0.5f)
        }
    }

    override fun onUpdate(delta: Float) {
        time += delta

        // animate lights
        light0.position(sin(time) * 10f, light0.position().y, cos(time) * 10f)
        light1.position(5 + sin(time * 1.7f) * 14f, light0.position().y, sin(time * 1.7f) * 14f)

        // animate camera
        scene.camera.apply {
            yaw += angularVelocity * delta
            val position = position()
            position.x += sin(yaw.toRadians()) * linearVelocity * delta
            position.z -= cos(yaw.toRadians()) * linearVelocity * delta
            position.y = 40f
            position(position)
        }
    }

}
