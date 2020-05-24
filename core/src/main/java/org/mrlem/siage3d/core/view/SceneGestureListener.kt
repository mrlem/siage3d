package org.mrlem.siage3d.core.view

import android.view.GestureDetector
import android.view.MotionEvent
import androidx.annotation.CallSuper

/**
 * Base listener class for gestures on the scene view.
 */
open class SceneGestureListener : GestureDetector.SimpleOnGestureListener() {

    internal var isScrolling = false

    override fun onDown(e: MotionEvent?) = true

    @CallSuper
    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        isScrolling = true
        return false
    }
    open fun onScrollEnd(endEvent: MotionEvent) {}

}
