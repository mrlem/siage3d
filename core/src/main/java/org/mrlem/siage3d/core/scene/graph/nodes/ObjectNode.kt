package org.mrlem.siage3d.core.scene.graph.nodes

import org.mrlem.siage3d.core.scene.graph.resources.materials.Material
import org.mrlem.siage3d.core.common.gl.shaders.Shader
import org.mrlem.siage3d.core.scene.graph.resources.shapes.Shape

open class ObjectNode(
    val shape: Shape?,
    val material: Material?,
    name: String? = null
) : SpatialNode(name ?: "Object #${counter++}") {

    open fun render(shader: Shader = material?.shader ?: Shader.defaultShader) {
        (shader as? Shader.TransformationAware)?.loadTransformationMatrix(globalTransform)

        // draw the shape: material is handled at scene-level
        shape?.draw()
    }

    companion object {
        private var counter = 0
    }

}