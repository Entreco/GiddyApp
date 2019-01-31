package nl.entreco.giddyapp.libcropper.window.handle

import android.graphics.RectF
import nl.entreco.giddyapp.libcropper.window.edge.Edge

internal enum class Handle(private val helper: HandleHelper) {
    TOP_LEFT(CornerHandleHelper(Edge.TOP, Edge.LEFT)),
    TOP_RIGHT(CornerHandleHelper(Edge.TOP, Edge.RIGHT)),
    BOTTOM_LEFT(CornerHandleHelper(Edge.BOTTOM, Edge.LEFT)),
    BOTTOM_RIGHT(CornerHandleHelper(Edge.BOTTOM, Edge.RIGHT)),
    LEFT(VerticalHandleHelper(Edge.LEFT)),
    TOP(HorizontalHandleHelper(Edge.TOP)),
    RIGHT(VerticalHandleHelper(Edge.RIGHT)),
    BOTTOM(HorizontalHandleHelper(Edge.BOTTOM)),
    CENTER(CenterHandleHelper());

    fun updateCropWindow(x: Float, y: Float, imageRect: RectF, snapRadius: Float) {
        helper.updateCropWindow(x, y, imageRect, snapRadius)
    }

    fun updateCropWindow(x: Float, y: Float, targetAspectRatio: Float, imageRect: RectF, snapRadius: Float) {
        helper.updateCropWindow(x, y, targetAspectRatio, imageRect, snapRadius)
    }
}