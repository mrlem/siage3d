package org.mrlem.k3d.core.scene.sky

import android.opengl.GLES30.*
import org.joml.Vector3f
import org.mrlem.k3d.core.common.gl.CubemapTexture

sealed class Sky(
    val color: Vector3f
) {

    abstract fun render()

    class SkyColor(color: Vector3f): Sky(color) {

        override fun render() {
            glClearColor(color.x, color.y, color.z, 1f)
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
        }

    }

    class SkyBox(
        texture: CubemapTexture,
        color: Vector3f
    ) : Sky(color) {

        override fun render() {
            // TODO
        }

    }

}
