package org.mrlem.k3d.sample

import org.mrlem.k3d.core.SceneActivity

class MainActivity : SceneActivity() {

    override val sceneAdapter by lazy { SceneAdapter(resources) }

}
