package org.mrlem.siage3d.core.render

import org.mrlem.siage3d.core.scene.ObjectNode
import org.mrlem.siage3d.core.scene.Scene
import org.mrlem.siage3d.core.scene.shaders.Shader

class MainSceneRenderer(scene: Scene) : SceneRenderer(scene) {

    override fun render() {
        // apply camera
        scene.camera.use()

        // render sky
        scene.sky.render()

        // draw scene
        scene.lights.forEachIndexed { index, light ->
            Shader.notifyLight(light, index)
        }
        Shader.notifyFog(scene.sky.color, 3f, 0.007f)

        val objects = gatherObjects(scene, mutableListOf())
        objects.sortWith(compareBy<ObjectNode> { it.material.shader }.thenBy { it.material })

        objects.forEach {
            it.material.use()
            it.render()
        }
    }

}
