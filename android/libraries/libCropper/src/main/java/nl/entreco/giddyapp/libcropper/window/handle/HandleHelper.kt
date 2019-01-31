package nl.entreco.giddyapp.libcropper.window.handle

import android.graphics.RectF
import nl.entreco.giddyapp.libcropper.util.AspectRatioUtil
import nl.entreco.giddyapp.libcropper.window.edge.Edge
import nl.entreco.giddyapp.libcropper.window.edge.EdgePair

internal abstract class HandleHelper(private val horizontalEdge: Edge?, private val verticalEdge: Edge?) {

    var activeEdges = EdgePair(horizontalEdge, verticalEdge)

    open fun updateCropWindow(x: Float, y: Float, imageRect: RectF, snapRadius: Float){
        val (primary, seconday) = activeEdges
        primary?.adjustCoordinate(x, y, imageRect, snapRadius, unfixed_aspect_ratio_constant)
        seconday?.adjustCoordinate(x, y, imageRect, snapRadius, unfixed_aspect_ratio_constant)
    }

    abstract fun updateCropWindow(x:Float, y: Float, targetAspectRatio: Float, imageRect: RectF, snapRadius: Float)

    fun getActiveEdges(x: Float, y: Float, targetAspectRatio: Float) : EdgePair {
        val potentialAspectRation = getAspectRatio(x, y)
        if(potentialAspectRation > targetAspectRatio){
            activeEdges = EdgePair(verticalEdge, horizontalEdge)
        } else {
            activeEdges = EdgePair(horizontalEdge, verticalEdge)
        }
        return activeEdges
    }

    private fun getAspectRatio(x: Float, y: Float) : Float{
        val left = if(verticalEdge == Edge.LEFT) x else Edge.LEFT.coordinate
        val top = if(horizontalEdge == Edge.TOP) y else Edge.TOP.coordinate
        val right = if(verticalEdge == Edge.RIGHT) x else Edge.RIGHT.coordinate
        val bottom = if(horizontalEdge == Edge.BOTTOM) y else Edge.BOTTOM.coordinate
        return AspectRatioUtil.calculateAspectRatio(left, top, right, bottom)
    }

    companion object {
        const val unfixed_aspect_ratio_constant = 1F
    }
}