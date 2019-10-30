package org.mrlem.k3d.core

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.mrlem.k3d.core.view.K3DView

abstract class K3DActivity : AppCompatActivity() {

    abstract val sceneAdapter: K3DView.Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sceneView.adapter = sceneAdapter
    }

    override fun onStart() {
        super.onStart()
        initFullscreen()
    }

    private fun initFullscreen() {
        val uiVisibility = window.decorView.systemUiVisibility
        window.decorView.systemUiVisibility = uiVisibility or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    }

}
