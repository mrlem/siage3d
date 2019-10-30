package org.mrlem.k3d.core.scene

import android.opengl.GLES30
import org.mrlem.k3d.core.common.gl.Color
import javax.microedition.khronos.opengles.GL10

class Scene : GroupNode("Scene") {

    val skyColor: Color =
        Color(0f, 0f, 0f)
    val camera: Camera = Camera()

    override fun render() {
        // draw sky
        skyColor.components.let { components ->
            GLES30.glClearColor(components[0], components[1], components[2], 1f)
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)
        }

        // apply camera
        camera.use()

        // draw scene
        super.render()
    }

}
