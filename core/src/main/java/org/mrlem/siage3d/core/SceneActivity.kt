package org.mrlem.siage3d.core

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_scene.*
import org.mrlem.siage3d.core.common.io.AssetManager
import org.mrlem.siage3d.core.view.SceneAdapter
import org.mrlem.siage3d.core.view.SceneView

abstract class SceneActivity<A : SceneAdapter> : AppCompatActivity() {

    @LayoutRes open val layoutId: Int = R.layout.activity_scene
    @IdRes open val sceneId: Int = R.id.sceneView

    protected val sceneAdapter: A by lazy { createSceneAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AssetManager.init(applicationContext)

        // setup scene view
        setupFullscreen()
        setContentView(layoutId)
        findViewById<SceneView>(sceneId).adapter = sceneAdapter
    }

    override fun onStart() {
        super.onStart()
        sceneView.onResume()
    }

    override fun onStop() {
        sceneView.onPause()
        super.onStop()
    }

    @CallSuper
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            setupFullscreen()
        }
    }

    abstract fun createSceneAdapter(): A

}

fun AppCompatActivity.setupFullscreen() {
    val uiVisibility = window.decorView.systemUiVisibility
    window.decorView.systemUiVisibility = uiVisibility or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}