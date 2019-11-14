package org.mrlem.siage3d.core.view

import org.mrlem.siage3d.core.render.SceneRenderer
import org.mrlem.siage3d.core.render.SortedMaterialsSceneRenderer
import org.mrlem.siage3d.core.scene.Scene

abstract class SceneAdapter {

    abstract val scene: Scene
    private lateinit var sceneRenderer: SceneRenderer

    internal fun init() {
        sceneRenderer = SortedMaterialsSceneRenderer(scene)
        onInit()
    }

    internal fun resize(width: Int, height: Int) {
        scene.camera.update(width, height)
        onResize(width, height)
    }

    internal fun update(delta: Float) {
        onUpdate(delta)
        scene.update()
        sceneRenderer.render()
    }

    internal fun destroy() {
        onDestroy()
    }

    open fun onInit() {}
    open fun onResize(width: Int, height: Int) {}
    open fun onUpdate(delta: Float) {}
    open fun onDestroy() {}

}