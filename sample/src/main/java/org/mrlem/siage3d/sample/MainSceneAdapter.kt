package org.mrlem.siage3d.sample

import org.mrlem.siage3d.R
import org.mrlem.siage3d.core.common.io.AssetManager.shape
import org.mrlem.siage3d.core.common.math.randomFloat
import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.scene.Node
import org.mrlem.siage3d.core.scene.ObjectNode
import org.mrlem.siage3d.core.scene.Scene
import org.mrlem.siage3d.core.scene.dsl.*
import org.mrlem.siage3d.core.scene.lights.PointLight
import org.mrlem.siage3d.core.scene.shapes.Terrain
import org.mrlem.siage3d.core.view.SceneAdapter
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Scene adapter: this is where we describe what we want to display, and how it changes through
 * time.
 */
class MainSceneAdapter : SceneAdapter() {

    var linearVelocity = 0f
    var angularVelocity = 0f

    lateinit var groundNode: Node
    lateinit var terrain: Terrain

    lateinit var light0: PointLight
    lateinit var light1: PointLight
    lateinit var lightCube0: ObjectNode
    lateinit var lightCube1: ObjectNode

    private var time = 0f

    // TODO - colors from resources

    override fun onInit() = scene {
        camera(
            name = "camera"
        )
            .translate(0f, 1.75f, 5f)

        sky(
            color = color(.6f, .8f, 1f),
            cubemap = R.array.skybox_daylight
        )

        pointLight(
            name = "light0",
            ambient = color(0f, 0f, 0f),
            diffuse = color(1f, 1f, .8f)
        )
            .translate(0f, 25f, 0f)
            .also { this@MainSceneAdapter.light0 = it }

        pointLight(
            name = "light1",
            ambient = color(0f, 0f, 0f),
            diffuse = color(1f, 1f, .8f)
        )
            .translate(0f, 25f, 0f)
            .also { this@MainSceneAdapter.light1 = it }

        directionLight(
            name = "Sun",
            ambient = color(.3f, .3f, .3f),
            diffuse = color(1f, 1f, 1f)
        )
            .rotate(45f, 0f, 0f)

        createSceneGraph()
    }

    override fun onUpdate(delta: Float) {
        time += delta

        // animate lights
        light0
            .position(sin(time) * 10f, light0.position().y, cos(time) * 10f)
            .also { lightCube0.position(it.position()) }
        light1
            .position(5 + sin(time * 1.7f) * 14f, light0.position().y, cos(time * 1.7f) * 14f)
            .also { lightCube1.position(it.position()) }

        // animate camera
        scene.camera.apply {
            yaw += angularVelocity * delta
            position(position()
                .apply { x += sin(yaw.toRadians()) * linearVelocity * delta }
                .apply { z -= cos(yaw.toRadians()) * linearVelocity * delta }
                .apply { y = 40f }
            )
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun Scene.createSceneGraph() {
        groupNode {
            // ground

            objectNode(
                "ground",
                shape = terrain(500f, heightMap(R.raw.heightmap), 50f).also { terrain = it },
                material = multiTextureMaterial(
                    R.drawable.texture_blend_map,
                    R.drawable.texture_grassy2,
                    R.drawable.texture_mud,
                    R.drawable.texture_grass_flowers,
                    R.drawable.texture_path,
                    0.02f,
                    reflectivity = .3f
                )
            )
                .also { groundNode = it }

            // objects

            for (i in 0 ..100) {
                val x = randomFloat() * 150f - 75f
                val z = randomFloat() * 150f - 75f
                objectNode(
                    "tree",
                    shape = shape(R.raw.model_tree_lowpoly_mesh),
                    material = textureMaterial(R.raw.model_tree_lowpoly_texture, reflectivity = 0.1f)
                )
                    .translate(x, terrain.heightAt(x, z), z)
                    .rotate(0f, (randomFloat() * 2 * PI).toFloat(), 0f)
                    .scale(.1f)
            }

            for (i in 0 .. 100) {
                val x = randomFloat() * 150f - 75f
                val z = randomFloat() * 150f - 75f
                objectNode(
                    "crate$i",
                    shape = box(),
                    material = textureMaterial(R.raw.crate1_diffuse, 0.5f, reflectivity = 0.4f)
                )
                    .translate(x, terrain.heightAt(x, z) + 0.5f, z)
                    .rotate(0f, (randomFloat() * 2 * PI).toFloat(), 0f)
                    .scale(1f)
            }

            objectNode(
                "lightcube0",
                shape = box(),
                material = textureMaterial(R.drawable.white, ambient = 100f)
            )
                .translate(light0.position())
                .scale(0.5f)
                .also { lightCube0 = it }

            objectNode(
                "lightcube1",
                shape = box(),
                material = textureMaterial(R.drawable.white, ambient = 100f)
            )
                .translate(light0.position())
                .scale(0.5f)
                .also { lightCube1 = it }
        }
    }

}
