package org.mrlem.siage3d.sample

import org.mrlem.siage3d.core.common.io.AssetManager
import org.mrlem.siage3d.core.common.math.randomFloat
import org.mrlem.siage3d.core.scene.dsl.SceneBuilder
import org.mrlem.siage3d.core.scene.dsl.scene
import org.mrlem.siage3d.core.scene.graph.nodes.terrains.TerrainNode
import org.mrlem.siage3d.core.scene.graph.resources.shapes.BoxShape

///////////////////////////////////////////////////////////////////////////
// The scene
///////////////////////////////////////////////////////////////////////////

val advancedScene = scene {
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

///////////////////////////////////////////////////////////////////////////
// Internal
///////////////////////////////////////////////////////////////////////////

private var terrain: TerrainNode? = null

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
        objectNode("tree", AssetManager.shape(R.raw.model_tree_lowpoly_mesh)) {
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
