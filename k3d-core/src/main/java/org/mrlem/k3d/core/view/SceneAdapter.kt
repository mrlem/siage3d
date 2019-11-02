package org.mrlem.k3d.core.view

import org.mrlem.k3d.core.scene.Scene
import org.mrlem.k3d.core.scene.shaders.Shader

abstract class SceneAdapter {

    abstract val scene: Scene

    internal fun init() {
        onInit()
    }

    internal fun resize(width: Int, height: Int) {
        Shader.defaultShader.use {
            scene.camera.update(width, height)
        }
        onResize(width, height)
    }

    internal fun update(delta: Float) {
        onUpdate(delta)
        scene.update()
        scene.render()
    }

    internal fun destroy() {
        onDestroy()
    }

    open fun onInit() {}
    open fun onResize(width: Int, height: Int) {}
    open fun onUpdate(delta: Float) {}
    open fun onDestroy() {}

}
