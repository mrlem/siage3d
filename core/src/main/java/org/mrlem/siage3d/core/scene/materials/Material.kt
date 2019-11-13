package org.mrlem.siage3d.core.scene.materials

import org.mrlem.siage3d.core.scene.shaders.Shader

abstract class Material {

    abstract val shader: Shader

    open fun use(block: Material.() -> Unit) {
        this.block()
    }

}