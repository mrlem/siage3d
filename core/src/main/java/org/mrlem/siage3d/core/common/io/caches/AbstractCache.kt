package org.mrlem.siage3d.core.common.io.caches

import android.content.res.Resources

abstract class AbstractCache<T> {

    protected val objects = mutableMapOf<String, T>()
    private val references = mutableListOf<Ref<T>>()

    abstract fun ref(resources: Resources, resId: Int): Ref<T>

    fun clear() {
        references.forEach { it.clear() }
        objects.clear()
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    protected fun get(resources: Resources, resId: Int): T {
        return getOrCreate("$RESOURCE_SCHEME:$resId") { create(resources, resId)}
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

    abstract class Ref<T> {

        abstract var value: T?

        abstract fun create(): T

        fun get(): T = value ?: create().also { value = it }

        fun clear() {
            value = null
        }

    }

    companion object {
        protected const val RESOURCE_SCHEME = "res"
        @JvmStatic
        protected val INTERNAL_SCHEME = "internal"
    }

}
