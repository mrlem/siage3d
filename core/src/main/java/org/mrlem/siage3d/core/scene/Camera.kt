package org.mrlem.siage3d.core.scene

import org.joml.Matrix4f
import org.mrlem.siage3d.core.scene.shaders.Shader
import org.mrlem.siage3d.core.common.math.fromCamera

class Camera(
    name: String,
    // TODO - handle those using node rotation
    var yaw: Float = 0f,
    var pitch: Float = 0f,
    var roll: Float = 0f
) : Node(name) {

    val near = 0.1f
    val far = 300f
    val fov = 45.0

    private val projectionMatrix = Matrix4f()
    private var aspectRatio = 1f

    private val viewMatrix = Matrix4f()

    fun update(width: Int, height: Int) {
        aspectRatio = width.toFloat() / height

        projectionMatrix.setPerspective(Math.toRadians(fov).toFloat(), aspectRatio, near, far)
        Shader.notifyProjectionMatrix(projectionMatrix)
    }

    fun use() {
        viewMatrix.fromCamera(this)
        Shader.notifyViewMatrix(viewMatrix)
    }

}
