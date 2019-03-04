package nl.entreco.giddyapp.viewer.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import com.google.android.material.appbar.AppBarLayout
import nl.entreco.giddyapp.libcore.doOnLayout
import nl.entreco.giddyapp.libcore.ui.DetailSheet
import javax.inject.Inject

class ToolbarAnimator @Inject constructor(window: Window, private val appbar: AppBarLayout) : DetailSheet.SlideListener {

    private var height : Int = 0
    init {
        window.statusBarColor = Color.TRANSPARENT
        appbar.background =
            GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT))

        appbar.doOnLayout {
            height = appbar.height
            onSlide(0F)
        }
    }

    override fun onSlide(offset: Float) {
        appbar.animate()
            .alpha(offset + 0.07F)
//            .translationY(height * (offset - 1))
            .setDuration(0)
            .start()
    }
}