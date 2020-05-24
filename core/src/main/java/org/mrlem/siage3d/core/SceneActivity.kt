package org.mrlem.siage3d.core

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import kotlinx.android.synthetic.main.activity_scene.*
import org.mrlem.siage3d.core.common.io.AssetManager
import org.mrlem.siage3d.core.view.SceneAdapter
import org.mrlem.siage3d.core.view.SceneGestureListener
import org.mrlem.siage3d.core.view.SceneView

/**
 * Base activity for displaying a 3D scene fullscreen.
 */
abstract class SceneActivity : AppCompatActivity() {

    @LayoutRes open val layoutId: Int = R.layout.activity_scene
    @IdRes open val sceneId: Int = R.id.sceneView

    /**
     * Listener for all gestures.
     */
    open val sceneGestureListener: SceneGestureListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AssetManager.init(applicationContext)

        // setup scene view
        setupFullscreen()
        setContentView(layoutId)
        findViewById<SceneView>(sceneId).apply {
            adapter = createSceneAdapter()

            sceneGestureListener?.let {
                val gestureDetector = GestureDetectorCompat(this@SceneActivity, it)
                setOnTouchListener { _, event ->
                    gestureDetector.onTouchEvent(event)
                    if (event.action == MotionEvent.ACTION_UP && it.isScrolling) {
                        it.isScrolling = false
                        it.onScrollEnd(event)
                    }
                    true
                }
            }
        }
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

    /**
     * Creates tje scene adapter that will be plugged into the [SceneView].
     *
     * @return the resulting scene adapter.
     */
    abstract fun createSceneAdapter(): SceneAdapter

}

/**
 * Perform all operations required to have a fullscreen app.
 * Just make sure you use the theme that inherits from a NoActionBar variant.
 */
fun AppCompatActivity.setupFullscreen() {
    val uiVisibility = window.decorView.systemUiVisibility
    window.decorView.systemUiVisibility = uiVisibility or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
}
