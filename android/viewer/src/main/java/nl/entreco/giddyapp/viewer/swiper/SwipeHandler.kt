package nl.entreco.giddyapp.viewer.swiper

import android.view.MotionEvent
import android.view.View

class SwipeHandler(
    private val drag: (x: Float, y: Float) -> Unit,
    private val release: (x: Float, y: Float) -> Unit
) : View.OnTouchListener {

    private var dX: Float = 0F
    private var dY: Float = 0F
    private var startX: Float = 0F
    private var startY: Float = 0F

    override fun onTouch(view: View, event: MotionEvent): Boolean {

        return when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                startX = view.x
                startY = view.y
                dX = view.x - event.rawX
                dY = view.y - event.rawY
                true
            }
            MotionEvent.ACTION_MOVE -> {
                view.y = event.rawY + dY
                view.x = event.rawX + dX
                drag(view.x, view.y)
                true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                release(startX, startY)
                true
            }
            else -> false
        }
    }
}