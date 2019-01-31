package nl.entreco.giddyapp.libcropper.util

import android.graphics.PointF
import nl.entreco.giddyapp.libcropper.window.handle.Handle
import kotlin.math.abs

internal object HandleUtil {

    fun getPressedHandle(
        x: Float,
        y: Float,
        left: Float,
        top: Float,
        right: Float,
        bottom: Float,
        targetRadius: Float
    ): Handle? {
        var closestHandle: Handle? = null
        var closestDistance = Float.POSITIVE_INFINITY

        val distanceToTopLeft = MathUtil.calculateDistance(x, y, left, top)
        if (distanceToTopLeft < closestDistance) {
            closestDistance = distanceToTopLeft
            closestHandle = Handle.TOP_LEFT
        }

        val distanceToTopRight = MathUtil.calculateDistance(x, y, right, top)
        if (distanceToTopRight < closestDistance) {
            closestDistance = distanceToTopRight
            closestHandle = Handle.TOP_RIGHT
        }

        val distanceToBottomLeft = MathUtil.calculateDistance(x, y, left, bottom)
        if (distanceToBottomLeft < closestDistance) {
            closestDistance = distanceToBottomLeft
            closestHandle = Handle.BOTTOM_LEFT
        }

        val distanceToBottomRight = MathUtil.calculateDistance(x, y, right, bottom)
        if (distanceToBottomRight < closestDistance) {
            closestDistance = distanceToBottomRight
            closestHandle = Handle.BOTTOM_RIGHT
        }

        if (closestDistance <= targetRadius) return closestHandle


        if (isInHorizontalTargetZone(x, y, left, right, top, targetRadius)) {
            return Handle.TOP
        } else if (isInHorizontalTargetZone(x, y, left, right, bottom, targetRadius)) {
            return Handle.BOTTOM
        } else if (isInVerticalTargetZone(x, y, left, top, bottom, targetRadius)) {
            return Handle.LEFT
        } else if (isInVerticalTargetZone(x, y, right, top, bottom, targetRadius)) {
            return Handle.RIGHT
        }

        if (isWithinBounds(x, y, left, top, right, bottom)) {
            return Handle.CENTER
        }

        return null
    }


    fun getOffset(handle: Handle, x:Float, y:Float, left: Float, top:Float, right:Float, bottom:Float, touchOffsetOutput: PointF) {
        val (_x, _y) = when(handle){
            Handle.TOP_LEFT -> Pair(left - x, top - y)
            Handle.TOP_RIGHT -> Pair(right - x, top - y)
            Handle.BOTTOM_LEFT -> Pair(left - x, bottom - y)
            Handle.BOTTOM_RIGHT -> Pair(right - x, bottom - y)
            Handle.LEFT -> Pair(left - x, 0F)
            Handle.TOP -> Pair(0F, top - y)
            Handle.RIGHT -> Pair(right - x, 0F)
            Handle.BOTTOM -> Pair(0F, bottom - y)
            Handle.CENTER -> {
                val centerX = (right + left) / 2F
                val centerY = (top + bottom) / 2F
                Pair(centerX - x, centerY - y)
            }
        }

        touchOffsetOutput.x = _x
        touchOffsetOutput.y = _y
    }

    private fun isInHorizontalTargetZone(
        x: Float,
        y: Float,
        handleXStart: Float,
        handleXEnd: Float,
        handleY: Float,
        targetRadius: Float
    ): Boolean {
        return (x > handleXStart && x < handleXEnd && abs(y - handleY) <= targetRadius)
    }

    private fun isInVerticalTargetZone(
        x: Float,
        y: Float,
        handleX: Float,
        handleYStart: Float,
        handleYEnd: Float,
        targetRadius: Float
    ): Boolean {
        return (abs(x - handleX) <= targetRadius && y > handleYStart && y < handleYEnd)
    }

    private fun isWithinBounds(x: Float, y: Float, left: Float, top: Float, right: Float, bottom: Float): Boolean {
        return x in left..right && y in top..bottom
    }
}