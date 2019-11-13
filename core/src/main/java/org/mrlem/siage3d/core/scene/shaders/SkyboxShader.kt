package org.mrlem.siage3d.core.scene.shaders

import org.joml.Matrix4f

class SkyboxShader(
    vertexShaderCode: String,
    fragmentShaderCode: String
) : Shader(vertexShaderCode, fragmentShaderCode) {

    private var locationProjectionMatrix: Int = 0
    private var locationViewMatrix: Int = 0

    override fun bindAttributes() {
        bindAttribute(DefaultShader.ATTR_POSITIONS, "position")
    }

    override fun getAllUniformLocations() {
        locationProjectionMatrix = getUniformLocation("projectionMatrix")
        locationViewMatrix = getUniformLocation("viewMatrix")
    }

    fun loadProjectionMatrix(matrix: Matrix4f) {
        loadMatrix(locationProjectionMatrix, matrix)
    }

    fun loadViewMatrix(matrix: Matrix4f) {
        loadMatrix(locationViewMatrix, matrix)
    }

}
