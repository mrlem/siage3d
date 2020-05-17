package org.mrlem.siage3d.core.common.io.caches

import androidx.annotation.RawRes
import org.mrlem.siage3d.core.common.gl.Program
import org.mrlem.siage3d.core.common.io.AssetManager.text
import org.mrlem.siage3d.core.common.io.caches.AbstractCache.Ref

object ProgramCache {

    private val objects = mutableMapOf<String, Program>()
    private val references = mutableListOf<Ref<Program>>()

    fun init() {
        objects.clear()
        references.forEach { it.renew() }
    }

    fun ref(
        vertexShaderResId: Int, fragmentShaderResId: Int,
        attributes: List<Program.AttributeDefinition>, uniforms: List<Program.UniformDefinition>
    ): Ref<Program> = ProgramRef(vertexShaderResId, fragmentShaderResId, attributes, uniforms)
        .also { references.add(it) }

    private class ProgramRef(
        @RawRes private val vertexShaderResId: Int,
        @RawRes private val fragmentShaderResId: Int,
        private val attributes: List<Program.AttributeDefinition>,
        private val uniforms: List<Program.UniformDefinition>
    ) : Ref<Program>() {

        override lateinit var value: Program

        override fun create() = Program(text(vertexShaderResId), text(fragmentShaderResId), attributes, uniforms)

    }

}
