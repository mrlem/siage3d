package org.mrlem.siage3d.core.scene.shaders

import org.joml.Matrix4f
import org.mrlem.siage3d.core.R
import org.mrlem.siage3d.core.common.io.AssetManager.text

open class DepthMapShader: Shader(
    text(R.raw.shader_depthmap_v), text(R.raw.shader_depthmap_f),
    listOf(Attribute.POSITIONS), Uniform.values().asList()
), Shader.TransformationAware {

    ///////////////////////////////////////////////////////////////////////////
    // Matrices
    ///////////////////////////////////////////////////////////////////////////

    fun loadProjectionViewMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.PROJECTION_VIEW_MATRIX, matrix)
    }

    override fun loadTransformationMatrix(matrix: Matrix4f) {
        loadMatrix(Uniform.TRANSFORMATION_MATRIX, matrix)
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constants: uniforms
    ///////////////////////////////////////////////////////////////////////////

    enum class Uniform(
        override val id: String,
        override var location: Int = 0
    ) : UniformDefinition {

        PROJECTION_VIEW_MATRIX("projectionViewMatrix"),
        TRANSFORMATION_MATRIX("transformationMatrix")
    }

}
