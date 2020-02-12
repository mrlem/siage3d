package org.mrlem.siage3d.core.scene.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.k3d.core.R
import org.mrlem.siage3d.core.common.io.AssetManager.text
import org.mrlem.siage3d.core.scene.lights.PointLight

class MultiTextureShader : Shader(
    text(R.raw.shader_default_v), text(R.raw.shader_multitexture_f),
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

    fun loadShine(shineDamper: Float, reflectivity: Float) {
        loadFloat(Uniform.SHINE_DAMPER, shineDamper)
        loadFloat(Uniform.REFLECTIVITY, reflectivity)
    }

    fun loadFakeLighting(useFakeLighting: Boolean) {
        loadFloat(Uniform.USE_FAKE_LIGHTING, if (useFakeLighting) 1f else 0f)
    }

    override fun loadLight(light: PointLight) {
        loadVector(Uniform.LIGHT_POSITION, light.position)
        loadVector(Uniform.LIGHT_AMBIENT, light.ambient)
        loadVector(Uniform.LIGHT_DIFFUSE, light.diffuse)
    }

    override fun loadFogColor(color: Vector3f) {
        loadVector(Uniform.FOG_COLOR, color)
    }

    override fun loadFogGradient(gradient: Float) {
        loadFloat(Uniform.FOG_GRADIENT, gradient)
    }

    override fun loadFogDensity(density: Float) {
        loadFloat(Uniform.FOG_DENSITY, density)
    }

    fun loadTileSize(tileSize: Float) {
        loadFloat(Uniform.TILE_SIZE, tileSize)
    }

    fun loadSamplers() {
        loadInt(Uniform.BLEND_MAP, 0)
        loadInt(Uniform.TEXTURE_BACKGROUND, 1)
        loadInt(Uniform.TEXTURE_RED, 2)
        loadInt(Uniform.TEXTURE_GREEN, 3)
        loadInt(Uniform.TEXTURE_BLUE, 4)
    }

    enum class Uniform(
        override val id: String,
        override var location: Int = 0
    ) : UniformDefinition {

        PROJECTION_MATRIX("projectionMatrix"),
        VIEW_MATRIX("viewMatrix"),
        TRANSFORMATION_MATRIX("transformationMatrix"),
        LIGHT_POSITION("light.position"),
        LIGHT_AMBIENT("light.ambient"),
        LIGHT_DIFFUSE("light.diffuse"),
        REFLECTIVITY("material.reflectivity"),
        SHINE_DAMPER("material.shineDamper"),
        BLEND_MAP("material.diffuse.blendMap"),
        TEXTURE_BACKGROUND("material.diffuse.backgroundTexture"),
        TEXTURE_RED("material.diffuse.rTexture"),
        TEXTURE_GREEN("material.diffuse.gTexture"),
        TEXTURE_BLUE("material.diffuse.bTexture"),
        USE_FAKE_LIGHTING("useFakeLighting"),
        FOG_COLOR("fog.color"),
        FOG_GRADIENT("fog.gradient"),
        FOG_DENSITY("fog.density"),
        TILE_SIZE("tileSize"),

    }

}
