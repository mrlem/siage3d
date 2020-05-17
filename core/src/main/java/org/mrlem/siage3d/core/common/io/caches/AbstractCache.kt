package org.mrlem.siage3d.core.common.io.caches

import android.content.res.Resources

abstract class AbstractCache<T : Any> {

    private val objects = mutableMapOf<String, T>()
    protected val references = mutableListOf<Ref<T>>()

    fun init() {
        objects.clear()
        references.forEach { it.renew() }
    }

    abstract fun ref(resources: Resources, resId: Int): Ref<T>

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    protected fun get(resources: Resources, resId: Int): T {
        return getOrCreate("$RESOURCE_SCHEME:$resId") { create(resources, resId) }
    }

    protected fun getOrCreate(key: String, creator: () -> T): T {
        // in cache?
        objects[key]?.also { cache -> return cache }

        // or create it
        return creator().also {
            objects[key] = it
        }
    }

    protected abstract fun create(resources: Resources, resId: Int): T

    abstract class Ref<T : Any> {

        abstract var value: T

        protected abstract fun create(): T

        fun renew() {
            value = create()
        }

    }

    companion object {
        protected const val RESOURCE_SCHEME = "res"
        @JvmStatic
        protected val INTERNAL_SCHEME = "internal"
    }

}
