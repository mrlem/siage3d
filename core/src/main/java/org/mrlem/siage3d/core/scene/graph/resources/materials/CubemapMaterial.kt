package org.mrlem.siage3d.core.scene.graph.resources.materials

import org.mrlem.siage3d.core.common.gl.TextureCubemap
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader
import org.mrlem.siage3d.core.scene.graph.resources.shaders.SkyboxShader

class CubemapMaterial(
    name: String,
    var texture: TextureCubemap
) : Material(name) {

    override val shader: SkyboxShader = Shader.skyboxShader

    override fun setup() {
        disableCulling()

        texture.use()
    }

}
