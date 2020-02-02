package org.mrlem.siage3d.sample

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.mrlem.siage3d.core.SceneActivity
import org.mrlem.siage3d.core.view.DirectionPadView

class MainActivity : SceneActivity() {

    override val sceneAdapter by lazy { MainSceneAdapter() }

    private val padListener = object : DirectionPadView.OnDirectionPadListener {
        override fun onDirectionChanged(direction: DirectionPadView.Direction?, active: Boolean) {
            when (direction) {
                DirectionPadView.Direction.UP ->
                    sceneAdapter.linearVelocity = if (active) 20f else 0f
                DirectionPadView.Direction.DOWN ->
                    sceneAdapter.linearVelocity = if (active) -20f else 0f
                DirectionPadView.Direction.LEFT ->
                    sceneAdapter.angularVelocity = if (active) -80f else 0f
                DirectionPadView.Direction.RIGHT ->
                    sceneAdapter.angularVelocity = if (active) 80f else 0f
                else -> {
                    sceneAdapter.linearVelocity = 0f
                    sceneAdapter.angularVelocity = 0f
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pad.onDirectionPadListener = padListener
    }

}
