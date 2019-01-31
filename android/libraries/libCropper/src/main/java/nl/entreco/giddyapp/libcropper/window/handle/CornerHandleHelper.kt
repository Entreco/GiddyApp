package nl.entreco.giddyapp.libcropper.window.handle

import android.graphics.RectF
import nl.entreco.giddyapp.libcropper.window.edge.Edge

class CornerHandleHelper(horizontalEdge: Edge, verticalEdge: Edge) : HandleHelper(horizontalEdge, verticalEdge) {
    override fun updateCropWindow(x: Float, y: Float, targetAspectRatio: Float, imageRect: RectF, snapRadius: Float) {
        val (primary, secondary) = getActiveEdges(x, y, targetAspectRatio)
        primary?.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio)
        secondary?.adjustCoordinate(targetAspectRatio)

        if (secondary?.isOutsideMargin(imageRect, snapRadius) == true) {
            secondary.snapToRect(imageRect)
            primary?.adjustCoordinate(targetAspectRatio)
        }
    }
}