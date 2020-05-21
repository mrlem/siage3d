package org.mrlem.siage3d.core.scene.graph.nodes

import org.mrlem.siage3d.core.scene.graph.resources.materials.Material
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader
import org.mrlem.siage3d.core.scene.graph.resources.shapes.Shape

/**
 * A scene-graph spatial node that renders something.
 *
 * @property shape shape to render.
 * @property material material with which to render the shape.
 * @param name see [Node.name].
 */
open class ObjectNode(
    val shape: Shape?,
    val material: Material?,
    name: String? = null
) : SpatialNode(name ?: "Object #${counter++}") {

    open fun render(shader: Shader = material?.shader ?: Shader.textureShader) {
        (shader as? Shader.TransformationAware)?.loadTransformationMatrix(globalTransform)

        // draw the shape: material is handled at scene-level
        shape?.draw()
    }

    companion object {
        private var counter = 0
    }

}