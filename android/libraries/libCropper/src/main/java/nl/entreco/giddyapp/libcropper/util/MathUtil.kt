package nl.entreco.giddyapp.libcropper.util

import kotlin.math.sqrt

internal object MathUtil {
    fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float) : Float {
        val side1 = x2 - x1
        val side2 = y2 - y1
        return sqrt(side1 * side1 + side2 * side2)
    }
}