package org.mrlem.siage3d.core.scene.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.k3d.core.R
import org.mrlem.siage3d.core.common.io.AssetManager.text
import org.mrlem.siage3d.core.scene.lights.PointLight

open class DefaultShader: Shader(
    text(R.raw.shader_default_v), text(R.raw.shader_default_f),
    Attribute.values().asList(), Uniform.values().asList()
), Shader.ProjectionAware, Shader.ViewAware, Shader.TransformationAware, Shader.LightAware, Shader.FogAware {

    override fun loadProjectionMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.PROJECTION_MATRIX, matrix)
    }

    override fun loadViewMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.VIEW_MATRIX, matrix)
    }

    override fun loadTransformationMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.TRANSFORMATION_MATRIX, matrix)
    }

    override fun loadLight(light: PointLight, index: Int) {
        // TODO - use index
        loadVector(Uniform.LIGHT_POSITION, light.position)
        loadVector(Uniform.LIGHT_AMBIENT, light.ambient)
        loadVector(Uniform.LIGHT_DIFFUSE, light.diffuse)
    }

    fun loadShine(shineDamper: Float, reflectivity: Float) {
        loadFloat(Uniform.SHINE_DAMPER, shineDamper)
        loadFloat(Uniform.REFLECTIVITY, reflectivity)
    }

    fun loadFakeLighting(useFakeLighting: Boolean) {
        loadFloat(Uniform.USE_FAKE_LIGHTING, if (useFakeLighting) 1f else 0f)
    }

    override fun loadFogColor(skyColor: Vector3f) {
        loadVector(Uniform.FOG_COLOR, skyColor)
    }

    override fun loadFogGradient(gradient: Float) {
        loadFloat(Uniform.FOG_GRADIENT, gradient)
    }

    override fun loadFogDensity(density: Float) {
        loadFloat(Uniform.FOG_DENSITY, density)
    }

    fun loadScale(scale: Float) {
        loadFloat(Uniform.SCALE, scale)
    }

    enum class Uniform(
        override val id: String,
        override var location: Int = 0
    ) : UniformDefinition {

        PROJECTION_MATRIX("projectionMatrix"),
        VIEW_MATRIX("viewMatrix"),
        TRANSFORMATION_MATRIX("transformationMatrix"),
        LIGHT_POSITION("lights[0].position"),
        LIGHT_AMBIENT("lights[0].ambient"),
        LIGHT_DIFFUSE("lights[0].diffuse"),
        REFLECTIVITY("material.reflectivity"),
        SHINE_DAMPER("material.shineDamper"),
        SCALE("material.scale"),
        USE_FAKE_LIGHTING("useFakeLighting"),
        FOG_COLOR("fog.color"),
        FOG_GRADIENT("fog.gradient"),
        FOG_DENSITY("fog.density")

    }

}
