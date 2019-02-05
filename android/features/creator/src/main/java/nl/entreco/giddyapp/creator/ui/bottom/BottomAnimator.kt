package nl.entreco.giddyapp.creator.ui.bottom

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import nl.entreco.giddyapp.creator.R
import kotlin.math.ceil
import kotlin.math.floor

internal class BottomAnimator(private val view: BottomProgressView) {

    companion object {
        const val num = 4 // Number of CreatorState's + 1
    }

    private val bottomBar = view.findViewById<BottomAppBar>(R.id.bar)
    private val bottomFab = view.findViewById<FloatingActionButton>(R.id.fab)
    private val bottomStart = view.findViewById<View>(R.id.bottom_start).apply { pivotX = 0F }
    private val bottomEnd = view.findViewById<View>(R.id.bottom_end).apply { pivotX = view.width.toFloat() }

    fun slideTo(index: Int) {

        if(index in 0 until num) {
            val screenWidth = view.width
            bottomBar.animate()
                .withStartAction { bottomFab.hide() }
                .translationX((index * ((screenWidth - bottomBar.width) / (num - 1))).toFloat())
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setUpdateListener { _ ->
                    val current = bottomBar.x
                    bottomStart.scaleX = (current / screenWidth) + 0.01f
                    bottomEnd.scaleX = 1.01f- (current + bottomBar.width) / screenWidth
                }
                .withEndAction { bottomFab.show() }
                .start()
        }
    }
}