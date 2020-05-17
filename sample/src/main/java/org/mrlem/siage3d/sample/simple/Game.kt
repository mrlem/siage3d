package org.mrlem.siage3d.sample.simple

import org.mrlem.siage3d.core.SceneActivity

class Game : SceneActivity<SceneAdapter>() {

    override fun createSceneAdapter() = SceneAdapter(initialScene)

}
