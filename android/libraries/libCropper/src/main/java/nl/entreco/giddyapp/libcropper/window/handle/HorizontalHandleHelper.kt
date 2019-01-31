package nl.entreco.giddyapp.libcropper.window.handle

import android.graphics.RectF
import nl.entreco.giddyapp.libcropper.util.AspectRatioUtil
import nl.entreco.giddyapp.libcropper.window.edge.Edge

class HorizontalHandleHelper(private val edge: Edge) : HandleHelper(edge, null) {

    override fun updateCropWindow(x: Float, y: Float, targetAspectRatio: Float, imageRect: RectF, snapRadius: Float) {
        edge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio)
        var left = Edge.LEFT.coordinate
        var right = Edge.RIGHT.coordinate

        val targetWidth = AspectRatioUtil.calculateWidth(Edge.height(), targetAspectRatio)
        val difference = targetWidth - Edge.width()
        val halfDifference = difference / 2

        left -= halfDifference
        right += halfDifference

        Edge.LEFT.coordinate = left
        Edge.RIGHT.coordinate = right

        if (Edge.LEFT.isOutsideMargin(imageRect, snapRadius) &&
            !edge.isNewRectangleOutOfBounds(Edge.LEFT, imageRect, targetAspectRatio)
        ) {
            val offset = Edge.LEFT.snapToRect(imageRect)
            Edge.RIGHT.offset = -offset
            edge.adjustCoordinate(targetAspectRatio)
        }

        if (Edge.RIGHT.isOutsideMargin(imageRect, snapRadius) &&
            !edge.isNewRectangleOutOfBounds(Edge.RIGHT, imageRect, targetAspectRatio)
        ) {
            val offset = Edge.RIGHT.snapToRect(imageRect)
            Edge.LEFT.offset = -offset
            edge.adjustCoordinate(targetAspectRatio)
        }
    }
}