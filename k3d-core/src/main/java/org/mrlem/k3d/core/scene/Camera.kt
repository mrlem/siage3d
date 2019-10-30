package org.mrlem.k3d.core.scene

import android.opengl.GLES30
import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.k3d.core.scene.shaders.Shader
import org.mrlem.k3d.core.common.math.fromCamera

class Camera(
    val position: Vector3f = Vector3f(0f, 0f, 0f),
    var yaw: Float = 0f,
    var pitch: Float = 0f,
    var roll: Float = 0f
) {

    private val near = 0.1f
    private val far = 1000f
    private val fov = 70.0

    private val projectionMatrix = Matrix4f()
    private var aspectRatio = 1f

    private val viewMatrix = Matrix4f()

    fun update(width: Int, height: Int) {
        aspectRatio = width.toFloat() / height
        GLES30.glViewport(0, 0, width, height)

        projectionMatrix.perspective(Math.toRadians(fov).toFloat(), aspectRatio, near, far)
        Shader.defaultShader.loadProjectionMatrix(projectionMatrix)
    }

    fun use() {
        viewMatrix.fromCamera(this)
        Shader.defaultShader.loadViewMatrix(viewMatrix)
    }

    fun position(position: Vector3f): Camera {
        position.set(position)
        return this
    }

    fun lookAt(target: Vector3f): Camera {
        // TODO - minor - set yaw / pitch / roll
        return this
    }

}
