package org.mrlem.siage3d.core.scene

import org.joml.Matrix3f
import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.shaders.Shader
import org.mrlem.siage3d.core.common.math.fromCamera

class Camera(
    val position: Vector3f = Vector3f(0f, 0f, 0f),
    var yaw: Float = 0f,
    var pitch: Float = 0f,
    var roll: Float = 0f
) {

    val near = 0.1f
    val far = 300f
    val fov = 45.0

    private val projectionMatrix = Matrix4f()
    private var aspectRatio = 1f

    private val viewMatrix = Matrix4f()
    private val rotationOnlyMatrix = Matrix4f()
    private val rotationMatrix = Matrix3f()

    fun update(width: Int, height: Int) {
        aspectRatio = width.toFloat() / height

        projectionMatrix.setPerspective(Math.toRadians(fov).toFloat(), aspectRatio, near, far)
        Shader.defaultShader.use {
           Shader.defaultShader.loadProjectionMatrix(projectionMatrix)
        }
        Shader.skyboxShader.use {
            Shader.skyboxShader.loadProjectionMatrix(projectionMatrix)
        }
    }

    fun use() {
        viewMatrix.fromCamera(this)
        rotationMatrix.set(viewMatrix)
        rotationOnlyMatrix.set(rotationMatrix)
        Shader.defaultShader.use {
            Shader.defaultShader.loadViewMatrix(viewMatrix)
        }
        Shader.skyboxShader.use {
            Shader.skyboxShader.loadViewMatrix(rotationOnlyMatrix)
        }
    }

    fun position(position: Vector3f): Camera {
        this.position.set(position)
        return this
    }

    fun lookAt(target: Vector3f): Camera {
        // TODO - minor - set yaw / pitch / roll
        return this
    }

}
