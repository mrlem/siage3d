package org.mrlem.siage3d.core.scene.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.siage3d.core.scene.lights.PointLight

class DefaultShader(
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

    fun loadTransformationMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.TRANSFORMATION_MATRIX, matrix)
    }

    fun loadLight(light: PointLight) {
        loadVector(Uniform.LIGHT_POSITION, light.position)
        loadVector(Uniform.LIGHT_COLOR, light.color)
    }

    fun loadShine(shineDamper: Float, reflectivity: Float) {
        loadFloat(Uniform.SHINE_DAMPER, shineDamper)
        loadFloat(Uniform.REFLECTIVITY, reflectivity)
    }

    fun loadFakeLighting(useFakeLighting: Boolean) {
        loadFloat(Uniform.USE_FAKE_LIGHTING, if (useFakeLighting) 1f else 0f)
    }

    fun loadSkyColor(skyColor: Vector3f) {
        loadVector(Uniform.SKY_COLOR, skyColor)
    }

    enum class Attribute(
        override val id: String,
        override val index: Int
    ) : AttributeDefinition {

        POSITIONS("position", 0),
        TEXCOORDS("textureCoords", 1),
        NORMALS("normal", 2)

    }

    enum class Uniform(
        override val id: String,
        override var location: Int = 0
    ) : UniformDefinition {

        PROJECTION_MATRIX("projectionMatrix"),
        VIEW_MATRIX("viewMatrix"),
        TRANSFORMATION_MATRIX("transformationMatrix"),
        LIGHT_POSITION("lightPosition"),
        LIGHT_COLOR("lightColor"),
        SHINE_DAMPER("shineDamper"),
        REFLECTIVITY("reflectivity"),
        USE_FAKE_LIGHTING("useFakeLighting"),
        SKY_COLOR("skyColor")

    }

}
