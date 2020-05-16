package org.mrlem.siage3d.core.view

import android.content.Context
import android.opengl.GLES30.*
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import org.mrlem.siage3d.core.common.io.*
import org.mrlem.siage3d.core.common.io.caches.ProgramCache
import org.mrlem.siage3d.core.common.io.caches.VaoCache
import org.mrlem.siage3d.core.common.io.caches.Texture2DCache
import org.mrlem.siage3d.core.common.io.caches.TextureCubemapCache
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SceneView(context: Context, attributes: AttributeSet) : GLSurfaceView(context, attributes) {

    var adapter: SceneAdapter? = null
    private var lastTime: Long = 0L

    private val renderer  = object : Renderer {

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            clearCaches()

            AssetManager.init(context)

            glEnable(GL_DEPTH_TEST)
            adapter?.init()

            lastTime = System.nanoTime()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            adapter?.resize(width, height)
        }

        override fun onDrawFrame(gl: GL10?) {
            // calculate delta since last frame
            val time = System.nanoTime()
            val delta = (time - lastTime) / 1000000000f
            lastTime = time

            adapter?.update(delta)
        }

    }

    init {
        setEGLContextClientVersion(3)
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
    }

    ///////////////////////////////////////////////////////////////////////////
    // Internal
    ///////////////////////////////////////////////////////////////////////////

    private fun clearCaches() {
        VaoCache.clear(true)
        Texture2DCache.clear(true)
        TextureCubemapCache.clear(true)
        ProgramCache.clear()
    }

}
