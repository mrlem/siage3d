package org.mrlem.siage3d.sample.advanced

import org.mrlem.siage3d.core.SceneActivity

/**
 * Game entry-point:
 *
 * - sets the scene & scene adapter
 * - sets the gesture listener to interact with the scene
 */
class Game : SceneActivity() {

    override val sceneGestureListener = GestureListener()

    override fun createSceneAdapter() = SceneAdapter(initialScene)

}
