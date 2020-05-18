package org.mrlem.siage3d.sample.advanced

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_advanced.*
import org.mrlem.siage3d.core.SceneActivity
import org.mrlem.siage3d.core.view.DirectionPadView
import org.mrlem.siage3d.sample.R

class Game : SceneActivity<World>() {

    override val layoutId: Int = R.layout.activity_advanced
    override val sceneId: Int = R.id.sceneView

    private val padListener = object : DirectionPadView.OnDirectionPadListener {
        override fun onDirectionChanged(direction: DirectionPadView.Direction?, active: Boolean) {
            when (direction) {
                DirectionPadView.Direction.UP ->
                    world.linearVelocity = if (active) 20f else 0f
                DirectionPadView.Direction.DOWN ->
                    world.linearVelocity = if (active) -20f else 0f
                DirectionPadView.Direction.LEFT ->
                    world.angularVelocity = if (active) -80f else 0f
                DirectionPadView.Direction.RIGHT ->
                    world.angularVelocity = if (active) 80f else 0f
                else -> {
                    world.linearVelocity = 0f
                    world.angularVelocity = 0f
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pad.onDirectionPadListener = padListener
    }

    override fun createSceneAdapter() = SceneAdapter(initialScene)

}
