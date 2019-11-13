package org.mrlem.siage3d.core.common.io

import android.content.res.Resources

abstract class AbstractCache<T> {

    protected val objects = mutableMapOf<String, T>()

    fun get(resources: Resources, resId: Int): T {
        return getOrCreate("$RESOURCE_SCHEME:$resId") { create(resources, resId)}
    }

    fun clear() {
        objects.clear()
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

    companion object {
        private const val RESOURCE_SCHEME = "res"
    }

}
