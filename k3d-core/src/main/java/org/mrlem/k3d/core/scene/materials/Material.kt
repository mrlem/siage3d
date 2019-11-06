package org.mrlem.k3d.core.scene.materials

import org.mrlem.k3d.core.scene.shaders.Shader

abstract class Material {

    abstract val shader: Shader

    open fun use(block: Material.() -> Unit) {
        this.block()
    }

}