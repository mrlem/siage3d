package org.mrlem.k3d.core.scene.sky

import android.opengl.GLES30.*
import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.k3d.core.R
import org.mrlem.k3d.core.common.gl.TextureCubemap
import org.mrlem.k3d.core.scene.ObjectNode
import org.mrlem.k3d.core.scene.materials.TextureMaterial
import org.mrlem.k3d.core.scene.shaders.Shader
import org.mrlem.k3d.core.scene.shapes.Box

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

    class Skybox(
        private val texture: TextureCubemap,
        color: Vector3f
    ) : Sky(color) {

        private val box = Box()

        override fun render() {
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

            glDisable(GL_CULL_FACE)

            glDepthMask(false)
            Shader.skyboxShader.use {
                texture.use {
                    box.draw()
                }
            }

            glDepthMask(true)
        }

    }

}
