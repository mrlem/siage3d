package org.mrlem.k3d.sample

import android.os.Bundle
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*
import org.mrlem.k3d.core.SceneActivity

class MainActivity : SceneActivity() {

    override val sceneAdapter by lazy { MainSceneAdapter(resources) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO - minor - add game pad view to core

        topArrow.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    sceneAdapter.motion = -40f
                }
                MotionEvent.ACTION_UP -> {
                    sceneAdapter.motion = 0f
                }
            }
            false
        }

        bottomArrow.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    sceneAdapter.motion = 40f
                }
                MotionEvent.ACTION_UP -> {
                    sceneAdapter.motion = 0f
                }
            }
            false
        }
    }

}
