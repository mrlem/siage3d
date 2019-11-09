package org.mrlem.k3d.core.scene

import org.joml.Vector3f
import org.mrlem.k3d.core.scene.lights.PointLight

class Scene : GroupNode("Scene") {

    val skyColor: Vector3f = Vector3f(0f, 0f, 0f)
    val light: PointLight = PointLight(Vector3f(10f, 10f, 10f), Vector3f(1f, 1f, .8f))
    val camera: Camera = Camera()

}
