package org.mrlem.siage3d.core.scene.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.k3d.core.R
import org.mrlem.siage3d.core.common.io.AssetManager.text
import org.mrlem.siage3d.core.common.math.directionUp
import org.mrlem.siage3d.core.scene.dsl.position
import org.mrlem.siage3d.core.scene.lights.DirectionLight
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

    override fun loadPointLight(light: PointLight, index: Int) {
        loadVector(lightPosition[index], light.position())
        loadVector(lightAmbient[index], light.ambient)
        loadVector(lightDiffuse[index], light.diffuse)
    }

    override fun loadDirectionLight(light: DirectionLight) {
        loadVector(Uniform.DIRECTIONLIGHT_DIRECTION, light.localTransform.directionUp().negate())
        loadVector(Uniform.DIRECTIONLIGHT_AMBIENT, light.ambient)
        loadVector(Uniform.DIRECTIONLIGHT_DIFFUSE, light.diffuse)
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
        DIRECTIONLIGHT_DIRECTION("directionLight.direction"),
        DIRECTIONLIGHT_AMBIENT("directionLight.ambient"),
        DIRECTIONLIGHT_DIFFUSE("directionLight.diffuse"),
        POINTLIGHT0_POSITION("pointLights[0].position"),
        POINTLIGHT0_AMBIENT("pointLights[0].ambient"),
        POINTLIGHT0_DIFFUSE("pointLights[0].diffuse"),
        POINTLIGHT1_POSITION("pointLights[1].position"),
        POINTLIGHT1_AMBIENT("pointLights[1].ambient"),
        POINTLIGHT1_DIFFUSE("pointLights[1].diffuse"),
        POINTLIGHT2_POSITION("pointLights[2].position"),
        POINTLIGHT2_AMBIENT("pointLights[2].ambient"),
        POINTLIGHT2_DIFFUSE("pointLights[2].diffuse"),
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
        Uniform.POINTLIGHT0_POSITION,
        Uniform.POINTLIGHT1_POSITION,
        Uniform.POINTLIGHT2_POSITION
    )

    private val lightAmbient = arrayOf(
        Uniform.POINTLIGHT0_AMBIENT,
        Uniform.POINTLIGHT1_AMBIENT,
        Uniform.POINTLIGHT2_AMBIENT
    )

    private val lightDiffuse = arrayOf(
        Uniform.POINTLIGHT0_DIFFUSE,
        Uniform.POINTLIGHT1_DIFFUSE,
        Uniform.POINTLIGHT2_DIFFUSE
    )

}
