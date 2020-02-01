package org.mrlem.siage3d.core.scene.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.k3d.core.R
import org.mrlem.siage3d.core.common.io.AssetManager.text
import org.mrlem.siage3d.core.scene.lights.PointLight

class MultiTextureShader : Shader(
    text(R.raw.shader_default_v), text(R.raw.shader_multitexture_f),
    Attribute.values().asList(), Uniform.values().asList()
), Shader.ProjectionAware, Shader.ViewAware, Shader.TransformationAware, Shader.LightAware {

    override fun loadProjectionMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.PROJECTION_MATRIX, matrix)
    }

    override fun loadViewMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.VIEW_MATRIX, matrix)
    }

    override fun loadTransformationMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.TRANSFORMATION_MATRIX, matrix)
    }

    override fun loadLight(light: PointLight) {
        loadVector(Uniform.LIGHT_POSITION, light.position)
        loadVector(Uniform.LIGHT_COLOR, light.color)
    }

    override fun loadSkyColor(skyColor: Vector3f) {
        loadVector(Uniform.SKY_COLOR, skyColor)
    }

    fun loadShine(shineDamper: Float, reflectivity: Float) {
        loadFloat(Uniform.SHINE_DAMPER, shineDamper)
        loadFloat(Uniform.REFLECTIVITY, reflectivity)
    }

    fun loadFakeLighting(useFakeLighting: Boolean) {
        loadFloat(Uniform.USE_FAKE_LIGHTING, if (useFakeLighting) 1f else 0f)
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
        LIGHT_POSITION("lightPosition"),
        LIGHT_COLOR("lightColor"),
        SHINE_DAMPER("shineDamper"),
        REFLECTIVITY("reflectivity"),
        USE_FAKE_LIGHTING("useFakeLighting"),
        SKY_COLOR("skyColor"),
        TILE_SIZE("tileSize"),

        BLEND_MAP("blendMap"),
        TEXTURE_BACKGROUND("backgroundTexture"),
        TEXTURE_RED("rTexture"),
        TEXTURE_GREEN("gTexture"),
        TEXTURE_BLUE("bTexture")

    }

}
