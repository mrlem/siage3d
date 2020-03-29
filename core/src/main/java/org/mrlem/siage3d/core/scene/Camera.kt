package org.mrlem.siage3d.core.scene

import android.opengl.GLES30.*
import org.joml.Matrix4f
import org.mrlem.siage3d.core.scene.shaders.Shader
import org.mrlem.siage3d.core.common.math.fromCamera

class Camera(
    name: String? = null,
    // TODO - handle those using node rotation
    var yaw: Float = 0f,
    var pitch: Float = 0f,
    var roll: Float = 0f
) : Node(name ?: "camera") {

    private val near = 0.1f
    private val far = 300f
    private val fov = 45.0

    private val projectionMatrix = Matrix4f()
    private var aspectRatio = 1f

    private val viewMatrix = Matrix4f()

    fun update(width: Int, height: Int) {
        glViewport(0, 0, width, height)

        aspectRatio = width.toFloat() / height
        projectionMatrix.setPerspective(Math.toRadians(fov).toFloat(), aspectRatio, near, far)
        Shader.notifyProjectionMatrix(projectionMatrix)
    }

    fun use() {
        viewMatrix.fromCamera(this)
        Shader.notifyViewMatrix(viewMatrix)
    }

}
