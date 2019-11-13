package org.mrlem.siage3d.core.render

import org.mrlem.siage3d.core.scene.Scene

abstract class SceneRenderer(protected val scene: Scene) {

    abstract fun render()

}