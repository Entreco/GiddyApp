package nl.entreco.giddyapp.viewer.swiper

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
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

    private val piAsFloat = PI.toFloat()
    private var progressChangedListener: OnProgressChangedListener? = null
    private var swipedListener: OnSwipedListener? = null

    private var screenWidth = 0
    private var viewOffsetFactor = 0
    private var swipeProgess = 0F
        set(value) {
            progressChangedListener?.onChanged(this, value)
            field = value
        }


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
        when {
            swipeProgess < -.5 -> onDisliked(startX, startY)
            swipeProgess > .5 -> onLiked(startX, startY)
            else -> onUserCancelled(startX, startY)
        }
    }

    private fun onDisliked(x: Float, y: Float) {
        springX.apply {
            addUpdateListener { _, value, _ ->
                swipeProgess = (value + viewOffsetFactor) / screenWidth
            }
            addEndListener { _, _, _, _ ->
                swipedListener?.onNext()
            }
            animateToFinalPosition(x - screenWidth)
        }
        springY.apply {
            animateToFinalPosition(y)
        }
    }

    private fun onLiked(x: Float, y: Float) {
        springX.apply {
            addUpdateListener { _, value, _ ->
                swipeProgess = (value + viewOffsetFactor) / screenWidth
            }
            addEndListener { _, _, _, _ ->
                swipedListener?.onNext()
            }
            animateToFinalPosition(x + screenWidth)
        }
        springY.apply {
            animateToFinalPosition(y)
        }
    }

    private fun onUserCancelled(x: Float, y: Float) {
        // Reset all properties changed by 'someRandomDepthTransformation'
        animate().scaleY(1F)
            .scaleX(1f)
            .rotationY(0F)
            .translationZ(2F)
            .setDuration(100)
            .start()

        // Release the Springs
        springX.apply {
            addUpdateListener { _, value, _ ->
                swipeProgess = (value + viewOffsetFactor) / screenWidth
            }
            animateToFinalPosition(x)
        }
        springY.apply {
            animateToFinalPosition(y)
        }
    }

    fun setModel(model: SwipeHorseModel?) {
        binding.model = model
        binding.executePendingBindings()
        binding.image.background = model?.gradient

        when (model?.draggable) {
            true -> setOnTouchListener(SwipeHandler(onDragged, onReleased))
            else -> setOnTouchListener(null)
        }
    }

    fun setOnSwipedListener(listener: OnSwipedListener?) {
        swipedListener = listener
    }

    fun setOnProgressChangedListener(listener: OnProgressChangedListener?) {
        progressChangedListener = listener
    }

    interface OnProgressChangedListener {
        fun onChanged(view: View, progress: Float)
    }
}