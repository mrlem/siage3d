package org.mrlem.k3d.core.scene.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.k3d.core.scene.lights.PointLight

class DefaultShader(
    vertexShaderCode: String,
    fragmentShaderCode: String
) : Shader(vertexShaderCode, fragmentShaderCode) {

    private var locationProjectionMatrix: Int = 0
    private var locationViewMatrix: Int = 0
    private var locationTransformationMatrix: Int = 0
    private var locationLightPosition: Int = 0
    private var locationLightColor: Int = 0
    private var locationShineDamper: Int = 0
    private var locationReflectivity: Int = 0
    private var locationUseFakeLighting: Int = 0
    private var locationSkyColor: Int = 0

    override fun bindAttributes() {
        bindAttribute(ATTR_POSITIONS, "position")
        bindAttribute(ATTR_TEXCOORDS, "textureCoords")
        bindAttribute(ATTR_NORMALS, "normal")
    }

    override fun getAllUniformLocations() {
        locationProjectionMatrix = getUniformLocation("projectionMatrix")
        locationViewMatrix = getUniformLocation("viewMatrix")
        locationTransformationMatrix = getUniformLocation("transformationMatrix")
        locationLightPosition = getUniformLocation("lightPosition")
        locationLightColor = getUniformLocation("lightColor")
        locationShineDamper = getUniformLocation("shineDamper")
        locationReflectivity = getUniformLocation("reflectivity")
        locationUseFakeLighting = getUniformLocation("useFakeLighting")
        locationSkyColor = getUniformLocation("skyColor")
    }

    fun loadProjectionMatrix(matrix: Matrix4f) {
        loadMatrix(locationProjectionMatrix, matrix)
    }

    fun loadViewMatrix(matrix: Matrix4f) {
        loadMatrix(locationViewMatrix, matrix)
    }

    fun loadTransformationMatrix(matrix: Matrix4f) {
        loadMatrix(locationTransformationMatrix, matrix)
    }

    fun loadLight(light: PointLight) {
        loadVector(locationLightPosition, light.position)
        loadVector(locationLightColor, light.color)
    }

    fun loadShine(shineDamper: Float, reflectivity: Float) {
        loadFloat(locationShineDamper, shineDamper)
        loadFloat(locationReflectivity, reflectivity)
    }

    fun loadFakeLighting(useFakeLighting: Boolean) {
        loadFloat(locationUseFakeLighting, if (useFakeLighting) 1f else 0f)
    }

    fun loadSkyColor(skyColor: Vector3f) {
        loadVector(locationSkyColor, skyColor)
    }

    companion object {
        const val ATTR_POSITIONS = 0
        const val ATTR_TEXCOORDS = 1
        const val ATTR_NORMALS = 2
    }

}
