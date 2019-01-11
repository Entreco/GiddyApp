package nl.entreco.giddyapp.viewer.swiper

import android.view.View
import androidx.databinding.BindingAdapter
import kotlin.math.abs
import kotlin.random.Random

object SwipeBindings {
    @JvmStatic
    @BindingAdapter("ga_animateNext")
    fun animateNextView(view: View, progress: Float) {
        val scale = 0.5F + abs(progress) / 2.0F
        if (view.scaleX != scale) {
            val rotation = (1 - (abs(progress))) * (view.tag as? Float ?: 0F)
            view.animate().scaleX(scale).scaleY(scale).rotation(rotation).setDuration(0).start()
        }
    }

    @JvmStatic
    @BindingAdapter("ga_randomRotation")
    fun applyRandomRotation(view: View, apply: Boolean) {
        if (apply) {
            val random = Random.nextInt(-15, 15).toFloat()
            view.tag = random
            view.animate().rotation(random).setDuration(0).start()
        }
    }

    @JvmStatic
    @BindingAdapter("ga_animateDislike")
    fun animateDislikeView(view: View, progress: Float) {
        val select = progress < -.5
        if (view.isSelected != select) view.isSelected = select
    }

    @JvmStatic
    @BindingAdapter("ga_animateLike")
    fun animateLikeView(view: View, progress: Float) {
        val select = progress > .5
        if (view.isSelected != select) view.isSelected = select
    }
}