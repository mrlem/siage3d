package org.mrlem.siage3d.core.scene.graph.nodes.skies

import android.opengl.GLES30.*
import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader

class ColorSkyNode(
    color: Vector3f = Vector3f(.6f, .8f, 1f),
    name: String? = null
) : SkyNode(null, null, color, name ?: "sky color") {

    override fun render(shader: Shader) {
        glClearColor(color.x, color.y, color.z, 1f)
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

}
