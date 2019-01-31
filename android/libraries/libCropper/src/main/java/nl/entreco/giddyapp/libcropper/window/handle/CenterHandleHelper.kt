package nl.entreco.giddyapp.libcropper.window.handle

import android.graphics.RectF
import nl.entreco.giddyapp.libcropper.window.edge.Edge

internal class CenterHandleHelper : HandleHelper(null, null) {
    override fun updateCropWindow(x: Float, y: Float, imageRect: RectF, snapRadius: Float) {
        val left = Edge.LEFT.coordinate
        val top = Edge.TOP.coordinate
        val right = Edge.RIGHT.coordinate
        val bottom = Edge.BOTTOM.coordinate

        val currentCenterX = (left + right) / 2
        val currentCenterY = (top + bottom) / 2

        val offsetX = x - currentCenterX
        val offsetY = y - currentCenterY

        Edge.LEFT.offset = offsetX
        Edge.TOP.offset = offsetY
        Edge.RIGHT.offset = offsetX
        Edge.BOTTOM.offset = offsetY

        if (Edge.LEFT.isOutsideMargin(imageRect, snapRadius)) {
            val offset = Edge.LEFT.snapToRect(imageRect)
            Edge.RIGHT.offset = offset
        } else if (Edge.RIGHT.isOutsideMargin(imageRect, snapRadius)) {
            val offset = Edge.RIGHT.snapToRect(imageRect)
            Edge.LEFT.offset = offset
        }


        if (Edge.TOP.isOutsideMargin(imageRect, snapRadius)) {
            val offset = Edge.TOP.snapOffset(imageRect)
            Edge.BOTTOM.offset = offset
        } else if (Edge.BOTTOM.isOutsideMargin(imageRect, snapRadius)) {
            val offset = Edge.BOTTOM.snapToRect(imageRect)
            Edge.TOP.offset = offset
        }
    }

    override fun updateCropWindow(x: Float, y: Float, targetAspectRatio: Float, imageRect: RectF, snapRadius: Float) {
        updateCropWindow(x, y, imageRect, snapRadius)
    }
}