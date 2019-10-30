package org.mrlem.k3d.sample

import org.mrlem.k3d.core.K3DActivity

class MainActivity : K3DActivity() {

    override val sceneAdapter by lazy { SceneAdapter(resources) }

}
