package org.mrlem.siage3d.core.scene

import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.lights.PointLight
import org.mrlem.siage3d.core.scene.sky.Sky

class Scene : GroupNode("Scene") {

    var camera: Camera = Camera()
    var lights: List<PointLight> = mutableListOf()
    var sky: Sky = Sky.SkyColor(Vector3f(0f, 0f, 0f))

}
