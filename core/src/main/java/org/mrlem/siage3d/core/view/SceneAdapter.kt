package org.mrlem.siage3d.core.view

import org.mrlem.siage3d.core.render.MainSceneRenderer
import org.mrlem.siage3d.core.render.ShadowSceneRenderer
import org.mrlem.siage3d.core.scene.graph.Scene

/**
 * Scene adapter: this is where we describe what we want to display, and how it changes through time.
 *
 * @property scene the scene to be rendered on the [SceneView].
 */
abstract class SceneAdapter(protected val scene: Scene) {

    private val renderers = listOf(
        ShadowSceneRenderer(scene),
        MainSceneRenderer(scene)
    )

    internal fun resize(width: Int, height: Int) {
        scene.camera.update(width, height)
        onResize(width, height)
    }

    internal fun update(delta: Float) {
        // update
        // .. states
        for (i in 0 until scene.states.size) {
            val state = scene.states[i]
            state.update(delta)
        }
        // .. behaviours
        scene.root.browse { node ->
            for (i in 0 until node.behaviours.size) {
                val behaviour = node.behaviours[i]
                behaviour.update(delta)
            }
        }
        // .. scene
        onSceneUpdate()

        // render nodes
        scene.root.applyTransforms()
        for (i in 0 until renderers.size) {
            val renderer = renderers[i]
            renderer.render()
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Public API
    ///////////////////////////////////////////////////////////////////////////

    open fun onResize(width: Int, height: Int) {}
    open fun onSceneUpdate() {}

}
