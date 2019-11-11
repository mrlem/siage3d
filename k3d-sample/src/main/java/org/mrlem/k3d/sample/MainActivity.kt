package org.mrlem.k3d.sample

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import org.mrlem.k3d.core.SceneActivity
import org.mrlem.k3d.core.view.DirectionPadView

class MainActivity : SceneActivity() {

    override val sceneAdapter by lazy { MainSceneAdapter(resources) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pad.onDirectionPadListener = object : DirectionPadView.OnDirectionPadListener {
            override fun onDirectionChanged(direction: DirectionPadView.Direction?) {
                println("direction: $direction")
                when (direction) {
                    DirectionPadView.Direction.UP -> sceneAdapter.motion.set(0f, 0f, -2f)
                    DirectionPadView.Direction.DOWN -> sceneAdapter.motion.set(0f, 0f, 2f)
                    DirectionPadView.Direction.LEFT -> sceneAdapter.motion.set(-2f, 0f, 0f)
                    DirectionPadView.Direction.RIGHT -> sceneAdapter.motion.set(2f, 0f, 0f)
                    else -> sceneAdapter.motion.set(0f, 0f, 0f)
                }
            }
        }
    }

}
