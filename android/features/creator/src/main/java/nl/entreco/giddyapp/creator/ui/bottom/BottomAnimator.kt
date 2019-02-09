package nl.entreco.giddyapp.creator.ui.bottom

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import nl.entreco.giddyapp.creator.CreatorState
import nl.entreco.giddyapp.creator.R

internal class BottomAnimator(private val view: BottomProgressView) {

    private val bottomBar = view.findViewById<BottomAppBar>(R.id.bar)
    private val bottomFab = view.findViewById<FloatingActionButton>(R.id.fab)
    private val bottomStart = view.findViewById<View>(R.id.bottom_start).apply { pivotX = 0F }
    private val bottomEnd = view.findViewById<View>(R.id.bottom_end).apply { pivotX = view.width.toFloat() }

    fun slideTo(index: Int) {

        if (index in 0 until num) {
            val screenWidth = view.width
            bottomBar.animate()
                .withStartAction { bottomFab.hide() }
                .translationX((index * ((screenWidth - bottomBar.width) / (num - 1))).toFloat())
                .setInterpolator(AccelerateDecelerateInterpolator())
                .setUpdateListener { _ ->
                    val current = bottomBar.x
                    bottomStart.scaleX = (current / screenWidth) + 0.01f
                    bottomEnd.scaleX = 1.01f - (current + bottomBar.width) / screenWidth
                }
//                .withEndAction { bottomFab.show() }
                .start()
        }
    }

    companion object {
        const val num = 4 // Number of CreatorState's + 1

        @JvmStatic
        @BindingAdapter("ga_animateheight")
        fun animateHeight(view: ConstraintLayout, step: BottomStepModel?) {

            // Hide them All
            val select = view.findViewById<View>(R.id.include_steps_select).apply { animate().alpha(0F).withEndAction { visibility = View.GONE }.start() }
            val crop = view.findViewById<View>(R.id.include_steps_crop).apply { animate().alpha(0F).withEndAction { visibility = View.GONE }.start() }
            val entry = view.findViewById<View>(R.id.include_steps_entry).apply { animate().alpha(0F).withEndAction { visibility = View.GONE }.start() }

            val height = when (step?.state) {
                is CreatorState.Select -> select.apply { animate().alpha(1F).withStartAction { visibility = View.VISIBLE }.start() }.height
                is CreatorState.Crop -> crop.apply { animate().alpha(1F).withStartAction { visibility = View.VISIBLE }.start() }.height
                is CreatorState.Entry -> entry.apply { animate().alpha(1F).withStartAction { visibility = View.VISIBLE }.start() }.height
                else -> 0
            }
        }
    }
}