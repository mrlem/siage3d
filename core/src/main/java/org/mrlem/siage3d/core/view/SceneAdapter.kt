package org.mrlem.siage3d.core.view

import org.mrlem.siage3d.core.render.MainSceneRenderer
import org.mrlem.siage3d.core.render.ShadowSceneRenderer
import org.mrlem.siage3d.core.scene.Scene
import org.mrlem.siage3d.core.scene.dsl.SceneBuilder

/**
 * Scene adapter: this is where we describe what we want to display, and how it changes through time.
 */
abstract class SceneAdapter {

    var scene: Scene = Scene("default")
        private set(value) {
            field = value
            renderers.forEach { it.scene = scene }
        }

    private val renderers = listOf(
        ShadowSceneRenderer(scene),
        MainSceneRenderer(scene)
    )

    internal fun init() {
        scene = onSceneCreate().build()
        onSceneCreated()
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

    abstract fun onSceneCreate(): SceneBuilder
    open fun onSceneCreated() {}
    open fun onResize(width: Int, height: Int) {}
    open fun onUpdate(delta: Float) {}

}
