package org.mrlem.siage3d.core.common.io.caches

import android.content.res.Resources
import androidx.annotation.RawRes
import org.mrlem.siage3d.core.common.gl.arrays.Vao
import org.mrlem.siage3d.core.common.gl.arrays.VaoFactory
import org.mrlem.siage3d.core.common.io.AssetManager.text
import org.mrlem.siage3d.core.common.io.loaders.ObjLoader
import org.mrlem.siage3d.core.scene.graph.resources.shapes.Shape

object VaoCache : AbstractCache<Vao>() {

    override fun ref(resources: Resources, resId: Int): Ref<Vao> = VaoRef(resources, resId)
        .also { references.add(it) }

    fun ref(key: String, creator: () -> Shape.Data): Ref<Vao> = PredefinedVaoRef(key, creator)
        .also { references.add(it) }

    override fun create(resources: Resources, resId: Int): Vao {
        return VaoFactory.createVao(ObjLoader().load(text(resId)))
    }

    fun get(key: String, creator: () -> Shape.Data): Vao = getOrCreate("$INTERNAL_SCHEME:$key") {
        VaoFactory.createVao(creator())
    }

    private class VaoRef(
        private val resources: Resources,
        @RawRes private val resId: Int
    ) : AbstractCache.Ref<Vao>() {

        override var value: Vao = create()

        override fun create(): Vao = VaoCache.get(resources, resId)

    }

    private class PredefinedVaoRef(
        private val key: String,
        private val creator: () -> Shape.Data
    ) : AbstractCache.Ref<Vao>() {

        override var value: Vao = create()

        override fun create(): Vao = VaoCache.get(key, creator)

    }

}
