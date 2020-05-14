package org.mrlem.siage3d.core.common.io.caches

import android.content.res.Resources

abstract class AbstractCache<T> {

    protected val objects = mutableMapOf<String, T>()

    fun clear() {
        objects.clear()
    }

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

    abstract fun ref(resources: Resources, resId: Int): Ref<T>
    protected abstract fun create(resources: Resources, resId: Int): T

    companion object {
        protected const val RESOURCE_SCHEME = "res"
        @JvmStatic
        protected val INTERNAL_SCHEME = "internal"
    }

}
