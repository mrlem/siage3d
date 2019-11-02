package org.mrlem.k3d.core.view

import android.content.Context
import android.opengl.GLES30.*
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.SurfaceHolder
import org.mrlem.k3d.core.R
import org.mrlem.k3d.core.common.io.TextureCache
import org.mrlem.k3d.core.common.io.VaoCache
import org.mrlem.k3d.core.common.io.readText
import org.mrlem.k3d.core.scene.shaders.DefaultShader
import org.mrlem.k3d.core.scene.shaders.Shader
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

// TODO - one shader instance per type of objects, so you don't have to constantly load uniforms, juste useProgram
// TODO - move draw code to shader? (only he nows about its attributes and how to map them to the shape's vao)

class SceneView(context: Context, attributes: AttributeSet) : GLSurfaceView(context, attributes) {

    var adapter: SceneAdapter? = null
    private var lastTime = System.nanoTime()

    private val renderer  = object : Renderer {

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            Shader.defaultShader = DefaultShader(
                resources.readText(R.raw.shader_default_v),
                resources.readText(R.raw.shader_default_f)
            )
            glEnable(GL_DEPTH_TEST)
            adapter?.init()
        }

        override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
            glViewport(0, 0, width, height)
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

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        adapter?.destroy()
        VaoCache.clear()
        TextureCache.clear()
        super.surfaceDestroyed(holder)
    }

}
