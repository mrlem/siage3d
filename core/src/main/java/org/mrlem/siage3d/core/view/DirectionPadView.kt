package org.mrlem.siage3d.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_direction_pad.view.*
import org.mrlem.siage3d.core.R

class DirectionPadView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val onTouchListener = OnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> notifyDirectionChanged(view, true)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> notifyDirectionChanged(view, false)
        }
        false
    }

    var onDirectionPadListener: OnDirectionPadListener? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_direction_pad, this, true)
        upArrow.setOnTouchListener(onTouchListener)
        downArrow.setOnTouchListener(onTouchListener)
        leftArrow.setOnTouchListener(onTouchListener)
        rightArrow.setOnTouchListener(onTouchListener)
    }

    private fun notifyDirectionChanged(view: View, active: Boolean) {
        when (view) {
            upArrow -> onDirectionPadListener?.onDirectionChanged(Direction.UP, active)
            downArrow -> onDirectionPadListener?.onDirectionChanged(Direction.DOWN, active)
            leftArrow -> onDirectionPadListener?.onDirectionChanged(Direction.LEFT, active)
            rightArrow -> onDirectionPadListener?.onDirectionChanged(Direction.RIGHT, active)
        }
    }

    interface OnDirectionPadListener {
        fun onDirectionChanged(direction: Direction?, active: Boolean)
    }

    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

}
