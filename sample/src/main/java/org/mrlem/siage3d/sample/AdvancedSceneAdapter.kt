package org.mrlem.siage3d.sample

import org.mrlem.siage3d.core.common.io.AssetManager.shape
import org.mrlem.siage3d.core.common.math.randomFloat
import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.scene.graph.nodes.terrains.TerrainNode
import org.mrlem.siage3d.core.scene.dsl.*
import org.mrlem.siage3d.core.scene.graph.nodes.lights.PointLightNode
import org.mrlem.siage3d.core.scene.graph.resources.shapes.BoxShape
import org.mrlem.siage3d.core.view.SceneAdapter
import kotlin.math.cos
import kotlin.math.sin

/**
 * This is a slightly more advanced sample, with terrain, skybox & multiple animated light sources & camera control.
 */
class AdvancedSceneAdapter : SceneAdapter() {

    // scene objects refs
    private val light0 get() = scene.get<PointLightNode>("light0")!!
    private val light1 get() = scene.get<PointLightNode>("light1")!!
    private val lightCube0 get() = scene.get<ObjectNode>("light-cube0")!!
    private val lightCube1 get() = scene.get<ObjectNode>("light-cube1")!!

    private var terrain: TerrainNode? = null

    // time-bound variables
    private var time = 0f
    var linearVelocity = 0f
    var angularVelocity = 0f

    // TODO - minor - colors from resources

    override fun onSceneCreate() = scene {
        camera {
            position(0f, 40f, 5f)
        }

        sky {
            color(.6f, .8f, 1f)
            cubemap(R.array.skybox_daylight)
        }

        pointLight("light0") {
            ambient(0f, 0f, 0f)
            diffuse(1f, 1f, .8f)
            position(0f, 25f, 0f)
        }

        pointLight("light1") {
            diffuse(1f, 1f, .8f)
            position(0f, 25f, 0f)
        }

        directionLight("sun") {
            ambient(.1f, .1f, .1f)
            diffuse(1f, 1f, 1f)
            rotation(180f, 75f, 0f)
        }

        createGround().also { terrain = it }
        createTrees()
        createCrates()
        createLightCubes()
    }

    override fun onUpdate(delta: Float) {
        time += delta

        // animate lights
        light0
            .apply { position(sin(time) * 10f, position().y, cos(time) * 10f) }
            .also { lightCube0.position(it.position()) }
        light1
            .apply { position(5 + sin(time * 1.7f) * 14f, position().y, cos(time * 1.7f) * 14f) }
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

    private fun SceneBuilder.createGround(): TerrainNode {
        return terrainNode("ground", R.drawable.heightmap, 0.1f) {
            material {
                textureMap(
                    R.drawable.texture_blend_map,
                    R.drawable.texture_grassy2,
                    R.drawable.texture_mud,
                    R.drawable.texture_grass_flowers,
                    R.drawable.texture_path
                )
                scale(0.02f)
            }
            scale(500f)
        }
    }

    private fun SceneBuilder.createTrees() {
        material("tree") {
            texture(R.drawable.model_tree_lowpoly_texture)
        }

        for (i in 0..200) {
            val x = randomFloat() * 150f - 75f
            val z = randomFloat() * 150f - 75f
            objectNode("tree", shape(R.raw.model_tree_lowpoly_mesh)) {
                material("tree")
                position(x, terrain?.heightAt(x, z) ?: 0f, z)
                rotation(randomFloat() * 360, 0f, 0f)
                scale(.1f)
            }
        }
    }

    private fun SceneBuilder.createCrates() {
        material("crate") {
            texture(R.drawable.crate1_diffuse)
            reflectivity(0.1f)
        }

        for (i in 0..200) {
            val x = randomFloat() * 150f - 75f
            val z = randomFloat() * 150f - 75f
            objectNode("crate$i", BoxShape()) {
                material("crate")
                position(x, 0.5f + (terrain?.heightAt(x, z) ?: 0f), z)
                rotation(randomFloat() * 360, 0f, 0f)
            }
        }
    }

    private fun SceneBuilder.createLightCubes() {
        material("white") {
            texture(R.drawable.white)
            ambient(100f)
        }

        objectNode("light-cube0", BoxShape()) {
            material("white")
            scale(0.5f)
        }

        objectNode("light-cube1", BoxShape()) {
            material("white")
            scale(0.5f)
        }
    }
}
