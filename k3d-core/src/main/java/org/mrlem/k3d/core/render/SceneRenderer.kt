package org.mrlem.k3d.core.render

import org.mrlem.k3d.core.scene.Scene

abstract class SceneRenderer(protected val scene: Scene) {

    abstract fun render()

}