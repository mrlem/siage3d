package org.mrlem.siage3d.core.scene.graph.resources.materials

import org.mrlem.siage3d.core.common.gl.texture.Texture2D
import org.mrlem.siage3d.core.scene.graph.resources.shaders.TextureShader
import org.mrlem.siage3d.core.common.io.caches.AbstractCache.Ref
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader

/**
 * Texture-based material consisting in an image & various properties.
 *
 * @param name see [Material.name].
 * @property textureRef reference to the texture to be drawn.
 * @property scale scale to apply the texture.
 * @property ambient amount of ambient light produced by the material.
 * @property shineDamper how does reflectivity induced shininess spread on the surface.
 * @property reflectvity how shiny is the surface?
 * @property hasTransparency whether the material texture will allows to see what's behind it.
 * @property fakeLighting whether the material needs fake lighting (when faking a more complex shape with a texture).
 */
class TextureMaterial(
    name: String,
    var textureRef: Ref<Texture2D>,
    var scale: Float = 1f,
    var ambient: Float = 1f,
    var shineDamper: Float = 1f,
    var reflectvity: Float = 0f,
    var hasTransparency: Boolean = false,
    var fakeLighting: Boolean = false
) : Material(name) {

    override val shader: TextureShader = Shader.textureShader

    override fun setup() {
        if (hasTransparency) disableCulling() else enableCulling()

        shader.loadAmbient(ambient)
        shader.loadFakeLighting(fakeLighting)
        shader.loadShine(shineDamper, reflectvity)
        shader.loadScale(scale)

        textureRef.value.use()
    }

}
