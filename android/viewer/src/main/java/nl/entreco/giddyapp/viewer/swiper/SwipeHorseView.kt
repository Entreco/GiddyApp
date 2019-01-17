package nl.entreco.giddyapp.viewer.swiper

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import nl.entreco.giddyapp.core.images.ImageLoader
import nl.entreco.giddyapp.viewer.databinding.WidgetSwipeHorseViewBinding


class SwipeHorseView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: WidgetSwipeHorseViewBinding
    private val inflater by lazy { LayoutInflater.from(context) }
    private val animator by lazy { SwipeAnimator(this) }

    init {
        binding = WidgetSwipeHorseViewBinding.inflate(inflater, this, true)
    }

    private var progressChangedListener: OnProgressChangedListener? = null

    private var touched = false
    private var screenWidth = 0
    private var viewOffsetFactor = 0
    private var swipeProgress = 0F
        set(value) {
            progressChangedListener?.onChanged(this, value, touched)
            field = value
        }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = context.resources.displayMetrics.widthPixels
        viewOffsetFactor = w / 2 - screenWidth / 2
    }

    private val onDragged: (Float, Float) -> Unit = { x, y ->
        if (viewOffsetFactor >= 0 && screenWidth != 0) {
            touched = true
            animator.setStartValues(x, y)
            swipeProgress = (x + viewOffsetFactor) / screenWidth
            animator.animateDrag(swipeProgress)
        }
    }

    private val onReleased: (Float, Float) -> Unit = { startX, startY ->
        touched = false
        when {
            swipeProgress < -SwipeAnimator.threshold -> onDisliked(startX, startY)
            swipeProgress > SwipeAnimator.threshold -> onLiked(startX, startY)
            else -> onUserCancelled(startX, startY)
        }
    }

    private fun onDisliked(x: Float, y: Float) {
        animator.releaseTo(x - screenWidth, y) {
            updateSwipeProgress(it)
        }
    }

    private fun onLiked(x: Float, y: Float) {
        animator.releaseTo(x + screenWidth, y) {
            updateSwipeProgress(it)
        }
    }

    private fun onUserCancelled(x: Float, y: Float) {
        animator.cancel(x, y) {
            updateSwipeProgress(it)
        }
    }

    private fun updateSwipeProgress(value: Float) {
        swipeProgress = (value + viewOffsetFactor) / screenWidth
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

    fun setLoader(loader: ImageLoader) {
        binding.loader = loader
        binding.executePendingBindings()
    }

    fun setOnSwipedListener(listener: OnSwipedListener?) {
        animator.swipeListener = listener
    }

    fun setOnProgressChangedListener(listener: OnProgressChangedListener?) {
        progressChangedListener = listener
    }

    interface OnProgressChangedListener {
        fun onChanged(view: View, progress: Float, touched: Boolean)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("ga_loader", "ga_image", requireAll = true)
        fun loadImage(view: ImageView, loader: ImageLoader?, uri: Uri?) {
            loader?.get(view, uri)
        }
    }
}