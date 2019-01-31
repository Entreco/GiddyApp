package nl.entreco.giddyapp.libcropper.window.edge

import android.graphics.RectF
import nl.entreco.giddyapp.libcropper.util.AspectRatioUtil
import kotlin.math.max
import kotlin.math.min

enum class Edge() {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM;


    var coordinate: Float = 0F

    var offset: Float = 0F
        set(value) {
            coordinate += value
            field = value
        }

    fun adjustCoordinate(x: Float, y: Float, imageRect: RectF, imageSnapRadius: Float, aspectRatio: Float) {
        coordinate = when (this) {
            LEFT -> adjustLeft(x, imageRect, imageSnapRadius, aspectRatio)
            TOP -> adjustTop(y, imageRect, imageSnapRadius, aspectRatio)
            RIGHT -> adjustRight(x, imageRect, imageSnapRadius, aspectRatio)
            BOTTOM -> adjustBottom(y, imageRect, imageSnapRadius, aspectRatio)
        }
    }

    fun adjustCoordinate(aspectRatio: Float) {

        val left = Edge.LEFT.coordinate
        val top = Edge.TOP.coordinate
        val right = Edge.RIGHT.coordinate
        val bottom = Edge.BOTTOM.coordinate

        coordinate = when (this) {
            LEFT -> AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio)
            TOP -> AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio)
            RIGHT -> AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio)
            BOTTOM -> AspectRatioUtil.calculateBottom(left, top, right, aspectRatio)
        }
    }

    fun isNewRectangleOutOfBounds(edge: Edge, imageRect: RectF, aspectRatio: Float): Boolean {
        val offset = edge.snapOffset(imageRect)
        when (this) {
            LEFT -> {
                if (edge == Edge.TOP) {
                    val top = imageRect.top
                    val bottom = Edge.BOTTOM.coordinate - offset
                    val right = Edge.RIGHT.coordinate
                    val left = AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio)
                    return isOutOfBounds(top, left, bottom, right, imageRect)
                } else if (edge == Edge.BOTTOM) {
                    val bottom = imageRect.bottom
                    val top = Edge.TOP.coordinate - offset
                    val right = Edge.RIGHT.coordinate
                    val left = AspectRatioUtil.calculateLeft(top, right, bottom, aspectRatio)
                    return isOutOfBounds(top, left, bottom, right, imageRect)
                }
            }
            TOP -> {
                if (edge == Edge.LEFT) {
                    val left = imageRect.left
                    val right = Edge.RIGHT.coordinate - offset
                    val bottom = Edge.BOTTOM.coordinate
                    val top = AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio)
                    return isOutOfBounds(top, left, bottom, right, imageRect)
                } else if (edge == Edge.RIGHT) {
                    val right = imageRect.right
                    val left = Edge.LEFT.coordinate - offset
                    val bottom = Edge.BOTTOM.coordinate
                    val top = AspectRatioUtil.calculateTop(left, right, bottom, aspectRatio)
                    return isOutOfBounds(top, left, bottom, right, imageRect)
                }
            }
            RIGHT -> {
                if (edge == Edge.TOP) {
                    val top = imageRect.top
                    val bottom = Edge.BOTTOM.coordinate - offset
                    val left = Edge.LEFT.coordinate
                    val right = AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio)
                    return isOutOfBounds(top, left, bottom, right, imageRect)
                } else if (edge == Edge.BOTTOM) {
                    val bottom = imageRect.bottom
                    val top = Edge.TOP.coordinate - offset
                    val left = Edge.LEFT.coordinate
                    val right = AspectRatioUtil.calculateRight(left, top, bottom, aspectRatio)
                    return isOutOfBounds(top, left, bottom, right, imageRect)
                }
            }
            BOTTOM -> {
                if (edge == Edge.LEFT) {
                    val left = imageRect.left
                    val right = Edge.RIGHT.coordinate - offset
                    val top = Edge.TOP.coordinate
                    val bottom = AspectRatioUtil.calculateBottom(left, top, right, aspectRatio)
                    return isOutOfBounds(top, left, bottom, right, imageRect)
                } else if (edge == Edge.RIGHT) {
                    val right = imageRect.right
                    val left = Edge.LEFT.coordinate - offset
                    val top = Edge.TOP.coordinate
                    val bottom = AspectRatioUtil.calculateBottom(left, top, right, aspectRatio)
                    return isOutOfBounds(top, left, bottom, right, imageRect)
                }
            }
        }

        return true
    }

    fun isOutOfBounds(top: Float, left: Float, bottom: Float, right: Float, imageRect: RectF): Boolean {
        return (top < imageRect.top || left < imageRect.left || bottom > imageRect.bottom || right > imageRect.right)
    }

    fun snapToRect(imageRect: RectF): Float {
        val oldCoordinate = coordinate
        when (this) {
            LEFT -> coordinate = imageRect.left
            TOP -> coordinate = imageRect.top
            RIGHT -> coordinate = imageRect.right
            BOTTOM -> coordinate = imageRect.bottom
        }
        return coordinate - oldCoordinate
    }

    fun snapOffset(imageRect: RectF): Float {
        val oldCoordinate = coordinate
        val newCoordinate = when (this) {
            LEFT -> imageRect.left
            TOP -> imageRect.top
            RIGHT -> imageRect.right
            BOTTOM -> imageRect.bottom
        }
        return newCoordinate - oldCoordinate
    }

    fun isOutsideMargin(rect: RectF, margin: Float): Boolean {
        return when (this) {
            LEFT -> coordinate - rect.left < margin
            TOP -> coordinate - rect.top < margin
            RIGHT -> rect.right - coordinate < margin
            BOTTOM -> rect.bottom - coordinate < margin
        }
    }

    private fun adjustLeft(x: Float, imageRect: RectF, imageSnapRadius: Float, aspectRatio: Float): Float {
        return if (x - imageRect.left < imageSnapRadius) imageRect.left
        else {
            val horizontal =
                if (x >= Edge.RIGHT.coordinate - min_crop_length_px) Edge.RIGHT.coordinate - min_crop_length_px
                else Float.POSITIVE_INFINITY
            val vertical =
                if (((Edge.RIGHT.coordinate - x) / aspectRatio) <= min_crop_length_px) Edge.RIGHT.coordinate - (min_crop_length_px * aspectRatio)
                else Float.POSITIVE_INFINITY
            min(x, min(horizontal, vertical))
        }
    }

    private fun adjustRight(x: Float, imageRect: RectF, imageSnapRadius: Float, aspectRatio: Float): Float {
        return if (imageRect.right - x < imageSnapRadius) imageRect.right
        else {
            val horizontal =
                if (x <= Edge.LEFT.coordinate + min_crop_length_px) Edge.LEFT.coordinate + min_crop_length_px
                else Float.NEGATIVE_INFINITY
            val vertical =
                if (((x - Edge.LEFT.coordinate) / aspectRatio) <= min_crop_length_px) Edge.LEFT.coordinate + (min_crop_length_px * aspectRatio)
                else Float.NEGATIVE_INFINITY
            max(x, max(horizontal, vertical))
        }
    }

    private fun adjustTop(y: Float, imageRect: RectF, imageSnapRadius: Float, aspectRatio: Float): Float {
        return if (y - imageRect.top < imageSnapRadius) imageRect.top
        else {
            val horizontal =
                if (y >= Edge.BOTTOM.coordinate - min_crop_length_px) Edge.BOTTOM.coordinate - min_crop_length_px
                else Float.POSITIVE_INFINITY
            val vertical =
                if (((Edge.BOTTOM.coordinate - y) * aspectRatio) <= min_crop_length_px) Edge.BOTTOM.coordinate - (min_crop_length_px / aspectRatio)
                else Float.POSITIVE_INFINITY
            min(y, min(horizontal, vertical))
        }
    }

    private fun adjustBottom(y: Float, imageRect: RectF, imageSnapRadius: Float, aspectRatio: Float): Float {
        return if (imageRect.bottom - y < imageSnapRadius) imageRect.bottom
        else {
            val horizontal =
                if (y <= Edge.TOP.coordinate + min_crop_length_px) Edge.TOP.coordinate + min_crop_length_px
                else Float.NEGATIVE_INFINITY
            val vertical =
                if (((y - Edge.TOP.coordinate) * aspectRatio) <= min_crop_length_px) Edge.TOP.coordinate + (min_crop_length_px / aspectRatio)
                else Float.NEGATIVE_INFINITY
            max(y, max(horizontal, vertical))
        }
    }


    companion object {
        const val min_crop_length_px = 40
        fun width(): Float = Edge.RIGHT.coordinate - Edge.LEFT.coordinate
        fun height(): Float = Edge.BOTTOM.coordinate - Edge.TOP.coordinate
    }
}