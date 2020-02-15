package org.mrlem.siage3d.core.scene.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.k3d.core.R
import org.mrlem.siage3d.core.common.io.AssetManager.text
import org.mrlem.siage3d.core.scene.dsl.position
import org.mrlem.siage3d.core.scene.lights.PointLight

class MultiTextureShader : Shader(
    text(R.raw.shader_default_v), text(R.raw.shader_multitexture_f),
    Attribute.values().asList(), Uniform.values().asList()
), Shader.ProjectionAware, Shader.ViewAware, Shader.TransformationAware, Shader.LightAware, Shader.FogAware {

    ///////////////////////////////////////////////////////////////////////////
    // Matrices
    ///////////////////////////////////////////////////////////////////////////

    override fun loadProjectionMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.PROJECTION_MATRIX, matrix)
    }

    override fun loadViewMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.VIEW_MATRIX, matrix)
    }

    override fun loadTransformationMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.TRANSFORMATION_MATRIX, matrix)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lighting
    ///////////////////////////////////////////////////////////////////////////

    override fun loadLight(light: PointLight, index: Int) {
        loadVector(lightPosition[index], light.position())
        loadVector(lightAmbient[index], light.ambient)
        loadVector(lightDiffuse[index], light.diffuse)
    }

    fun loadFakeLighting(useFakeLighting: Boolean) {
        loadFloat(Uniform.USE_FAKE_LIGHTING, if (useFakeLighting) 1f else 0f)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Fog
    ///////////////////////////////////////////////////////////////////////////

    override fun loadFogColor(color: Vector3f) {
        loadVector(Uniform.FOG_COLOR, color)
    }

    override fun loadFogGradient(gradient: Float) {
        loadFloat(Uniform.FOG_GRADIENT, gradient)
    }

    override fun loadFogDensity(density: Float) {
        loadFloat(Uniform.FOG_DENSITY, density)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Material
    ///////////////////////////////////////////////////////////////////////////

    fun loadSamplers() {
        loadInt(Uniform.MATERIAL_DIFFUSE_BLEND_MAP, 0)
        loadInt(Uniform.MATERIAL_DIFFUSE_BACKGROUND, 1)
        loadInt(Uniform.MATERIAL_DIFFUSE_RED, 2)
        loadInt(Uniform.MATERIAL_DIFFUSE_GREEN, 3)
        loadInt(Uniform.MATERIAL_DIFFUSE_BLUE, 4)
    }

    fun loadAmbient(ambient: Float) {
        loadFloat(Uniform.MATERIAL_AMBIENT, ambient)
    }

    fun loadShine(shineDamper: Float, reflectivity: Float) {
        loadFloat(Uniform.MATERIAL_SHINE_DAMPER, shineDamper)
        loadFloat(Uniform.MATERIAL_REFLECTIVITY, reflectivity)
    }

    fun loadScale(scale: Float) {
        loadFloat(Uniform.MATERIAL_SCALE, scale)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constants: uniforms
    ///////////////////////////////////////////////////////////////////////////

    enum class Uniform(
        override val id: String,
        override var location: Int = 0
    ) : UniformDefinition {

        PROJECTION_MATRIX("projectionMatrix"),
        VIEW_MATRIX("viewMatrix"),
        TRANSFORMATION_MATRIX("transformationMatrix"),
        LIGHT0_POSITION("lights[0].position"),
        LIGHT0_AMBIENT("lights[0].ambient"),
        LIGHT0_DIFFUSE("lights[0].diffuse"),
        LIGHT1_POSITION("lights[1].position"),
        LIGHT1_AMBIENT("lights[1].ambient"),
        LIGHT1_DIFFUSE("lights[1].diffuse"),
        LIGHT2_POSITION("lights[2].position"),
        LIGHT2_AMBIENT("lights[2].ambient"),
        LIGHT2_DIFFUSE("lights[2].diffuse"),
        MATERIAL_AMBIENT("material.ambient"),
        MATERIAL_REFLECTIVITY("material.reflectivity"),
        MATERIAL_SHINE_DAMPER("material.shineDamper"),
        MATERIAL_DIFFUSE_BLEND_MAP("material.diffuse.blendMap"),
        MATERIAL_DIFFUSE_BACKGROUND("material.diffuse.backgroundTexture"),
        MATERIAL_DIFFUSE_RED("material.diffuse.rTexture"),
        MATERIAL_DIFFUSE_GREEN("material.diffuse.gTexture"),
        MATERIAL_DIFFUSE_BLUE("material.diffuse.bTexture"),
        MATERIAL_SCALE("material.scale"),
        USE_FAKE_LIGHTING("useFakeLighting"),
        FOG_COLOR("fog.color"),
        FOG_GRADIENT("fog.gradient"),
        FOG_DENSITY("fog.density"),

    }

    private val lightPosition = arrayOf(
        Uniform.LIGHT0_POSITION,
        Uniform.LIGHT1_POSITION,
        Uniform.LIGHT2_POSITION
    )

    private val lightAmbient = arrayOf(
        Uniform.LIGHT0_AMBIENT,
        Uniform.LIGHT1_AMBIENT,
        Uniform.LIGHT2_AMBIENT
    )

    private val lightDiffuse = arrayOf(
        Uniform.LIGHT0_DIFFUSE,
        Uniform.LIGHT1_DIFFUSE,
        Uniform.LIGHT2_DIFFUSE
    )

}
