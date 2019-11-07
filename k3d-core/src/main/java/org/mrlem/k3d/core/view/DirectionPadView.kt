package org.mrlem.k3d.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_direction_pad.view.*
import org.mrlem.k3d.core.R

class DirectionPadView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val onTouchListener = OnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                when (view) {
                    upArrow -> onDirectionPadListener?.onDirectionChanged(Direction.UP)
                    downArrow -> onDirectionPadListener?.onDirectionChanged(Direction.DOWN)
                    leftArrow -> onDirectionPadListener?.onDirectionChanged(Direction.LEFT)
                    rightArrow -> onDirectionPadListener?.onDirectionChanged(Direction.RIGHT)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                onDirectionPadListener?.onDirectionChanged(null)
            }
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

    interface OnDirectionPadListener {
        fun onDirectionChanged(direction: Direction?)
    }

    enum class Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

}
