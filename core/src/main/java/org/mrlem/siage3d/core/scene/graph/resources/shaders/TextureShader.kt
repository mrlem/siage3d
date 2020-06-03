package org.mrlem.siage3d.core.scene.graph.resources.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.siage3d.core.R
import org.mrlem.siage3d.core.common.gl.Program
import org.mrlem.siage3d.core.common.math.directionUp
import org.mrlem.siage3d.core.scene.graph.nodes.lights.DirectionLightNode
import org.mrlem.siage3d.core.scene.graph.nodes.lights.PointLightNode

open class TextureShader: Shader(
    R.raw.shader_default_v, R.raw.shader_default_f,
    Program.Attribute.values().asList(), Uniform.values().asList()
), Shader.ProjectionAware, Shader.ViewAware, Shader.TransformationAware, Shader.LightAware, Shader.FogAware {

    ///////////////////////////////////////////////////////////////////////////
    // Matrices
    ///////////////////////////////////////////////////////////////////////////

    override fun loadProjectionMatrix(matrix: Matrix4f) {
        programRef.value.loadMatrix(Uniform.PROJECTION_MATRIX, matrix)
    }

    override fun loadViewMatrix(matrix: Matrix4f) {
        programRef.value.loadMatrix(Uniform.VIEW_MATRIX, matrix)
    }

    override fun loadTransformationMatrix(matrix: Matrix4f) {
        programRef.value.loadMatrix(Uniform.TRANSFORMATION_MATRIX, matrix)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Lighting
    ///////////////////////////////////////////////////////////////////////////

    override fun loadPointLight(light: PointLightNode, index: Int) {
        programRef.value.loadVector(lightPosition[index], light.translation)
        programRef.value.loadFloat(lightConstant[index], light.constant)
        programRef.value.loadFloat(lightLinear[index], light.linear)
        programRef.value.loadFloat(lightQuadratic[index], light.quadratic)
        programRef.value.loadVector(lightAmbient[index], light.ambient)
        programRef.value.loadVector(lightDiffuse[index], light.diffuse)
    }

    override fun loadDirectionLight(light: DirectionLightNode) {
        programRef.value.loadVector(Uniform.DIRECTIONLIGHT_DIRECTION, light.globalTransform.directionUp().negate())
        programRef.value.loadVector(Uniform.DIRECTIONLIGHT_AMBIENT, light.ambient)
        programRef.value.loadVector(Uniform.DIRECTIONLIGHT_DIFFUSE, light.diffuse)
    }

    fun loadFakeLighting(useFakeLighting: Boolean) {
        programRef.value.loadFloat(Uniform.USE_FAKE_LIGHTING, if (useFakeLighting) 1f else 0f)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Fog
    ///////////////////////////////////////////////////////////////////////////

    override fun loadFogColor(color: Vector3f) {
        programRef.value.loadVector(Uniform.FOG_COLOR, color)
    }

    override fun loadFogGradient(gradient: Float) {
        programRef.value.loadFloat(Uniform.FOG_GRADIENT, gradient)
    }

    override fun loadFogDensity(density: Float) {
        programRef.value.loadFloat(Uniform.FOG_DENSITY, density)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Material
    ///////////////////////////////////////////////////////////////////////////

    fun loadAmbient(ambient: Float) {
        programRef.value.loadFloat(Uniform.MATERIAL_AMBIENT, ambient)
    }

    fun loadShine(shineDamper: Float, reflectivity: Float) {
        programRef.value.loadFloat(Uniform.MATERIAL_SHINE_DAMPER, shineDamper)
        programRef.value.loadFloat(Uniform.MATERIAL_REFLECTIVITY, reflectivity)
    }

    fun loadScale(scale: Float) {
        programRef.value.loadFloat(Uniform.MATERIAL_SCALE, scale)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constants: uniforms
    ///////////////////////////////////////////////////////////////////////////

    enum class Uniform(
        override val id: String,
        override var location: Int = 0
    ) : Program.UniformDefinition {

        PROJECTION_MATRIX("projectionMatrix"),
        VIEW_MATRIX("viewMatrix"),
        TRANSFORMATION_MATRIX("transformationMatrix"),
        DIRECTIONLIGHT_DIRECTION("directionLight.direction"),
        DIRECTIONLIGHT_AMBIENT("directionLight.color.ambient"),
        DIRECTIONLIGHT_DIFFUSE("directionLight.color.diffuse"),
        POINTLIGHT0_POSITION("pointLights[0].position"),
        POINTLIGHT0_CONSTANT("pointLights[0].constant"),
        POINTLIGHT0_LINEAR("pointLights[0].linear"),
        POINTLIGHT0_QUADRATIC("pointLights[0].quadratic"),
        POINTLIGHT0_AMBIENT("pointLights[0].color.ambient"),
        POINTLIGHT0_DIFFUSE("pointLights[0].color.diffuse"),
        POINTLIGHT1_POSITION("pointLights[1].position"),
        POINTLIGHT1_CONSTANT("pointLights[1].constant"),
        POINTLIGHT1_LINEAR("pointLights[1].linear"),
        POINTLIGHT1_QUADRATIC("pointLights[1].quadratic"),
        POINTLIGHT1_AMBIENT("pointLights[1].color.ambient"),
        POINTLIGHT1_DIFFUSE("pointLights[1].color.diffuse"),
        POINTLIGHT2_POSITION("pointLights[2].position"),
        POINTLIGHT2_CONSTANT("pointLights[2].constant"),
        POINTLIGHT2_LINEAR("pointLights[2].linear"),
        POINTLIGHT2_QUADRATIC("pointLights[2].quadratic"),
        POINTLIGHT2_AMBIENT("pointLights[2].color.ambient"),
        POINTLIGHT2_DIFFUSE("pointLights[2].color.diffuse"),
        MATERIAL_AMBIENT("material.ambient"),
        MATERIAL_REFLECTIVITY("material.reflectivity"),
        MATERIAL_SHINE_DAMPER("material.shineDamper"),
        MATERIAL_SCALE("material.scale"),
        USE_FAKE_LIGHTING("useFakeLighting"),
        FOG_COLOR("fog.color"),
        FOG_GRADIENT("fog.gradient"),
        FOG_DENSITY("fog.density")

    }

    companion object {

        private val lightPosition = arrayOf(
            Uniform.POINTLIGHT0_POSITION,
            Uniform.POINTLIGHT1_POSITION,
            Uniform.POINTLIGHT2_POSITION
        )

        private val lightConstant = arrayOf(
            Uniform.POINTLIGHT0_CONSTANT,
            Uniform.POINTLIGHT1_CONSTANT,
            Uniform.POINTLIGHT2_CONSTANT
        )

        private val lightLinear = arrayOf(
            Uniform.POINTLIGHT0_LINEAR,
            Uniform.POINTLIGHT1_LINEAR,
            Uniform.POINTLIGHT2_LINEAR
        )

        private val lightQuadratic = arrayOf(
            Uniform.POINTLIGHT0_QUADRATIC,
            Uniform.POINTLIGHT1_QUADRATIC,
            Uniform.POINTLIGHT2_QUADRATIC
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

}
