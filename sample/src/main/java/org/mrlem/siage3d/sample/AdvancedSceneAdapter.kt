package org.mrlem.siage3d.sample

import org.mrlem.siage3d.core.common.io.AssetManager.shape
import org.mrlem.siage3d.core.common.math.randomFloat
import org.mrlem.siage3d.core.common.math.toRadians
import org.mrlem.siage3d.core.scene.ObjectNode
import org.mrlem.siage3d.core.scene.TerrainNode
import org.mrlem.siage3d.core.scene.dsl.*
import org.mrlem.siage3d.core.scene.lights.PointLight
import org.mrlem.siage3d.core.scene.position
import org.mrlem.siage3d.core.scene.shapes.Box
import org.mrlem.siage3d.core.view.SceneAdapter
import kotlin.math.cos
import kotlin.math.sin

/**
 * This is a slightly more advanced sample, with terrain, skybox & multiple animated light sources & camera control.
 */
class AdvancedSceneAdapter : SceneAdapter() {

    // scene objects refs
    private val light0 by lazy { scene.get<PointLight>("light0")!! }
    private val light1 by lazy { scene.get<PointLight>("light1")!! }
    private val lightCube0 by lazy { scene.get<ObjectNode>("light-cube0")!! }
    private val lightCube1 by lazy { scene.get<ObjectNode>("light-cube1")!! }

    private var terrain: TerrainNode? = null

    // time-bound variables
    private var time = 0f
    var linearVelocity = 0f
    var angularVelocity = 0f

    // TODO - minor - colors from resources

    override fun onCreateScene() = scene {
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

        // ground
        terrainNode("ground", R.drawable.heightmap, 0.1f) {
            multiTextureMaterial(
                R.drawable.texture_blend_map,
                R.drawable.texture_grassy2,
                R.drawable.texture_mud,
                R.drawable.texture_grass_flowers,
                R.drawable.texture_path,
                0.02f,
                reflectivity = .3f
            )
            scale(500f)
        }
        terrain = lastNode as TerrainNode

        createObjects()
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

    private fun SceneBuilder.createObjects() {
        // objects: trees
        for (i in 0..100) {
            val x = randomFloat() * 150f - 75f
            val z = randomFloat() * 150f - 75f
            objectNode("tree", shape(R.raw.model_tree_lowpoly_mesh)) {
                textureMaterial(R.drawable.model_tree_lowpoly_texture, reflectivity = 0.1f)
                position(x, terrain?.heightAt(x, z) ?: 0f, z)
                rotation(randomFloat() * 360, 0f, 0f)
                scale(.1f)
            }
        }

        // objects: crates
        for (i in 0..100) {
            val x = randomFloat() * 150f - 75f
            val z = randomFloat() * 150f - 75f
            objectNode("crate$i", Box()) {
                textureMaterial(R.drawable.crate1_diffuse, 1f, reflectivity = 0.1f)
                position(x, 0.5f + (terrain?.heightAt(x, z) ?: 0f), z)
                rotation(randomFloat() * 360, 0f, 0f)
            }
        }

        // objects: light cubes
        objectNode("light-cube0", Box()) {
            textureMaterial(R.drawable.white, ambient = 100f)
            scale(0.5f)
        }
        objectNode("light-cube1", Box()) {
            textureMaterial(R.drawable.white, ambient = 100f)
            scale(0.5f)
        }
    }
}
