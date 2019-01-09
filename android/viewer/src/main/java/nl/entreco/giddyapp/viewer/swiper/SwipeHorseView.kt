package nl.entreco.giddyapp.viewer.swiper

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import nl.entreco.giddyapp.viewer.databinding.WidgetSwipeHorseViewBinding
import kotlin.math.PI
import kotlin.math.abs

class SwipeHorseView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: WidgetSwipeHorseViewBinding
    private val inflater by lazy { LayoutInflater.from(context) }
    private val springX by lazy { SpringAnimation(this, DynamicAnimation.X) }
    private val springY by lazy { SpringAnimation(this, DynamicAnimation.Y) }

    init {
        binding = WidgetSwipeHorseViewBinding.inflate(inflater, this, true)
    }

    private var swipeProgess = 0F
    private var screenWidth = 0
    private var viewOffsetFactor = 0
    private val piAsFloat = PI.toFloat()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = context.resources.displayMetrics.widthPixels
        viewOffsetFactor = w / 2 - screenWidth / 2
    }

    private val onDragged: (Float, Float) -> Unit = { x, y ->
        if (viewOffsetFactor != 0 && screenWidth != 0) {
            springX.setStartValue(x)
            springY.setStartValue(y)
            swipeProgess = (x + viewOffsetFactor) / screenWidth
            Log.d("DRAG", "progress: $swipeProgess")
            someRandomDepthTransformation(swipeProgess)
        }
    }

    private fun someRandomDepthTransformation(progress: Float) {
        scaleX = 1 - (abs(progress) / 6F)
        scaleY = 1 - (abs(progress) / 6F)
        translationZ = abs(progress) * 8 + 2F
        rotationY = progress * piAsFloat
    }

    private val onReleased: (Float, Float) -> Unit = { startX, startY ->
        Log.d("DRAG", "released: $startX, $startY")

        // TODO: Check 3 cases:
        // - User liked Horse -> dismiss and load next
        // - User Disliked Horse -> dismiss and load next
        // - User Cancelled ->
        when {
            swipeProgess < -.5 -> {
            }
            swipeProgess > .5 -> {
            }
            else -> {
            }
        }

        onUserCancelled(startX, startY)
        swipeProgess = 0F
    }

    private fun onUserCancelled(x: Float, y: Float) {
        // Reset all properties changed by 'someRandomDepthTransformation'
        animate().scaleY(1F).scaleX(1F).rotationY(0F).translationZ(2F).setDuration(100).start()
        // Release the Springs
        springX.apply {
            animateToFinalPosition(x)
        }
        springY.apply {
            animateToFinalPosition(y)
        }
    }

    fun setModel(model: SwipeHorseModel?) {
        binding.model = model
        binding.executePendingBindings()
        background = model?.gradient

        when (model?.draggable) {
            true -> setOnTouchListener(SwipeHandler(onDragged, onReleased))
            else -> setOnTouchListener(null)
        }
    }
}