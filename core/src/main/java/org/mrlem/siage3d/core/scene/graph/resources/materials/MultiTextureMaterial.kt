package org.mrlem.siage3d.core.scene.graph.resources.materials

import android.opengl.GLES30.*
import org.mrlem.siage3d.core.common.gl.texture.Texture2D
import org.mrlem.siage3d.core.scene.graph.resources.shaders.MultiTextureShader
import org.mrlem.siage3d.core.common.io.caches.AbstractCache.Ref
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader

/**
 * Texture map based material consisting in several textures, and map to say which part should contain which texture &
 * various properties.
 *
 * @param name see [Material.name].
 * @property blendMapRef reference to the texture map for selecting the textures.
 * @property backgroundTextureRef reference to the texture when no other is drawn..
 * @property redTextureRef reference to the texture drawn for texture map's red component.
 * @property greenTextureRef reference to the texture drawn for texture map's green component.
 * @property blueTextureRef reference to the texture drawn for texture map's blue component.
 * @property scale scale to apply the texture.
 * @property ambient amount of ambient light produced by the material.
 * @property shineDamper how does reflectivity induced shininess spread on the surface.
 * @property reflectvity how shiny is the surface?
 * @property hasTransparency whether the material texture will allows to see what's behind it.
 * @property fakeLighting whether the material needs fake lighting (when faking a more complex shape with a texture).
 */
class MultiTextureMaterial(
    name: String,
    private val blendMapRef: Ref<Texture2D>,
    private val backgroundTextureRef: Ref<Texture2D>,
    private val redTextureRef: Ref<Texture2D>,
    private val greenTextureRef: Ref<Texture2D>,
    private val blueTextureRef: Ref<Texture2D>,
    private val scale: Float = 1f,
    private val ambient: Float = 1f,
    private var shineDamper: Float = 1f,
    private var reflectvity: Float = 0f,
    private val hasTransparency: Boolean = false,
    private val fakeLighting: Boolean = false
) : Material(name) {

    override val shader: MultiTextureShader = Shader.multiTextureShader

    override fun setup() {
        if (hasTransparency) disableCulling() else enableCulling()

        shader.loadAmbient(ambient)
        shader.loadFakeLighting(fakeLighting)
        shader.loadShine(shineDamper, reflectvity)
        shader.loadScale(scale)
        shader.loadSamplers()

        blendMapRef.value.use(GL_TEXTURE0)
        backgroundTextureRef.value.use(GL_TEXTURE1)
        redTextureRef.value.use(GL_TEXTURE2)
        greenTextureRef.value.use(GL_TEXTURE3)
        blueTextureRef.value.use(GL_TEXTURE4)
    }

}
