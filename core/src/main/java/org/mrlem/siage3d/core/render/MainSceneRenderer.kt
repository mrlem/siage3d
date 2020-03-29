package org.mrlem.siage3d.core.render

import org.mrlem.siage3d.core.scene.ObjectNode
import org.mrlem.siage3d.core.scene.Scene
import org.mrlem.siage3d.core.scene.lights.DirectionLight
import org.mrlem.siage3d.core.scene.lights.PointLight
import org.mrlem.siage3d.core.scene.materials.Material
import org.mrlem.siage3d.core.scene.shaders.Shader

class MainSceneRenderer(scene: Scene) : SceneRenderer(scene) {

    override fun render() {
        // apply camera
        scene.camera.use()

        // render sky
        scene.sky.render()

        // draw scene
        prepareDraw()

        // .. lights
        scene.lights
            .filterIsInstance<PointLight>()
            .forEachIndexed { index, light -> Shader.notifyPointLight(light, index) }
        scene.lights
            .filterIsInstance<DirectionLight>()
            .firstOrNull()
            ?.let { Shader.notifyDirectionLight(it) }

        // .. fog
        Shader.notifyFog(scene.sky.color, 3f, 0.007f)

        // .. objects
        gatherObjects(scene, mutableListOf()).also { objects ->
            objects
                .sortWith(compareBy<ObjectNode> { it.material.shader }.thenBy { it.material })
            objects.forEach {
                it.material.use()
                it.render()
            }
        }
    }

    private fun prepareDraw() {
        Material.activeMaterial = null
    }

}
