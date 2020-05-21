package org.mrlem.siage3d.core.view

import org.mrlem.siage3d.core.render.MainSceneRenderer
import org.mrlem.siage3d.core.render.ShadowSceneRenderer
import org.mrlem.siage3d.core.scene.graph.Scene
import org.mrlem.siage3d.core.world.World

/**
 * Scene adapter: this is where we describe what we want to display, and how it changes through time.
 */
abstract class SceneAdapter<W : World>(
    protected val scene: Scene,
    protected val world: W
) {

    private val renderers = listOf(
        ShadowSceneRenderer(scene),
        MainSceneRenderer(scene)
    )

    internal fun resize(width: Int, height: Int) {
        scene.camera.update(width, height)
        onResize(width, height)
    }

    internal fun update(delta: Float) {
        world.update(delta)
        onSceneUpdate()
        scene.applyTransforms()
        renderers.forEach { it.render() }
    }

    open fun onResize(width: Int, height: Int) {}
    open fun onSceneUpdate() {}

}
