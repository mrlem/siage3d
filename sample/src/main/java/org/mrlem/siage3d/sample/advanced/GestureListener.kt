package org.mrlem.siage3d.sample.advanced

import android.view.MotionEvent
import org.mrlem.siage3d.core.view.SceneGestureListener
import kotlin.math.abs

/**
 * A simple gesture listener allowing to control your position with simple scroll events.
 */
class GestureListener : SceneGestureListener() {

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        super.onScroll(e1, e2, distanceX, distanceY)

        val startEvent = e1 ?: return true
        val currentEvent = e2 ?: return true

        // x axis controls camera rotation
        val deltaX = (currentEvent.x - startEvent.x).coerceIn(SCROLL_RANGE)
        state.leftRight = if (abs(deltaX) > SCROLL_THRESHOLD) {
            val thresholdCompensation = if (deltaX > 0) SCROLL_THRESHOLD else -SCROLL_THRESHOLD
            (deltaX - thresholdCompensation) / SCROLL_AMPLITUDE
        } else 0f

        // y axis controls motion direction
        val deltaY = (currentEvent.y - startEvent.y).coerceIn(SCROLL_RANGE)
        state.upDown = if (abs(deltaY) > SCROLL_THRESHOLD) {
            val thresholdCompensation = if (deltaY > 0) SCROLL_THRESHOLD else -SCROLL_THRESHOLD
            -(deltaY - thresholdCompensation) / SCROLL_AMPLITUDE
        } else 0f

        return true
    }

    override fun onScrollEnd(endEvent: MotionEvent) {
        state.upDown = 0f
        state.leftRight = 0f
    }

    companion object {
        private const val SCROLL_THRESHOLD = 64f
        private const val SCROLL_AMPLITUDE = 128f
        private val SCROLL_RANGE = (-(SCROLL_AMPLITUDE + SCROLL_THRESHOLD) .. SCROLL_AMPLITUDE + SCROLL_THRESHOLD)
    }

}
