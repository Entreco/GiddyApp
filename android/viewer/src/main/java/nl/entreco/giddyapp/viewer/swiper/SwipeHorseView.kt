package nl.entreco.giddyapp.viewer.swiper

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import nl.entreco.giddyapp.viewer.databinding.WidgetSwipeHorseViewBinding

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

    private var halfTheScreenWidth = 0
    private var viewOffsetFactor = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        halfTheScreenWidth = context.resources.displayMetrics.widthPixels / 2
        viewOffsetFactor = w / 2 - halfTheScreenWidth
    }

    private val onDragged: (Float, Float) -> Unit = { x, y ->
        if (viewOffsetFactor != 0 && halfTheScreenWidth != 0) {
            Log.d("DRAG", "progress: ${((x + viewOffsetFactor) / halfTheScreenWidth)}")
            springX.setStartValue(x)
            springY.setStartValue(y)
        }
    }

    private val onReleased: (Float, Float) -> Unit = { x, y ->
        Log.d("DRAG", "released: $x, $y")
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