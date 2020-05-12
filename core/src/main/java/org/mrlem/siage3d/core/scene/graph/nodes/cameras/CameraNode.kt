package org.mrlem.siage3d.core.scene.graph.nodes.cameras

import android.opengl.GLES30.*
import org.joml.Matrix4f
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader
import org.mrlem.siage3d.core.common.math.fromCamera
import org.mrlem.siage3d.core.scene.graph.nodes.SpatialNode

class CameraNode(
    name: String? = null,
    // TODO - handle those using node rotation
    var yaw: Float = 0f,
    var pitch: Float = 0f,
    var roll: Float = 0f
) : SpatialNode(name ?: "camera") {

    private var width: Int = 0
    private var height: Int = 0
    val near = 0.1f
    val far = 300f
    val fov = 45.0

    private val projectionMatrix = Matrix4f()
    var aspectRatio = 1f

    private val viewMatrix = Matrix4f()

    fun update(width: Int, height: Int) {
        this.width = width
        this.height = height

        aspectRatio = width.toFloat() / height
        projectionMatrix.setPerspective(Math.toRadians(fov).toFloat(), aspectRatio, near, far)
    }

    fun use() {
        glViewport(0, 0, width, height)
        Shader.notifyProjectionMatrix(projectionMatrix)

        viewMatrix.fromCamera(this) // TODO - minor - only if dirty
        Shader.notifyViewMatrix(viewMatrix)
    }

}
