package org.mrlem.siage3d.sample

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.mrlem.siage3d.core.SceneActivity
import org.mrlem.siage3d.core.view.DirectionPadView

class MainActivity : SceneActivity() {

    override val sceneAdapter by lazy { MainSceneAdapter(resources) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pad.onDirectionPadListener = object : DirectionPadView.OnDirectionPadListener {
            override fun onDirectionChanged(direction: DirectionPadView.Direction?) {
                when (direction) {
                    DirectionPadView.Direction.UP -> sceneAdapter.linearVelocity = 20f
                    DirectionPadView.Direction.DOWN -> sceneAdapter.linearVelocity = -20f
                    DirectionPadView.Direction.LEFT -> sceneAdapter.angularVelocity = -30f
                    DirectionPadView.Direction.RIGHT -> sceneAdapter.angularVelocity = 30f
                    else -> {
                        sceneAdapter.linearVelocity = 0f
                        sceneAdapter.angularVelocity = 0f
                    }
                }
            }
        }
    }

}
