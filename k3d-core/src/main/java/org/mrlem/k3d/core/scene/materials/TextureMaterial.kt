package org.mrlem.k3d.core.scene.materials

import org.mrlem.k3d.core.common.gl.Texture

class TextureMaterial(
    private val texture: Texture
) : Material() {

    override fun use(block: Material.() -> Unit) {
        if (texture == activeTexture) return

        texture.use {
            super.use(block)
        }
    }

    companion object {
        private val activeTexture: Texture? = null
    }

}
