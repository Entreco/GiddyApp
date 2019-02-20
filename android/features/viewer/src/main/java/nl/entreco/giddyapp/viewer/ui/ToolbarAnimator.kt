package nl.entreco.giddyapp.viewer.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Window
import com.google.android.material.animation.ArgbEvaluatorCompat
import com.google.android.material.appbar.AppBarLayout

class ToolbarAnimator(private val window: Window, private val appbar: AppBarLayout) {

    private var start: Int = 0
    private var end: Int = 0
    private val interpolator = ArgbEvaluatorCompat()

    init {
        val almostBlack = Color.parseColor("#190E20")
        window.statusBarColor = almostBlack
        appbar.background =
            GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(almostBlack, Color.TRANSPARENT))
    }

    fun toggle(startColor: Int, endColor: Int) {
        window.statusBarColor = Color.TRANSPARENT
        appbar.background =
            GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT))
    }

    fun go(offset: Float) {
//        val evaluatedColor = interpolator.evaluate(offset, start, end)
//        window.statusBarColor = evaluatedColor
//        appbar.background = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(evaluatedColor, Color.TRANSPARENT))
    }
}