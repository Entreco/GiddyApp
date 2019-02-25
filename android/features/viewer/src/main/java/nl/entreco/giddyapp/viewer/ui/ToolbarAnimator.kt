package nl.entreco.giddyapp.viewer.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Window
import com.google.android.material.appbar.AppBarLayout
import javax.inject.Inject

class ToolbarAnimator @Inject constructor(private val window: Window, private val appbar: AppBarLayout) {

    init {
        window.statusBarColor = Color.TRANSPARENT
        appbar.background =
            GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(Color.TRANSPARENT, Color.TRANSPARENT))
    }
}