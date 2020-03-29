package org.mrlem.siage3d.core.view

import org.mrlem.siage3d.core.render.MainSceneRenderer
import org.mrlem.siage3d.core.render.ShadowSceneRenderer
import org.mrlem.siage3d.core.scene.Scene
import org.mrlem.siage3d.core.scene.dsl.SceneBuilder

/**
 * Scene adapter: this is where we describe what we want to display, and how it changes through time.
 */
abstract class SceneAdapter {

    private val renderers by lazy {
        listOf(
            ShadowSceneRenderer(scene),
            MainSceneRenderer(scene)
        )
    }

    lateinit var scene: Scene
        private set

    internal fun init() {
        scene = onCreateScene().build()
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

    abstract fun onCreateScene(): SceneBuilder
    open fun onResize(width: Int, height: Int) {}
    open fun onUpdate(delta: Float) {}

}
