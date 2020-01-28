package org.mrlem.siage3d.core.scene.shaders

import org.joml.Matrix4f

class SkyboxShader(
    vertexShaderCode: String,
    fragmentShaderCode: String
) : Shader(
    vertexShaderCode, fragmentShaderCode,
    Attribute.values().asList(), Uniform.values().asList()
) {

    fun loadProjectionMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.PROJECTION_MATRIX, matrix)
    }

    fun loadViewMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.VIEW_MATRIX, matrix)
    }

    enum class Attribute(
        override val id: String,
        override val index: Int
    ) : AttributeDefinition {

        POSITIONS("position", 0)

    }

    enum class Uniform(
        override val id: String,
        override var location: Int = 0
    ) : UniformDefinition {

        PROJECTION_MATRIX("projectionMatrix"),
        VIEW_MATRIX("viewMatrix")

    }

}
