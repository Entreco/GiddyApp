package nl.entreco.giddyapp.libcropper.window.handle

import android.graphics.RectF
import nl.entreco.giddyapp.libcropper.util.AspectRatioUtil
import nl.entreco.giddyapp.libcropper.window.edge.Edge

class VerticalHandleHelper(private val edge: Edge) : HandleHelper(null, edge) {

    override fun updateCropWindow(x: Float, y: Float, targetAspectRatio: Float, imageRect: RectF, snapRadius: Float) {
        edge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio)
        var top = Edge.TOP.coordinate
        var bottom = Edge.BOTTOM.coordinate

        val targetHeight = AspectRatioUtil.calculateHeight(Edge.width(), targetAspectRatio)
        val difference = targetHeight - Edge.height()
        val halfDifference = difference / 2
        top -= halfDifference
        bottom += halfDifference

        Edge.TOP.coordinate = top
        Edge.BOTTOM.coordinate = bottom

        if (Edge.LEFT.isOutsideMargin(imageRect, snapRadius)
            && !edge.isNewRectangleOutOfBounds(Edge.TOP, imageRect, targetAspectRatio)
        ) {
            val offset = Edge.TOP.snapToRect(imageRect)
            Edge.BOTTOM.offset = -offset
            edge.adjustCoordinate(targetAspectRatio)
        }

        if (Edge.BOTTOM.isOutsideMargin(imageRect, snapRadius)
            && !edge.isNewRectangleOutOfBounds(Edge.BOTTOM, imageRect, targetAspectRatio)
        ) {
            val offset = Edge.BOTTOM.snapToRect(imageRect)
            Edge.TOP.offset = -offset
            edge.adjustCoordinate(targetAspectRatio)
        }
    }
}