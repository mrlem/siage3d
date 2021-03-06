package org.mrlem.siage3d.core.view

import android.content.Context
import android.opengl.GLES30.GL_DEPTH_TEST
import android.opengl.GLES30.glEnable
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import org.mrlem.siage3d.core.common.io.caches.ProgramCache
import org.mrlem.siage3d.core.common.io.caches.Texture2DCache
import org.mrlem.siage3d.core.common.io.caches.TextureCubemapCache
import org.mrlem.siage3d.core.common.io.caches.VaoCache
import org.mrlem.siage3d.core.scene.graph.Scene
import org.mrlem.siage3d.core.scene.graph.resources.shaders.Shader
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * View that displays a 3D [Scene].
 * Internally, this is a [GLSurfaceView], so the same limitations apply.
 *
 * @param context context to bind this view to.
 * @param attributes attributes to configure the view with.
 */
class SceneView(context: Context, attributes: AttributeSet) : GLSurfaceView(context, attributes) {

    private var lastTime: Long = 0L

    private val renderer  = object : Renderer {

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            Shader.shaders

            // purge old gl refs from caches
            VaoCache.init()
            Texture2DCache.init()
            TextureCubemapCache.init()
            ProgramCache.init()

            // init adapter
            glEnable(GL_DEPTH_TEST)

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

            // update adapter
            adapter?.update(delta)
        }

    }

    /**
     * Adapter that prorives the 3D scene to be displayed.
     */
    var adapter: SceneAdapter? = null

    init {
        setEGLContextClientVersion(3)
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
    }

}
