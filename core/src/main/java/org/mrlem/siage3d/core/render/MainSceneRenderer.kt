package org.mrlem.siage3d.core.render

import org.mrlem.siage3d.core.scene.graph.Scene
import org.mrlem.siage3d.core.scene.graph.nodes.ObjectNode
import org.mrlem.siage3d.core.scene.graph.nodes.lights.DirectionLightNode
import org.mrlem.siage3d.core.scene.graph.nodes.lights.PointLightNode
import org.mrlem.siage3d.core.scene.graph.nodes.skies.SkyNode
import org.mrlem.siage3d.core.scene.graph.resources.materials.Material
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader

class MainSceneRenderer(scene: Scene) : SceneRenderer(scene) {

    override fun render() {
        // apply camera
        scene.camera.use()

        // draw scene

        // .. lights
        val lights = scene.lights
        lights
            .filterIsInstance<PointLightNode>()
            .forEachIndexed { index, light -> Shader.notifyPointLight(light, index) }
        lights
            .filterIsInstance<DirectionLightNode>()
            .firstOrNull()
            ?.let { Shader.notifyDirectionLight(it) }

        // .. fog
        Shader.notifyFog(scene.sky.color, 3f, 0.007f)

        // .. objects
        Material.activeMaterial = null
        gatherObjects(scene)
            .also { objects ->
                objects
                    .sortWith(
                        compareBy<ObjectNode> { it !is SkyNode }    // render sky first
                            .thenBy { it.material?.shader }         // then objects sorted by shader & material
                            .thenBy { it.material }
                    )

                objects.forEach {
                    it.material?.use()
                    it.render()
                }
            }
    }

}
