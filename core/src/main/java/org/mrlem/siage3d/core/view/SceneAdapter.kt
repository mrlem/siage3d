package org.mrlem.siage3d.core.view

import org.mrlem.siage3d.core.render.MainSceneRenderer
import org.mrlem.siage3d.core.render.ShadowSceneRenderer
import org.mrlem.siage3d.core.scene.Scene

abstract class SceneAdapter {

    lateinit var scene: Scene
    private val renderers by lazy {
        listOf(
            ShadowSceneRenderer(scene),
            MainSceneRenderer(scene)
        )
    }

    internal fun init() {
        scene = onInit()
    }

    internal fun resize(width: Int, height: Int) {
        scene.camera.update(width, height)
        onResize(width, height)
    }

    internal fun update(delta: Float) {
        onUpdate(delta)
        scene.update()
        renderers.forEach { it.render() }
    }

    internal fun destroy() {
        onDestroy()
    }

    open fun onInit(): Scene = Scene()
    open fun onResize(width: Int, height: Int) {}
    open fun onUpdate(delta: Float) {}
    open fun onDestroy() {}

}
