package org.mrlem.siage3d.core.scene

import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.lights.LightNode
import org.mrlem.siage3d.core.scene.sky.Sky

class Scene : GroupNode("Scene") {

    var camera: Camera = Camera("camera")
    var lights: MutableList<LightNode> = mutableListOf()
    var sky: Sky = Sky.SkyColor(Vector3f(0f, 0f, 0f))

}
