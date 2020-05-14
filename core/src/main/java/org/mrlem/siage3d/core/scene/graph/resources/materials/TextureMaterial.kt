package org.mrlem.siage3d.core.scene.graph.resources.materials

import org.mrlem.siage3d.core.common.gl.texture.Texture2D
import org.mrlem.siage3d.core.common.gl.shaders.TextureShader
import org.mrlem.siage3d.core.common.gl.shaders.Shader
import org.mrlem.siage3d.core.common.io.caches.Ref

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

    override val shader: TextureShader = Shader.defaultShader

    override fun setup() {
        if (hasTransparency) disableCulling() else enableCulling()

        shader.loadAmbient(ambient)
        shader.loadFakeLighting(fakeLighting)
        shader.loadShine(shineDamper, reflectvity)
        shader.loadScale(scale)

        textureRef.get().use()
    }

}
