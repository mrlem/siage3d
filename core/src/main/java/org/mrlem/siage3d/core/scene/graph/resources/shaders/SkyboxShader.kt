package org.mrlem.siage3d.core.scene.graph.resources.shaders

import org.joml.Matrix4f
import org.joml.Vector3f
import org.mrlem.siage3d.core.R
import org.mrlem.siage3d.core.common.io.AssetManager.text

class SkyboxShader : Shader(
    text(R.raw.shader_skybox_v), text(R.raw.shader_skybox_f),
    Attribute.values().asList(), Uniform.values().asList()
), Shader.ProjectionAware, Shader.ViewAware, Shader.FogAware {

    private val rotationOnlyMatrix = Matrix4f()

    ///////////////////////////////////////////////////////////////////////////
    // Matrices
    ///////////////////////////////////////////////////////////////////////////

    override fun loadProjectionMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.PROJECTION_MATRIX, matrix)
    }

    override fun loadViewMatrix(matrix: Matrix4f) {
        rotationOnlyMatrix
            .set(matrix)
            .setTranslation(0f, 0f, 0f)
        loadMatrix(Uniform.VIEW_MATRIX, rotationOnlyMatrix)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Fog
    ///////////////////////////////////////////////////////////////////////////

    override fun loadFogColor(color: Vector3f) {
        loadVector(Uniform.FOG_COLOR, color)
    }

    override fun loadFogDensity(density: Float) {
        // nothing
    }

    override fun loadFogGradient(gradient: Float) {
        // nothing
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constants: attributes
    ///////////////////////////////////////////////////////////////////////////

    enum class Attribute(
        override val id: String,
        override val index: Int
    ) : AttributeDefinition {

        POSITIONS("position", 0)

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
        FOG_COLOR("fogColor")

    }

}
