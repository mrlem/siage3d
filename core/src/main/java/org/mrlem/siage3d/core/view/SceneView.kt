package org.mrlem.siage3d.core.view

import android.content.Context
import android.opengl.GLES30.*
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.SurfaceHolder
import org.mrlem.k3d.core.R
import org.mrlem.siage3d.core.common.io.*
import org.mrlem.siage3d.core.common.io.AssetManager.text
import org.mrlem.siage3d.core.scene.shaders.DefaultShader
import org.mrlem.siage3d.core.scene.shaders.Shader
import org.mrlem.siage3d.core.scene.shaders.SkyboxShader
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

// TODO - optional - move draw code to shader? (only he knows about its attributes and how to map them to the shape's vao)
// TODO - optional - replace vectors / matrices with float[] and android matrices + ext functions?

class SceneView(context: Context, attributes: AttributeSet) : GLSurfaceView(context, attributes) {

    var adapter: SceneAdapter? = null
    private var lastTime = System.nanoTime()

    private val renderer  = object : Renderer {

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            AssetManager.init(context)

            Shader.defaultShader = DefaultShader(
                text(R.raw.shader_default_v),
                text(R.raw.shader_default_f)
            )
            Shader.skyboxShader = SkyboxShader(
                text(R.raw.shader_skybox_v),
                text(R.raw.shader_skybox_f)
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
        ShapeCache.clear(true)
        Texture2DCache.clear(true)
        TextureCubemapCache.clear(true)
        super.surfaceDestroyed(holder)
    }

}
