package org.mrlem.siage3d.core.scene

import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.lights.LightNode
import org.mrlem.siage3d.core.scene.sky.Sky

class Scene(name: String?) : GroupNode(name) {

    var camera: Camera = Camera("camera")
    var lights: MutableList<LightNode> = mutableListOf()
    var sky: Sky = Sky.SkyColor(Vector3f(0f, 0f, 0f))

    @Suppress("UNCHECKED_CAST")
    override fun <T> get(name: String): T? {
        if (camera.name == name) return camera as T
        lights.firstOrNull { it.name == name }?.let { return it as T }
        return super.get(name)
    }

}
