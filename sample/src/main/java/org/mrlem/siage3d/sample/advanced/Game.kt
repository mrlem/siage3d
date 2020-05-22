package org.mrlem.siage3d.sample.advanced

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_advanced.*
import org.mrlem.siage3d.core.SceneActivity
import org.mrlem.siage3d.core.view.DirectionPadView
import org.mrlem.siage3d.sample.R

class Game : SceneActivity() {

    override val layoutId: Int = R.layout.activity_advanced
    override val sceneId: Int = R.id.sceneView

    private val padListener = object : DirectionPadView.OnDirectionPadListener {
        override fun onDirectionChanged(direction: DirectionPadView.Direction?, active: Boolean) {
            when (direction) {
                DirectionPadView.Direction.UP ->
                    state.linearVelocity = if (active) 20f else 0f
                DirectionPadView.Direction.DOWN ->
                    state.linearVelocity = if (active) -20f else 0f
                DirectionPadView.Direction.LEFT ->
                    state.angularVelocity = if (active) -80f else 0f
                DirectionPadView.Direction.RIGHT ->
                    state.angularVelocity = if (active) 80f else 0f
                else -> {
                    state.linearVelocity = 0f
                    state.angularVelocity = 0f
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
