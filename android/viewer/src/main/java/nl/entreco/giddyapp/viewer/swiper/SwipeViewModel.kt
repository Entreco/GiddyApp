package nl.entreco.giddyapp.viewer.swiper

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat
import androidx.lifecycle.ViewModel
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random

class SwipeViewModel @Inject constructor() : ViewModel() {
    val current = ObservableField<SwipeHorseModel>()
    val next = ObservableField<SwipeHorseModel>()
    val nextProgress = ObservableFloat(0F)

    fun trackProgress(view: View, progress: Float) {
        nextProgress.set(progress)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("ga_animateNext")
        fun animateNextView(view: View, progress: Float) {
            val scale = 0.5F + abs(progress)/2.0F
            val rotation = 1 - abs(progress)
            if(view.scaleX != scale) view.animate().scaleX(scale).scaleY(scale).rotation(rotation).setDuration(0).start()
        }

        @JvmStatic
        @BindingAdapter("ga_randomRotation")
        fun applyRandomRotation(view: View, apply: Boolean) {
            if(apply){
                val random = Random.nextInt(-20, 20).toFloat()
                view.animate().rotation(random).setDuration(0).start()
            }
        }

        @JvmStatic
        @BindingAdapter("ga_animateDislike")
        fun animateDislikeView(view: View, progress: Float) {
            val select = progress < -.5
            if(view.isSelected != select) view.isSelected = select
        }

        @JvmStatic
        @BindingAdapter("ga_animateLike")
        fun animateLikeView(view: View, progress: Float) {
            val select = progress > .5
            if(view.isSelected != select) view.isSelected = select
        }
    }
}