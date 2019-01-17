package nl.entreco.giddyapp.viewer.ui.swiper

import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import kotlin.math.PI
import kotlin.math.abs

internal class SwipeAnimator(private val view: SwipeHorseView) {

    internal var swipeListener: OnSwipedListener? = null

    private val piAsFloat = PI.toFloat()
    private val springX by lazy { SpringAnimation(view, DynamicAnimation.X) }
    private val springY by lazy { SpringAnimation(view, DynamicAnimation.Y) }

    fun setStartValues(x: Float, y: Float) {
        springX.setStartValue(x)
        springY.setStartValue(y)
    }

    fun releaseTo(xTarget: Float, y: Float, onUpdate: (Float) -> Unit) {
        springX.apply {
            cancel()
            addUpdateListener { _, value, _ ->
                onUpdate(value)
            }
            addEndListener { _, _, _, _ ->
                swipeListener?.onNext()
            }
            animateToFinalPosition(xTarget)
        }
        springY.apply {
            cancel()
            animateToFinalPosition(y)
        }
    }

    fun cancel(x: Float, y: Float, onUpdate: (Float) -> Unit) {
        view.animate().scaleY(1F)
            .scaleX(1f)
            .rotationY(0F)
            .translationZ(2F)
            .setDuration(300)
            .start()

        // Release the Springs
        springX.apply {
            cancel()
            addUpdateListener { _, value, _ ->
                onUpdate(value)
            }
            animateToFinalPosition(x)
        }
        springY.apply {
            cancel()
            animateToFinalPosition(y)
        }
    }

    fun animateDrag(progress: Float) {
        view.scaleX = 1 - (abs(progress) / 6F)
        view.scaleY = 1 - (abs(progress) / 6F)
        view.translationZ = abs(progress) * 8 + 2F
        view.rotationY = progress * piAsFloat
        view.isSelected = progress > threshold
        view.isActivated = progress < -threshold
    }

    companion object {
        const val threshold = 0.3F
    }
}